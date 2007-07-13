package hub.sam.streamoriented.streamoriented;

public class StaLtaCustom extends StaLtaDlg {

	@Override
	public void consume(Object o) {
		if (getSta() == null) {
			// initialisation			
			streamorientedFactory factory = self.getExtent().getAdaptor(streamorientedFactory.class);
			MovingAverage sta = factory.createMovingAverage();
			MovingAverage lta = factory.createMovingAverage();
			int ltaTime = self.getLtaTime();
			int staTime = self.getStaTime();
			sta.setTime(staTime);
			lta.setTime(ltaTime);			
			self.setSta(sta);
			self.setLta(lta);
		}
					
		self.getSta().move((java.lang.Integer)o);
		self.getLta().move((java.lang.Integer)o);
		
		if (self.getSta().getCurrentAvr() / self.getLta().getCurrentAvr() > 4) {
			self.getSink().consume(true);
		}
	}	
}
