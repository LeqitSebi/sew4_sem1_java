package ue05_eieruhr;

public class Button implements IButton {

    private boolean wasPressed = false;
    private boolean isPressed = false;

    @Override
    public void press() {
        isPressed = true;
        wasPressed = true;
    }

    @Override
    public void release() {
        isPressed = false;
    }

    @Override
    public boolean isPressed() {
        return isPressed;
    }

    @Override
    public boolean pullStatus() {
        boolean b = wasPressed;
        wasPressed = false;
        return b;
    }

    @Override
    public String toString() {
        return (isPressed) ? "{PRESSED}" : "{RELEASED}";
    }

}
