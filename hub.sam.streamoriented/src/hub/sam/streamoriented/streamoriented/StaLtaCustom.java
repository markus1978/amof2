package hub.sam.streamoriented.streamoriented;

public class StaLtaCustom extends StaLtaDlg {

	@Override
	public void consume() {
		if (getSta() == null) {
			// initialisation
			streamorientedFactory factory = self.getExtent().getAdaptor(streamorientedFactory.class);
			MovingAverage sta = factory.createMovingAverage();
			MovingAverage lta = factory.createMovingAverage();
			int ltaTime = self.getLtaTime();
			int staTime = self.getStaTime();
			for (int i = 0; i < ltaTime; i++) {		
				if (i < staTime) {					
					sta.getValues().add((int)1);
				}
				lta.getValues().add((int)1);
			}
			self.setSta(sta);
			self.setLta(lta);
		}
			
		java.lang.Object nextValue = self.getSource().getNextValue();		
		self.getSta().move((java.lang.Integer)nextValue);
		self.getLta().move((java.lang.Integer)nextValue);
	}

	/*
	@Override
	public java.lang.Object getNextValue() {
		while (true) {
			self.consume();			
			if (((java.lang.Integer)getSta().getCurrentAvr()) / ((java.lang.Integer)getLta().getCurrentAvr()) > 4) {
				return new java.lang.Integer(1);
			}
		}
	}
	*/
}
