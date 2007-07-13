package hub.sam.streamoriented.streamoriented;

public class DetectionWarningCustom extends DetectionWarningDlg {

	@Override
	public void consume(Object o) {
		System.out.println("Warning!!!");
	}

}
