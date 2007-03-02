package hub.sam.mof.jocl.teststuff;

import cmof.Namespace;
import cmof.NamedElement;
import hub.sam.mof.jocl.standardlib.OclCollection;
import hub.sam.mof.jocl.standardlib.OclModelElement;


public class NamespaceValue extends OclModelElement<Namespace> /* implements Namespace*/ {
    public NamespaceValue(Namespace value) {
        super(value);
    }

    public NamespaceValue() {
        super(null);
    }

    public OclCollection<NamedElementValue,NamedElement> ownedMember() {
        return null;
    }
}
