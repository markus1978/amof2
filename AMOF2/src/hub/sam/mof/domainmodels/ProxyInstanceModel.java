package hub.sam.mof.domainmodels;

import hub.sam.mof.mofinstancemodel.MofInstanceModel;
import hub.sam.mof.mofinstancemodel.MofClassInstance;
import cmof.UmlClass;

import java.util.HashMap;
import java.util.Map;

public class ProxyInstanceModel extends MofInstanceModel {

    private ProxyModelContext context = null;
    private Map<UmlClass, ProxyClassInstance> classes = new HashMap<UmlClass, ProxyClassInstance>();

    @Override
    public MofClassInstance createAInstance(String id, UmlClass classifier) {
        if (context == null) {
            return super.createAInstance(id, classifier);
        } else {
            if (context.isProxyInstanceClass(classifier)) {
                return new ProxyObjectInstance(classifier, context);
            } else if (context.isProxyClassClass(classifier)) {
                ProxyClassInstance result = classes.get(classifier);
                if (result == null) {
                    result = new ProxyClassInstance(classifier, context);
                    classes.put(classifier, result);
                }
                return result;
            } else {
                return super.createAInstance(id, classifier);
            }
        }
    }

    public void setContext(ProxyModelContext context) {
        this.context = context;
    }
}
