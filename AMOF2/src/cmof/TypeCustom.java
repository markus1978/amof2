package cmof;

import hub.sam.mof.instancemodel.ClassInstance;
import hub.sam.mof.reflection.*;

public class TypeCustom extends cmof.TypeDlg {
    
    @Override
	public boolean conformsTo(core.abstractions.typedelements.Type other) {    	
        return self == other;
    }
}

