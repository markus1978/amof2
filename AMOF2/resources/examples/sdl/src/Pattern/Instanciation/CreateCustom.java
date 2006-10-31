package Pattern.Instanciation;

import InfrastructureLibrary.Core.Abstractions.Classifiers.Classifier;
import InfrastructureLibrary.Core.Abstractions.BehavioralFeatures.BehavioralFeature;
import InfrastructureLibrary.Core.Abstractions.BehavioralFeatures.Parameter;
import InfrastructureLibrary.Core.Abstractions.StructuralFeatures.StructuralFeature;
import cmof.common.ReflectiveSequence;
import Pattern.Evaluation.Expression;
import Pattern.Evaluation.Evaluation;

public class CreateCustom extends CreateDlg {

    @Override
    public Instance create(Instance context) {
        Classifier classifier = self.getClassifier();
        Instance result = classifier.instanciate();
        BehavioralFeature feature = self.getBehavioralFeature();

        ReflectiveSequence<? extends Parameter> parameters = feature.getParameter();
        ReflectiveSequence<? extends Expression> arguments = self.getArgument();
        int parameterCount = parameters.size();
        for (int i = 0; i < parameterCount; i++) {
            Parameter parameter = parameters.get(i);
            Expression argument = arguments.get(i);
            Evaluation eval = argument.instantiate();
            eval.updateContext(context);
            Value value = eval.getValue();
            result.getSlot((StructuralFeature)parameter).updateValue(value);            
        }

        return result;
    }
}
