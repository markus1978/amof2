package hub.sam.streamoriented.streamoriented;

import cmof.common.ReflectiveCollection;
import cmof.common.ReflectiveSequence;

public class MovingAverageCustom extends MovingAverageDlg {

	@Override
	public int getCurrentAvr() {
		ReflectiveCollection<java.lang.Integer> values = self.getValues();
		int size = values.size();
		int acc = 0;
		for (java.lang.Object value: values) {
			acc += ((java.lang.Integer)value);
		}
		return new java.lang.Integer(acc/size);
	}
	
	@Override
	public void move(int value) {
		ReflectiveSequence values = self.getValues();
		if (values.size() == self.getTime()) {
			values.remove(0);
		}
		values.add(value);
	}

}
