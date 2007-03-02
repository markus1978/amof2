package TrafficDomainModel;

public class SignalLightControl {
    public static SignalLightControl createSignalLightControl() {
        return new SignalLightControl();
    }

    private boolean isOn = true;

    public boolean isOn() {
        return isOn;
    }

    public void switchOn() {
        isOn = true;
    }

    public void switchOff() {
        isOn = false;
    }
}
