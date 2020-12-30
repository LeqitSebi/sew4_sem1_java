package ue04_uml;

public class Button extends Component {

    public static int OUT = 0;

    private boolean state = false;

    public Button() {
        super(0, 1);
    }

    @Override
    protected void calcState() {
        getOutput(OUT).setState(state);
    }

    @Override
    protected void pushState() {
        getOutput(OUT).pushState();
    }

    private void setPressed(boolean state) {
        this.state = state;
    }

    public void press() {
        setPressed(true);
    }

    public void release() {
        setPressed(false);
    }

    public boolean getState() {
        return state;
    }

}
