package ue04_uml;

public abstract class Component {

    protected Input[] inputs;
    protected Output[] outputs;

    protected Component(int inputNum, int outputNum) {
        inputs = new Input[inputNum];
        for (int i = 0; i < inputNum; i++) {
            inputs[i] = new Input();
        }
        outputs = new Output[outputNum];
        for (int i = 0; i < outputNum; i++) {
            outputs[i] = new Output();
        }
    }

    public Input getInput(int n) {
        if (n >= 0 && n < inputs.length) {
            return inputs[n];
        } else {
            throw new IllegalArgumentException("Invalid input index");
        }
    }

    public Output getOutput(int n) {
        if (n >= 0 && n < outputs.length) {
            return outputs[n];
        } else {
            throw new IllegalArgumentException("Invalid output index");
        }
    }

    abstract protected void calcState();

    protected void pushState() {
        for (Output out : outputs) {
            out.pushState();
        }
    }

}
