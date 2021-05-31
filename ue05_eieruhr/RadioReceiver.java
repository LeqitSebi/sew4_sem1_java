package ue05_eieruhr;

public class RadioReceiver implements IRadioReceiver {


    @Override
    public boolean hasConnection() {
        return true;
    }

    @Override
    public long getTime() {
        return System.currentTimeMillis();
    }

}
