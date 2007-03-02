package hub.sam.mof.test.as;

import hub.sam.mof.util.AssertionException;
import core.abstractions.instances.InstanceSpecification;
import Instances.Classifier;
import Instances.InstanceDef;
import StateAutomaton.Semantics.AutomatonInstance;
import StateAutomaton.Semantics.ComponentInstance;
import StateAutomaton.Semantics.PortInstance;
import StateAutomaton.Semantics.SemanticsFactory;
import StateAutomaton.Semantics.SignalInstance;
import StateAutomaton.Semantics.SystemInstance;
import StateAutomaton.Syntax.Automaton;
import StateAutomaton.Syntax.Port;
import StateAutomaton.Syntax.Signal;
import StateAutomaton.Syntax.System;

public class InstanceFactory {

	private static InstanceFactory singleton = null;

	public static void initialize(SemanticsFactory factory) {
		singleton = new InstanceFactory(factory);
	}

	public static InstanceFactory getFactory() {
		return singleton;
	}

	private SemanticsFactory factory = null;

	private InstanceFactory(SemanticsFactory factory) {
		this.factory = factory;
	}

	public InstanceSpecification createInstance(Classifier classifier) {
		InstanceSpecification result = createMyInstance(classifier);

		if (result instanceof AutomatonInstance) {
			AutomatonInstance automaton = (AutomatonInstance)result;
			for (int i = 0; i < ((Automaton)classifier).getInitial(); i++) {
				automaton.create();
			}
		}

		for (InstanceDef implicitInstancesDef: classifier.getOwnedInstance()) {
			InstanceSpecification implicitInstance = createInstance(implicitInstancesDef);
			implicitInstancesDef.setImplicitInstance(implicitInstance);
			implicitInstance.setName(implicitInstancesDef.getName());
			if (result instanceof ComponentInstance && implicitInstance instanceof PortInstance) {
				((ComponentInstance)result).getPort().add(implicitInstance);
			}
		}

		return result;
	}

	protected InstanceSpecification createMyInstance(core.abstractions.classifiers.Classifier classifier) {
		InstanceSpecification result = null;
		if (classifier instanceof Signal) {
			result = factory.createSignalInstance();
		} else if (classifier instanceof Automaton) {
			result = factory.createAutomatonInstance();
		} else if (classifier instanceof Port) {
			result = factory.createPortInstance();
		} else if (classifier instanceof System) {
			result = factory.createSystemInstance();
		} else {
			throw new AssertionException();
		}
		result.getClassifier().add(classifier);
		return result;
	}

	public SignalInstance createSignalInstance(Signal classifier) {
		SignalInstance result = (SignalInstance)createInstance(classifier);
		return result;
	}

	public SystemInstance createSystemInstance(System classifier) {
		SystemInstance result = (SystemInstance)createInstance(classifier);
		return result;
	}

	public SemanticsFactory getSemanticsFactory() {
		return factory;
	}
}
