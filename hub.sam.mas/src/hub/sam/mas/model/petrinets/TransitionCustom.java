package hub.sam.mas.model.petrinets;

import cmof.exception.ModelException;

public class TransitionCustom extends TransitionDlg {

	@Override
	public boolean isEnabled(NetInstance context) {
		for (Place prePlace: self.getInputPlaces()) {
			if (context.getPlaces(prePlace).getTokens() == 0) {
				return false;
			}
		}
		return true;
	}

	
	@Override
	public void fire(NetInstance context) {	
		for (Place prePlace: self.getInputPlaces()) {
			if (context.getPlaces(prePlace).getTokens() <= 0) {
				throw new ModelException();
			}
			context.getPlaces(prePlace).setTokens(context.getPlaces(prePlace).getTokens() - 1);
		}
		for (Place postPlace: self.getOutputPlaces()) {
			context.getPlaces(postPlace).setTokens(context.getPlaces(postPlace).getTokens() + 1);
		}
	}
}
