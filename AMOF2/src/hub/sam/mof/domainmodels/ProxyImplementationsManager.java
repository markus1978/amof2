package hub.sam.mof.domainmodels;

import hub.sam.mof.reflection.Implementations;
import hub.sam.mof.reflection.ImplementationsManager;
import cmof.UmlClass;
import cmof.Tag;

public class ProxyImplementationsManager implements ImplementationsManager<UmlClass> {

    private ProxyModelContext context;

    public ProxyImplementationsManager(ProxyModelContext context) {
        super();
        this.context = context;
    }

    public Implementations getImplementationsForClassifier(UmlClass metaClass) {
        for (Tag aTag: metaClass.getTag()) {
            if (aTag.getName().equals(JavaModelGenerationDoclet.PROXY_TAG)) {
                return new ProxyObjectImplementations(context);
            } else if (aTag.getName().equals(JavaModelGenerationDoclet.CLASS_PROXY_TAG)) {
                return new ProxyClassImplementations(context);
            }
        }
        return null;
    }
}
