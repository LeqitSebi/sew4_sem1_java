package ue04_uml;

/**
 * The type Not gate.
 */
public class NOT_Gate extends Circuit{
    /**
     * Instantiates a new Circuit.
     *
     * @param inputs the inputs
     */
    public NOT_Gate(boolean[] inputs) {
        super(inputs);
    }

    @Override
    public void calc() {
        Statemachine_NOT_Gate currentState = Statemachine_NOT_Gate.NoOutput;
        if(this.inputs[0]){
            currentState = Statemachine_NOT_Gate.NoOutput;
            this.outputs[0] = !this.inputs[0];
        }

        if(!this.inputs[0]){
            currentState = Statemachine_NOT_Gate.Output;
            this.outputs[0] = !this.inputs[0];
        }
    }

    /**
     * Set output.
     *
     * @param outputs the outputs
     */
    public void setOutput(int outputs){
        this.outputs = new boolean[outputs];
    }

    /**
     * No tget output boolean.
     *
     * @return the boolean
     */
    public boolean NOTgetOutput(){
        return this.outputs[0];
    }
}
