package hub.sam.mof.petrinets;


public interface petrinetsFactory extends cmof.reflection.Factory {

    public hub.sam.mof.petrinets.Place createPlace();

    public hub.sam.mof.petrinets.Transition createTransition();

    public hub.sam.mof.petrinets.Net createNet();

    public hub.sam.mof.petrinets.NetInstance createNetInstance();

    public hub.sam.mof.petrinets.PlaceInstance createPlaceInstance();

}

