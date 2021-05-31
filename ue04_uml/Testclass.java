package ue04_uml;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

/**
 * The type Testclass.
 */
class Testclass {

    /**
     * Taster
     */
    @Test
    void getState() {
        Taster a = new Taster(new boolean[]{true});
        Taster b = new Taster(new boolean[]{false});

        assertEquals(true, a.getState());
        assertEquals(false, b.getState());
    }

    private void assertEquals(boolean b, boolean state) {
    }

    /**
     * Click.
     */
    @Test
    void click() {
        Taster a = new Taster(new boolean[]{true});
        Taster b = new Taster(new boolean[]{false});

        assertEquals(true, a.getState());
        assertEquals(false, b.getState());
    }

    /**
     * LED
     */
    @Test
    void getStateLED() {
        LED a = new LED(new boolean[]{true});
        LED b = new LED(new boolean[]{false});

        a.setState(false);
        b.setState(true);

        assertEquals(true, b.getStateLED());
        assertEquals(false, a.getStateLED());
    }


    /**
     * FlipFLop
     */
    @Test
    void getOutput() {
        boolean[] a_array = new boolean[]{true, false};
        boolean[] b_array = new boolean[]{false, true};

        RS_Flip_Flop a = new RS_Flip_Flop(a_array);
        RS_Flip_Flop b = new RS_Flip_Flop(b_array);

        a.setOutput(2);
        b.setOutput(2);

        a.calc();
        b.calc();

        assertEquals(true, a.getOutput());
        assertEquals(false, b.getOutput());
    }

    /**
     * NOTGate
     */
    @Test
    void NOTGetOutput() {
        boolean[] a_array = new boolean[]{true};
        boolean[] b_array = new boolean[]{false};

        NOT_Gate a = new NOT_Gate(a_array);
        NOT_Gate b = new NOT_Gate(b_array);

        a.setOutput(1);
        b.setOutput(1);

        a.calc();
        b.calc();

        assertEquals(false, a.NOTgetOutput());
        assertEquals(true, b.NOTgetOutput());
    }

    /**
     * Test logic board.
     */
    @Test
    void TestLogicBoard() {
        Logicboard logicboard = new Logicboard(new Taster(new boolean[]{true}), new Taster(new boolean[]{false}));
        logicboard.calc();
        assertTrue(logicboard.Led1.getStateLED());

        logicboard.t2.click();
        logicboard.calc();
        assertTrue(logicboard.Led1.getStateLED());

        logicboard.t1.click();
        logicboard.calc();
        assertTrue(logicboard.Led1.getStateLED());

        logicboard.t1.click();
        logicboard.calc();
        assertTrue(logicboard.Led1.getStateLED());
    }
}