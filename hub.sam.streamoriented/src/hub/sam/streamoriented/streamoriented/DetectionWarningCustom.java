package hub.sam.streamoriented.streamoriented;

public class DetectionWarningCustom extends DetectionWarningDlg {

	@Override
	public void consume() {
		System.out.println("Started detection");
		while (true) {
			getSource().getNextValue();
			System.out.println("Warning!!!");
		}
	}

}
