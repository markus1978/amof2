Traffic::SignalLightInstance::justDoIt activity {
    start;
    decision [self.isOn] {
        Call: switchOff;
    } [else] {
        Call: switchOn;
    }
    end;
}

Traffic::TrafficLightInstance::justDoIt activity {
    start >new lightType:SignalLightType;
    Expression: [lights->select(l|l.metaClassifier = lightType)->asSequence()->first()] <lightType as lightType, >new signal:SignalLightInstance;
    decision [not self.isOn] <signal as context {
        decision [lights->select(l|l.isOn)->notEmpty()] {
            Call: justDoIt <{Expression: [lights->select(l|l.isOn)->asSequence()->first()]}:SignalLightInstance as context;
        } [else] {}
        Call: justDoIt <signal as context;
    } [else] {

    }
    end;
}

Traffic::CrossroadInstance::justDoIt activity {
    start;
    Expression: [self.metaClassifier] >new crossroad:Crossroad;
    do {
        Iterate: [lightSequence], i <crossroad as context, >new col:SequenceColumn;
        Print: "----";
        do {
            Iterate: [entries], ii
                    <col as context,
                    >new entry:SequenceEntry;

            Expression: [signal.type.lights->select(l|l.color = self.color)->asSequence()->first()]
                    <entry as context,
                    >new lightType:SignalLightType;

            Call: justDoIt
                    <{Expression:[signal.metaInstance->asSequence()->first()] <entry as context}:TrafficLightInstance as context,
                    <lightType;

        } while [next] <{HasNext: ii }:PrimitiveTypes::Boolean as next

        Print: "1 second pause";

    } while [next] <{HasNext: i}:PrimitiveTypes::Boolean as next
    end;
}

Traffic::Crossroad::justDoIt activity {
    start;
        Call: metaCreateCrossroadInstance >new crossroadInstance:CrossroadInstance;
        do {
            Iterate: [signalDef], i >new trafficLight:TrafficLight;
            Call: metaCreateTrafficLightInstance <trafficLight as context, >new trafficLightInstance:TrafficLightInstance;
            WriteStructuralFeatureValue: signals <crossroadInstance as context, <trafficLightInstance;
            do {
                Iterate: [type.lights], ii <trafficLight as context, >new signalLightType:SignalLightType;
                Call: metaCreateSignalLightInstance <signalLightType as context, >new signalLight:SignalLightInstance;
                WriteStructuralFeatureValue: lights <trafficLightInstance as context, <signalLight;
                Call: switchOff <signalLight as context;
            } while [next] <{HasNext: ii}:Boolean as next
        } while [next] <{HasNext: i}:Boolean as next
        Call: justDoIt <crossroadInstance as context;
    end;
}