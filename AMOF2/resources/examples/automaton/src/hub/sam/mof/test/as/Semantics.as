StateAutomaton::Semantics::AutomatonInstance::consume activity {
    start;
    Expression: [
            port->select(p|p.classifier->asSequence()->first().directionForComponent(
                    self.classifier->asSequence()->first().oclAsType(StateAutomaton::Syntax::Component)) = 
                    StateAutomaton::Syntax::Direction::inbound)->asSequence()->first()
        ] >new inPort:PortInstance;
    Expression: [signal->at(1)] <inPort as context, >new signal:SignalInstance;
    RemoveStructuralFeatureValue: signal <inPort as context, <signal;
    Print: "consuming signal: ";
    PrintExpression: [classifier->asSequence()->first()] <signal as context;
    Call: consume <{Expression: [
        if signal.destinationId = -1 then instance->asSequence()->first() 
        else instance->select(i| i.processId = self.signal.destinationId)->asSequence()->first() endif] <signal as signal}:SingleAutomatonInstance as context, <signal;
    end;
}

StateAutomaton::Semantics::AutomatonInstance::create activity {
    start;
    CreateObject: Integer, unique >new return:core::primitivetypes::Integer;
    CreateObject: SingleAutomatonInstance >new sai:SingleAutomatonInstance;    
    WriteStructuralFeature: processId <sai as context, <return;
    WriteStructuralFeatureValue: instance <sai;
    Expression: [classifier->asSequence()->first().start] >new startState:StateAutomaton::Syntax::State;
    WriteStructuralFeature: actualState <sai as context, <startState;
    end;
}


StateAutomaton::Semantics::PortInstance::send activity {
    start;
    while [signal->notEmpty()] {
        Expression: [signal->at(1)] >new signal:SignalInstance;
        RemoveStructuralFeatureValue: signal <signal;
        WriteStructuralFeatureValue: signal <{Expression: [opposite]}:PortInstance as context, <signal;
        decision [opposite.component.oclIsTypeOf(AutomatonInstance)] {
            Print: "send signal to automaton";
            PrintExpression: [self.classifier->asSequence()->first()] < signal as context;
            Call: consume <{Expression: [opposite.component.oclAsType(AutomatonInstance)]}:AutomatonInstance as context;        
        } [else] {
            Print: "send signal to system";
            PrintExpression: [self.classifier->asSequence()->first()] < signal as context;
        }
    }
    end;
}


StateAutomaton::Semantics::PortInstance::opposite query {
    [classifier->asSequence()->first().channel.targetPort->asSequence()->first().implicitInstance]
}

StateAutomaton::Semantics::SingleAutomatonInstance::consume activity {
    start >new signal:SignalInstance;
    WriteStructuralFeature: senderId <{Expression: [sourceId] <signal as context}:core::primitivetypes::Integer;
    decision [let mysig:StateAutomaton::Syntax::Signal=signal.classifier->asSequence()->first() in 
            trigger->select(t|t.input.type = mysig)->notEmpty()] <signal as signal {
        Expression: [
            let mysig:StateAutomaton::Syntax::Signal=signal.classifier->asSequence()->first() in 
                trigger->select(t|t.input.type = mysig)->asSequence()->first()
        ] <signal as signal, >new transition:StateAutomaton::Syntax::Transition;
        do {
            Iterate: [action], i <transition as context, >new action:StateAutomaton::Syntax::Action; 
            decision [self.oclIsTypeOf(Create)] <action as context {
                Call: create <{Expression: [
                    self.oclAsType(Create).automaton.implicitInstance.oclAsType(AutomatonInstance)
                ] <action as context}:AutomatonInstance as context, 
                >new offspring:core::primitivetypes::Integer;
                WriteStructuralFeature: offspringId <offspring;        
            } [self.oclIsTypeOf(Output)] <action as context {
                CreateObject: SignalInstance >new newSignal:SignalInstance;
                WriteStructuralFeatureValue: classifier 
                        <newSignal as context, 
                        <{Expression: [self.oclAsType(Output).type] <action as context}:StateAutomaton::Syntax::Signal;
                WriteStructuralFeature: sourceId <newSignal as context, 
                        <{Expression: [processId]}:core::primitivetypes::Integer;
                decision [self.oclAsType(Output).receiver->isEmpty()] <action as context {
                    WriteStructuralFeature: destinationId <newSignal as context, <{Expression: [-1]}:core::primitivetypes::Integer;
                } [else] {
                    WriteStructuralFeature: destinationId <newSignal as context, <{Expression: [
                        let rec:StateAutomaton::Syntax::Expression=action.oclAsType(Output).receiver in 
                            if rec.oclIsTypeOf(Offspring) then self.offspringId else self.senderId endif
                    ] <action as action}:core::primitivetypes::Integer;
                }
                Expression: [
                    instanceSet.port->select(p|action.oclAsType(Output).via = 
                    p.classifier->asSequence()->first())->asSequence()->first()
                ] <action as action, >new outPort:PortInstance;
                WriteStructuralFeatureValue: signal <outPort as context, <newSignal;
                Call: send <outPort as context;
            }    
            HasNext: i >new next:core::primitivetypes::Boolean;
        } while [next] <next as next
    } [else] {
        Print: "no trigger activated";
    }
    Expression: [-1] >new return:core::primitivetypes::Integer;
    end;
}

StateAutomaton::Semantics::SingleAutomatonInstance::trigger query {
    [self.instanceSet.classifier->asSequence()->first().transition->select(t|
	t.sourceState->includes(self.actualState))]
}

StateAutomaton::Syntax::Port::directionForComponent query {
    [
        if otherComponent.oclIsTypeOf(System) then 
	        direction 
        else 
 	        if direction = StateAutomaton::Syntax::Direction::inbound then 
 	            StateAutomaton::Syntax::Direction::outbound
            else 
 	            StateAutomaton::Syntax::Direction::inbound
            endif 
        endif
    ]
}
