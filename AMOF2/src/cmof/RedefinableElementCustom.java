package cmof;

import core.abstractions.redefinitions.RedefinableElement;
import hub.sam.mof.instancemodel.ClassInstance;
import hub.sam.mof.reflection.*;

public class RedefinableElementCustom extends cmof.RedefinableElementDlg {
    
    @Override
	public boolean isConsistentWith(RedefinableElement redefinee) {   
        if (self == redefinee) {            
            return true;
        } else {
            if (self instanceof cmof.TypedElement && redefinee instanceof cmof.TypedElement) {
                boolean result = true;                    
                result &= ((cmof.TypedElement)self).getType().conformsTo(((cmof.TypedElement)redefinee).getType());
                if (self instanceof MultiplicityElement && redefinee instanceof cmof.TypedElement) {
                    result &= ! ((MultiplicityElement)self).isOrdered() && ! ((MultiplicityElement)redefinee).isOrdered();  
                }
                return result;
            } else {
                return false;
            }
        }
    }
}
