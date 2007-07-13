package hub.sam.streamoriented.streamoriented;

import java.util.Random;


public class SensorSourceCustom extends SensorSourceDlg {

	private static final int[] sensorValues = { 1,   1,  1,   1, 1, 1, 1, 1, 1, 1,
																	1,   1,  1,   1, 1, 1, 1, 1, 1, 1,
																	132, 145, 170, 160, 40, 1, 1, 1, 1, 1,
																	1,   1,  1,   1, 1, 1, 1, 1, 1, 1 };
	
	private static int index = 0;
	private static Random random = new Random();
	
	@Override
	public java.lang.Object getNextValue() {
		System.out.print(".");		
		int randomInt = random.nextInt(20);
		if (randomInt == 1) {
			index = 0;
		}
		if (index < sensorValues.length) {
			return sensorValues[index++];
		} else {
			return 1;
		}
	}

}
