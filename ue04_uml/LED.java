package ue04_uml;

/**
 * The type Led.
 */
public class LED extends Circuit {
    /**
     * The State.
     */
    boolean state;

    /**
     * Instantiates a new Led.
     *
     * @param inputs inputs
     */
    public LED(boolean[] inputs) {
        super(inputs);
        this.outputs = new boolean[1];
        state = inputs[0];
    }

    /**
     * SetState boolean.
     *
     * @param state the state
     */
    public void setState(boolean state) {
        this.state = state;
    }

    /**
     * Get state led boolean.
     *
     * @return the boolean
     */
    public boolean getStateLED() {
        return this.state;
    }

    /**
     * Calc Method
     */
    @Override
    public void calc() {
        this.outputs[0] = state;
    }
}
