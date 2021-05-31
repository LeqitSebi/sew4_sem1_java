package ue04_uml;

/**
 * The type Circuit.
 */
public abstract class Circuit {
    /**
     * The Input.
     */
    boolean[] inputs;
    /**
     * The Output.
     */
    boolean[] outputs;

    /**
     * Instantiates a new Circuit.
     *
     * @param inputs the inputs
     */
    public Circuit(boolean[] inputs) {
        this.inputs = inputs;
    }

    /**
     * Get outputs boolean [ ].
     *
     * @return the boolean [ ]
     */
    public boolean[] getOutputs() {
        return outputs;
    }

    /**
     * Calc.
     */
    public abstract void calc();
}
