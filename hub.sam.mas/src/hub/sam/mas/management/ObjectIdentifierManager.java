package hub.sam.mas.management;

import hub.sam.mas.model.mas.ObjectIdentifier;
import hub.sam.mof.management.MofModel;
import cmof.UmlClass;

public class ObjectIdentifierManager {
    
    private int id = 0;
    
    public ObjectIdentifierManager(MofModel masModel) {
        UmlClass objectClass = (UmlClass) masModel.getMetaModel().getExtent().query("Package:mas/Class:ObjectIdentifier");
        for (Object obj: masModel.getExtent().objectsOfType(objectClass, false)) {
            ObjectIdentifier object = (ObjectIdentifier) obj;
            int objectId;
            try {
                objectId = Integer.parseInt(object.getIdentifier());
            }
            catch (NumberFormatException e) {
                continue;
            }
            id = Math.max(id, objectId);
        }
    }
    
    public void addIdentifier(ObjectIdentifier object, String identifier) {
        object.setIdentifier(identifier);
    }

    public String addIdentifier(ObjectIdentifier object) {
        id++;
        String idAsString = Integer.toString(id);
        object.setIdentifier(idAsString);
        return idAsString;
    }
    
    public void removeIdentifier(ObjectIdentifier object) {
        object.setIdentifier(null);
    }
    
}
