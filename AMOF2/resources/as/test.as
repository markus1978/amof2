StateAutomaton::Semantics::AutomatonInstance::consume activity {
    start;
    Expression: [port->select(classifier.directionForComponent(self.classifier->any()) = 
            #Direction.IN)->any()] >new inPort:PortInstance;
    Expression: [signal->get(0)] <inPort as context, >new signal:Signal;
    Call: consume <{Expression: [if signal.destinationId = -1 then instance->any() 
            else instance->select(id = signal.destinationId)] <signal as signal};
    end;
}
