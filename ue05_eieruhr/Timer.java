package ue05_eieruhr;

public class Timer implements ITimer {

    private IController controller;

    void setController(IController c) {
        controller = c;
    }

    public void tick() {
        controller.tick();
    }

    public void tick(int n) {
        for (int i = 0; i < n; i++) {
            this.tick();
        }
    }

    public void tickSeconds(int n) {
        tick(n * ClockController.TICKS_PER_SECOND);
    }

}
