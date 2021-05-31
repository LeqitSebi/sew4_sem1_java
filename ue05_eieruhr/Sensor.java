package ue05_eieruhr;

public class Sensor implements ISensor {

    private boolean isTriggered = false;

    @Override
    public void trigger() {
        isTriggered = true;
    }

    @Override
    public boolean pullStatus() {
        boolean t = isTriggered;
        isTriggered = false;
        return t;
    }
}
