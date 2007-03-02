package Traffic;

public class SignalLightInstanceCustom extends SignalLightInstanceDlg {
    @Override
    public void justDoIt() {
        if (isOn()) {
            switchOff();
        } else {
            switchOn();
        }
    }

    @Override
    public void switchOn() {
        setIsOn(true);
        System.out.println("ON: " + getMetaClassifierSignalLightType().getColor() + ":" +
                getTrafficLight().getMetaClassifierTrafficLight().getLightId());
    }

    @Override
    public void switchOff() {
        setIsOn(false);
        System.out.println("OFF: " + getMetaClassifierSignalLightType().getColor() + ":" +
                getTrafficLight().getMetaClassifierTrafficLight().getLightId());
    }
}
