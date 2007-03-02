package cmof;

import cmof.common.ReflectiveCollection;
import core.abstractions.umlsuper.Classifier;
import hub.sam.mof.instancemodel.ClassInstance;
import hub.sam.mof.reflection.*;
import hub.sam.mof.util.*;

public class ClassifierCustom extends cmof.ClassifierDlg {   
    @Override
	public ReflectiveCollection<? extends Classifier> allParents() {
        ReflectiveCollection<? extends Classifier> result = new SetImpl<Classifier>();
        for (Classifier parent: self.getGeneral()) {
            result.add(parent);
            result.addAll(parent.allParents());
        }
        return result;
    }

    public boolean conformsTo(Classifier other) { 
        return (self == other) || self.allParents().contains(other);
    }
}
