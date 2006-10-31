package hub.sam.mof.jocl.teststuff;

import cmof.common.ReflectiveCollection;
import cmof.Namespace;
import cmof.NamedElement;
import hub.sam.mof.jocl.standardlib.OclBoolean;

public class Test {

    /**
     * Some examples. Namespace is a class from the UML 2.0 constructs package. A namespace contains
     * NamedElements as ownedMember.
     */
    public void test(Namespace myNamespace) {

        // For invariants
        // No names in a namespace are the same.
        // OCL: self.ownedElement->forAll(n|self.ownedElement->forAll(m|not n.name = m.name))
        {
            NamespaceValue self = new NamespaceValue(myNamespace);
            NamedElementValue n = new NamedElementValue();
            NamedElementValue m = new NamedElementValue();
            OclBoolean expr = self.ownedMember().forAll(n,
                    self.ownedMember().forAll(m, n.name().oclEquals(m.name()).not()));
            boolean result = expr.javaValue();
        }

        // the same in plain Java:
        {
            boolean result = true;
            Loop: for(NamedElement ownedElement1: myNamespace.getOwnedMember()) {
                String name1 = ownedElement1.getName();
                for(NamedElement ownedElement2: myNamespace.getOwnedMember()) {
                    if (!name1.equals(ownedElement2.getName())) {
                        result = false;
                        break Loop;
                    }
                }
            }
        }

        // As genral queries
        // OCL: self.ownedElement->collect(n|n.name)
        {
            NamespaceValue self = new NamespaceValue(myNamespace);
            NamedElementValue n = new NamedElementValue();
            ReflectiveCollection<String> names = self.ownedMember().collect(n, n.name()).javaValue();
        }
    }
}
