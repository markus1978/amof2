package hub.sam.mas.editor.test;

import hub.sam.mas.model.mas.*;
import hub.sam.mof.Repository;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import cmof.Package;
import cmof.cmofFactory;
import cmof.reflection.Extent;
import cmof.reflection.Object;

public class Test implements PropertyChangeListener {

    private Repository repository;
    private Extent masExtent;
    private Extent modelExtent;
    private Package cmofPackage;

    public static void main(java.lang.String[] args) {
        new Test().run();
    }
    
    public void run() {
        repository = Repository.getLocalRepository();
        Extent m3Extent = repository.getExtent(Repository.CMOF_EXTENT_NAME);
        cmofPackage = (Package) m3Extent.query("Package:cmof");

        masExtent = repository.createExtent("masExtent", m3Extent);

        try {
            repository.loadXmiIntoExtent(masExtent, cmofPackage, "resources/models/mas_merged.xml");
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        
        cmofFactory factory = masExtent.getAdaptor(cmofFactory.class); 
        cmof.Tag nsPrefixTag = factory.createTag();
        nsPrefixTag.setValue("mas");
        nsPrefixTag.setName("org.omg.xmi.nsPrefix");
        ((Package) masExtent.query("Package:mas")).getTag().add(nsPrefixTag);
        
//        modelExtent = repository.createExtent("modelExtent", masExtent);
//        createXmi();
//        repository.deleteExtent("modelExtent");
        
        modelExtent = repository.createExtent("modelExtent2", masExtent);
        test();
    }
    
    public void test() {
        try {
            repository.loadXmiIntoExtent(modelExtent, (Package) masExtent.query("Package:mas"), "resources/test/model.xml");
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        ControlFlow cf = null;
        for (Object obj: modelExtent.getObject()) {
            if (obj instanceof OpaqueAction) {
                obj.addListener(this);
                System.out.println("added listener for " + obj.hashCode());
            }
            else if (obj instanceof ControlFlow) {
                obj.addListener(this);
                cf = (ControlFlow) obj;
            }
        }
        System.out.println(cf.getSource().hashCode());
        cf.setSource(null);
        System.out.println("----");
        masFactory modelFactory = modelExtent.getAdaptor(masFactory.class);
        OpaqueAction oa = modelFactory.createOpaqueAction();
        oa.addListener(this);
        cf.setSource(oa);
    }
    
    public void createXmi() {
        masFactory modelFactory = modelExtent.getAdaptor(masFactory.class);
        
        Activity a = modelFactory.createActivity();
        OpaqueAction oa1 = modelFactory.createOpaqueAction();
        OpaqueAction oa2 = modelFactory.createOpaqueAction();
        ControlFlow cf = modelFactory.createControlFlow();
        a.getNode().add(oa1);
        a.getNode().add(oa2);
        cf.setSource(oa1);
        cf.setTarget(oa2);
        
        try {
            repository.writeExtentToXmi("resources/test/model.xml", (Package) masExtent.query("Package:mas"), modelExtent);
        }
        catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        } 
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName() != null) {
            System.out.println(evt.getPropertyName());
        }
        else {
            System.out.println("null");
        }
    }


}
