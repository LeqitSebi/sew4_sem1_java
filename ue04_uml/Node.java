package ue04_uml;

public class Node {

    private boolean state;

    protected Node() {
        this.state = false;
    }

    protected Node(boolean state) {
        this.state = state;
    }

    protected void setState(boolean state) {
        this.state = state;
    }

    protected boolean getState() {
        return state;
    }

}
