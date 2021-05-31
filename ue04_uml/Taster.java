package ue04_uml;

/**
 * The type Taster.
 */
public class Taster extends Circuit {
    /**
     * The State.
     */
    private boolean state;

    /**
     * Instantiates a new Taster.
     *
     * @param inputs inputs
     */
    public Taster(boolean[] inputs) {
        super(inputs);
        this.outputs = new boolean[1];
        state = inputs[0];
    }

    /**
     * Get state boolean.
     *
     * @return the boolean
     */
    public boolean getState() {
        return state;
    }

    /**
     * Click boolean.
     */
    public void click() {
        outputs[0] = !this.state;
        calc();
    }

    @Override
    public void calc() {
        this.outputs[0] = state;
    }
}
