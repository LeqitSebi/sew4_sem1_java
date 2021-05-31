package ue05_eieruhr;

public class Beeper implements IBeeper {

    private boolean isBeeping = false;

    @Override
    public void beep(boolean isBeeping) {
        this.isBeeping = isBeeping;
    }

    public boolean isBeeping() {
        return isBeeping;
    }

    @Override
    public String toString() {
        return (isBeeping) ? "{BEEPING}" : "{SILENT}";
    }

}
