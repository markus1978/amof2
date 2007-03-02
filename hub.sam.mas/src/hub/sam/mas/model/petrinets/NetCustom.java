package hub.sam.mas.model.petrinets;

public class NetCustom extends NetDlg {

	@Override
	public NetInstance instantiate() {
		NetInstance result = self.metaCreateNetInstance();
		for (Place place: self.getPlaces()) {
			PlaceInstance placeInstance = place.metaCreatePlaceInstance();
			placeInstance.setTokens(place.getInitialTokesn());
			result.setPlaces(place, placeInstance);
		}
		return result;
	}
	
}
