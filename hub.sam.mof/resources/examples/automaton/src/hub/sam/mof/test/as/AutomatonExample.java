package hub.sam.mof.test.as;

import StateAutomaton.Semantics.PortInstance;
import StateAutomaton.Semantics.SemanticsFactory;
import StateAutomaton.Semantics.SignalInstance;
import StateAutomaton.Semantics.SystemInstance;
import StateAutomaton.Syntax.Port;
import StateAutomaton.Syntax.Signal;
import StateAutomaton.Syntax.System;
import cmof.common.ReflectiveSequence;
import cmof.reflection.Extent;

public class AutomatonExample {

    private PortInstance envInPort = null;
    private PortInstance envOutPort = null;
    private final Signal signalA;
    private final Signal signalB;

    public AutomatonExample(Extent m1, SemanticsFactory semanticFactory) {
        System theExampleSystem = (System)m1.query("System:MySystem");
        this.signalA = (Signal)m1.query("System:MySystem/Signal:a");
        this.signalB = (Signal)m1.query("System:MySystem/Signal:b");
        Port envInPort = (Port)m1.query("System:MySystem/Port:EnvIn");
        Port envOutPort = (Port)m1.query("System:MySystem/Port:EnvOut");

        InstanceFactory.initialize(semanticFactory);
        InstanceFactory instanceFactory = InstanceFactory.getFactory();
        SystemInstance systemInstance = instanceFactory.createSystemInstance(theExampleSystem);

        for (PortInstance portInstance: systemInstance.getPort()) {
            if (portInstance.getClassifier().contains(envInPort)) {
                this.envInPort = portInstance;
            } else if (portInstance.getClassifier().contains(envOutPort)){
                this.envOutPort = portInstance;
            }
        }
    }

    public void sendSignal(SignalInstance signal) {
        signal.setDestinationId(-1);
        signal.setSourceId(-1);
        envInPort.getSignal().add(signal);
        envInPort.send();
    }

    public void sendSignalA() {
        SignalInstance instance = InstanceFactory.getFactory().createSignalInstance(signalA);
        sendSignal(instance);
    }

    public void sendSignalB() {
        SignalInstance instance = InstanceFactory.getFactory().createSignalInstance(signalB);
        sendSignal(instance);
    }

    public void receiveSignals() {
        ReflectiveSequence<? extends SignalInstance> signals = envOutPort.getSignal();
        int size = signals.size();
        for (int i = 0; i < size; i++) {
            SignalInstance signal = signals.get(0);
            signals.remove(0);
            java.lang.System.out.println("Received Signal of type: " + signal.getClassifier().iterator().next().getName());
            ((cmof.reflection.Object)signal).delete();
        }
        java.lang.System.out.println("Env out port empty.");
    }
}
