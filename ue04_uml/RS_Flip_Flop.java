package ue04_uml;

/**
 * The type Rs flip flop.
 */
public class RS_Flip_Flop extends Circuit {

    /**
     * The State.
     */
    Statemachine state = Statemachine.Invalid;

    /**
     * Instantiates a new Circuit.
     *
     * @param inputs the inputs
     */
    public RS_Flip_Flop(boolean[] inputs) {
        super(inputs);
        this.outputs = new boolean[2];
    }

    /**
     * Calc Method
     */
    @Override
    public void calc() {
        if (this.inputs[0] && !this.inputs[1]) {
            state = Statemachine.Q_True;
            this.outputs[0] = true;
            this.outputs[1] = false;
        } else if (!this.inputs[0] && this.inputs[1]) {
            state = Statemachine.Q_False;
            this.outputs[0] = false;
            this.outputs[1] = true;
        } else if (!this.inputs[0] && !this.inputs[1]) {
            state = Statemachine.Invalid;
        } else if (state == Statemachine.Invalid) {
            throw new IllegalArgumentException("Not a valid Input");
        }
    }

    /**
     * Set output.
     *
     * @param outputs the outputs
     */
    public void setOutput(int outputs) {
        this.outputs = new boolean[outputs];
    }

    /**
     * Get output boolean.
     *
     * @return the boolean
     */
    public boolean getOutput() {
        return this.outputs[0];
    }
}
