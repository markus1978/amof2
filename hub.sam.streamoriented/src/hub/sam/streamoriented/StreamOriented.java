package hub.sam.streamoriented;

import hub.sam.mas.AbstractRunModelMain;
import hub.sam.mof.management.MofModel;
import hub.sam.streamoriented.streamoriented.Sink;
import hub.sam.streamoriented.streamoriented.Source;
import hub.sam.streamoriented.streamoriented.StaLta;
import hub.sam.streamoriented.streamoriented.streamorientedFactory;

public class StreamOriented extends AbstractRunModelMain {
	
	public StreamOriented() {
		super("models/streamoriented.masctx", "Package:streamoriented","hub.sam.streamoriented");
	}	
	
	public void execute() throws Exception {
		initialise();                
        MofModel m1Model = manager.getM1Model();
        
        streamorientedFactory factory = (streamorientedFactory)m1Model.getFactory();
        Source source = factory.createSensorSource();
        Sink sink = factory.createDetectionWarning();
        StaLta filter = factory.createStaLta();
        filter.setStaTime(3);
        filter.setLtaTime(20);
        filter.setSource(source);
        sink.setSource(filter);
        
        sink.consume();
	}
	
	public static void main(String[] args) throws Exception {
		new StreamOriented().execute();
	}
        	
}
