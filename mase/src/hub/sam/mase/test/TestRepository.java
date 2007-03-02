package hub.sam.mase.test;

import hub.sam.mas.model.mas.*;

import java.io.*;

import org.apache.log4j.PropertyConfigurator;

public class TestRepository {

    public static void main(java.lang.String[] args) {
        PropertyConfigurator.configure("resources/log4j/mase.properties");
        
        FileInputStream fileStream = null;
        try {
            fileStream = new FileInputStream("resources/models/bootstrap_merged.xml");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (fileStream != null) {
            //MaseRepository.init(fileStream);
            
            /*Activity activity1 = MaseCreationFactory.createActivity();
            
            OpaqueAction a1 = MaseCreationFactory.createOpaqueAction();
            a1.setName("a1");
            activity1.getNode().add(a1);
            
            ValueNode v1 = MaseCreationFactory.createValueNode();
            v1.setName("v1");
            activity1.getNode().add(v1);
            
            for(Object o: activity1.getOwnedElement()) {
                if (o instanceof cmof.NamedElement)
                    System.out.println(((cmof.NamedElement) o).getName());
            }

            OpaqueAction a2 = MaseCreationFactory.createOpaqueAction();
            a2.setName("a2");
            activity1.getNode().add(a2);

            for(Object o: activity1.getOwnedElement()) {
                if (o instanceof cmof.NamedElement)
                    System.out.println(((cmof.NamedElement) o).getName());
            }*/
        }
    }
    
    public static void printInput(OpaqueAction a) {
        for(Object obj: a.getInput()) {
            System.out.println(((InputPin) obj).getNum());
        }
    }

}
