package ue04_uml;

/**
 * The type Logicboard.
 */
public class Logicboard {
    /**
     * The T 1.
     */
    public Taster t1;
    /**
     * The T 2.
     */
    public Taster t2;
    /**
     * The F lip flop 1.
     */
    public RS_Flip_Flop FLipFlop1;
    /**
     * The Led 1.
     */
    public LED Led1;

    /**
     * Instantiates a new Logicboard.
     *
     * @param t1 the t 1
     * @param t2 the t 2
     */
    public Logicboard(Taster t1, Taster t2) {
        this.t1 = t1;
        this.t2 = t2;
        FLipFlop1 = new RS_Flip_Flop(new boolean[2]);
        Led1 = new LED(new boolean[]{false});
    }

    /**
     * Calc.
     */
    public void calc() {
        FLipFlop1.inputs = new boolean[]{t1.getState(), t2.getState()};
        FLipFlop1.calc();
        Led1.setState(FLipFlop1.outputs[0]);
    }
}
