package Traffic;

import cmof.reflection.Extent;
import hub.sam.mof.reflection.ExtentImpl;
import hub.sam.mof.Repository;
import hub.sam.mof.as.layers.MultiLevelImplementationsManager;

/**
 * Created by IntelliJ IDEA.
 * User: scheidge
 * Date: Apr 7, 2006
 * Time: 1:12:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class Simulator {

    public static void main(String[] args) {
        Repository repo = Repository.getLocalRepository();
        Extent trafficM2 = Traffic.TrafficModel.createModel();
        Extent trafficM1M0 = repo.createExtent("extent");
        cmof.Package trafficPackage = (cmof.Package)trafficM2.query("Package:Traffic");
        ((ExtentImpl)trafficM1M0).setCustomImplementationsManager(new MultiLevelImplementationsManager(
                repo.createFactory(trafficM1M0, trafficPackage)));

        TrafficFactory trafficFactory = (TrafficFactory)repo.createFactory(trafficM1M0, trafficPackage);

        Crossroad crossroad = createSampleModel(trafficFactory);

        //while (true) {
            crossroad.justDoIt();
        //}

        //repo.loadXmiIntoExtent(trafficM1M0, trafficPackage, "test.xml");
        //repo.writeExtentToXmi("test-out.xml", trafficPackage, trafficM1M0);
        //((Java.UmlClass)trafficM1M0.query("Class:c1")).runMain();

    }

    public static Crossroad createSampleModel(TrafficFactory trafficFactory) {
        Crossroad crossroad = trafficFactory.createCrossroad();

        SignalLightType red = trafficFactory.createSignalLightType();
        red.setColor(Color.RED);

        SignalLightType green = trafficFactory.createSignalLightType();
        green.setColor(Color.GREEN);

        TrafficLightType walkLight = trafficFactory.createTrafficLightType();
        walkLight.getLights().add(red);
        walkLight.getLights().add(green);


        SignalLightType yellow = trafficFactory.createSignalLightType();
        yellow.setColor(Color.YELLOW);


        TrafficLightType carLight = trafficFactory.createTrafficLightType();
        carLight.getLights().add(red);
        carLight.getLights().add(yellow);
        carLight.getLights().add(green);

        TrafficLight light1 = trafficFactory.createTrafficLight();
        light1.setType(carLight);
        light1.setLightId(1);
        crossroad.getSignalDef().add(light1);
        TrafficLight light2 = trafficFactory.createTrafficLight();
        light2.setType(walkLight);
        light2.setLightId(2);
        crossroad.getSignalDef().add(light2);
        TrafficLight light3 = trafficFactory.createTrafficLight();
        light3.setType(carLight);
        light3.setLightId(3);
        crossroad.getSignalDef().add(light3);
        TrafficLight light4 = trafficFactory.createTrafficLight();
        light4.setType(walkLight);
        light4.setLightId(4);
        crossroad.getSignalDef().add(light4);

        SequenceColumn sequenceColumn1 = trafficFactory.createSequenceColumn();
        sequenceColumn1.setDuration(1000);
        crossroad.getLightSequence().add(sequenceColumn1);
        SequenceEntry sequenceEntry11 = trafficFactory.createSequenceEntry();
        sequenceEntry11.setColor(Color.RED);
        sequenceEntry11.setSignal(light1);
        sequenceColumn1.getEntries().add(sequenceEntry11);
        SequenceEntry sequenceEntry12 = trafficFactory.createSequenceEntry();
        sequenceEntry12.setColor(Color.GREEN);
        sequenceEntry12.setSignal(light2);
        sequenceColumn1.getEntries().add(sequenceEntry12);
        SequenceEntry sequenceEntry13 = trafficFactory.createSequenceEntry();
        sequenceEntry13.setColor(Color.RED);
        sequenceEntry13.setSignal(light3);
        sequenceColumn1.getEntries().add(sequenceEntry13);
        SequenceEntry sequenceEntry14 = trafficFactory.createSequenceEntry();
        sequenceEntry14.setColor(Color.GREEN);
        sequenceEntry14.setSignal(light4);
        sequenceColumn1.getEntries().add(sequenceEntry14);

        SequenceColumn sequenceColumn2 = trafficFactory.createSequenceColumn();
        sequenceColumn2.setDuration(1000);
        crossroad.getLightSequence().add(sequenceColumn2);
        SequenceEntry sequenceEntry21 = trafficFactory.createSequenceEntry();
        sequenceEntry21.setColor(Color.RED);
        sequenceEntry21.setSignal(light1);
        sequenceColumn2.getEntries().add(sequenceEntry21);
        SequenceEntry sequenceEntry22 = trafficFactory.createSequenceEntry();
        sequenceEntry22.setColor(Color.RED);
        sequenceEntry22.setSignal(light2);
        sequenceColumn2.getEntries().add(sequenceEntry22);
        SequenceEntry sequenceEntry23 = trafficFactory.createSequenceEntry();
        sequenceEntry23.setColor(Color.RED);
        sequenceEntry23.setSignal(light3);
        sequenceColumn2.getEntries().add(sequenceEntry23);
        SequenceEntry sequenceEntry24 = trafficFactory.createSequenceEntry();
        sequenceEntry24.setColor(Color.RED);
        sequenceEntry24.setSignal(light4);
        sequenceColumn2.getEntries().add(sequenceEntry24);

        SequenceColumn sequenceColumn3 = trafficFactory.createSequenceColumn();
        sequenceColumn3.setDuration(1000);
        crossroad.getLightSequence().add(sequenceColumn3);
        SequenceEntry sequenceEntry31 = trafficFactory.createSequenceEntry();
        sequenceEntry31.setColor(Color.YELLOW);
        sequenceEntry31.setSignal(light1);
        sequenceColumn3.getEntries().add(sequenceEntry31);
        SequenceEntry sequenceEntry32 = trafficFactory.createSequenceEntry();
        sequenceEntry32.setColor(Color.RED);
        sequenceEntry32.setSignal(light2);
        sequenceColumn3.getEntries().add(sequenceEntry32);
        SequenceEntry sequenceEntry33 = trafficFactory.createSequenceEntry();
        sequenceEntry33.setColor(Color.YELLOW);
        sequenceEntry33.setSignal(light3);
        sequenceColumn3.getEntries().add(sequenceEntry33);
        SequenceEntry sequenceEntry34 = trafficFactory.createSequenceEntry();
        sequenceEntry34.setColor(Color.RED);
        sequenceEntry34.setSignal(light4);
        sequenceColumn3.getEntries().add(sequenceEntry34);

        SequenceColumn sequenceColumn4 = trafficFactory.createSequenceColumn();
        sequenceColumn4.setDuration(1000);
        crossroad.getLightSequence().add(sequenceColumn4);
        SequenceEntry sequenceEntry41 = trafficFactory.createSequenceEntry();
        sequenceEntry41.setColor(Color.GREEN);
        sequenceEntry41.setSignal(light1);
        sequenceColumn4.getEntries().add(sequenceEntry41);
        SequenceEntry sequenceEntry42 = trafficFactory.createSequenceEntry();
        sequenceEntry42.setColor(Color.RED);
        sequenceEntry42.setSignal(light2);
        sequenceColumn4.getEntries().add(sequenceEntry42);
        SequenceEntry sequenceEntry43 = trafficFactory.createSequenceEntry();
        sequenceEntry43.setColor(Color.GREEN);
        sequenceEntry43.setSignal(light3);
        sequenceColumn4.getEntries().add(sequenceEntry43);
        SequenceEntry sequenceEntry44 = trafficFactory.createSequenceEntry();
        sequenceEntry44.setColor(Color.RED);
        sequenceEntry44.setSignal(light4);
        sequenceColumn4.getEntries().add(sequenceEntry44);
        return crossroad;
    }
}
