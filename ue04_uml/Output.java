package ue04_uml;

public class Output extends Node {

    private Input link;

    protected void connectTo(Input input) {
        link = input;
    }

    protected void disconnect() {
        link = null;
    }

    protected void pushState() {
        if (link != null) {
            link.setState(getState());
        }
    }

}
