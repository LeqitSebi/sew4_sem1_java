package ue05_eieruhr;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ClockController implements IController {

    public static final int LIGHT_SENSOR_TIMEOUT = 3;
    public static final int TICKS_PER_SECOND = 100;
    public static final int MAX_ALARM_REPEATS = 7;
    public static final int LONG_PRESS_SECONDS = 3;
    public static final int DEFAULT_ALARM_REPEAT_MINUTES = 4;

    public enum Mode {
        NORMAL, HOUR, MINUTE, YEAR, MONTH, DATE, AL1, AL2, AL1HOUR, AL2HOUR,
        AL1MINUTE, AL2MINUTE, AL1REPEATS, AL2REPEATS
    }

    private IDisplay display;
    private IBeeper beeper;
    private IButton buttonMode;
    private IButton buttonPlus;
    private IButton buttonMinus;
    private IButton buttonBell1;
    private IButton buttonBell2;
    private ISensor sensor;
    private IRadioReceiver radioReceiver;
    private ITimer timer;


    private int radioTimeout = 360 * TICKS_PER_SECOND;
    private boolean radioConnection = false;
    private long time = 0;
    private int sensorTimeout = 0;
    private int modeTimeout = 0;
    private Mode mode = Mode.NORMAL;

    private boolean blink = false;
    private int blinkTicksTimeout = TICKS_PER_SECOND;

    private int alarm1 = 6 * 60;  // 06:00
    private int alarm2 = 7 * 60;  // 07:00
    private int alarm1RepeatAfter = DEFAULT_ALARM_REPEAT_MINUTES;  // Minutes
    private int alarm2RepeatAfter = DEFAULT_ALARM_REPEAT_MINUTES;  // Minutes
    private int alarm1RepeatRemaining = 0;
    private int alarm2RepeatRemaining = 0;
    private int alarm1Timeout = 0;
    private int alarm2Timeout = 0;
    private long alarm1LastTime = 0;
    private long alarm2LastTime = 0;
    private boolean alarm1Active = false;
    private boolean alarm2Active = false;
    private boolean alarm1Activated = false;
    private boolean alarm2Activated = false;
    private boolean alarm1Snooze = false;
    private boolean alarm2Snooze = false;


    public ClockController(IDisplay display, IBeeper beeper,
                           IButton buttonMode, IButton buttonPlus, IButton buttonMinus,
                           IButton buttonBell1, IButton buttonBell2, ISensor sensor,
                           IRadioReceiver radioReceiver, ITimer timer) {
        this.display = display;
        this.beeper = beeper;
        this.buttonMode = buttonMode;
        this.buttonPlus = buttonPlus;
        this.buttonMinus = buttonMinus;
        this.buttonBell1 = buttonBell1;
        this.buttonBell2 = buttonBell2;
        this.sensor = sensor;
        this.radioReceiver = radioReceiver;
        this.timer = timer;
    }

    @Override
    public void powerOn() {
        display.showHour(true);
        display.showMinute(true);
        display.showSecond(true);
        display.showMonth(true);
        display.showDate(true);
        display.showMonth(true);
        display.showHeader(true);
        display.showAlarmMinute(true);
        display.showAlarmHour(true);
        display.setHour('1', '2');
        display.setMinute('0', '0');
        display.setSecond('0', '0');
        display.setDate('0', '0');
        display.setMonth('0', '0');
        display.setWeekday('N', 'A');
    }

    @Override
    public void powerOff() {

    }

    private int getHourMinute() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        return cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE);
    }

    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E", Locale.GERMAN);
        String d = sdf.format(new Date(time)).toUpperCase();
        if (mode == Mode.YEAR) {
            display.setHour(d.charAt(0), d.charAt(1));
            display.setMinute(d.charAt(2), d.charAt(3));
        } else {
            display.setHour(d.charAt(11), d.charAt(12));
            display.setMinute(d.charAt(14), d.charAt(15));
        }
        display.setSecond(d.charAt(17), d.charAt(18));
        display.setMonth(d.charAt(5), d.charAt(6));
        display.setDate(d.charAt(8), d.charAt(9));
        display.setWeekday(d.charAt(20), d.charAt(21));
    }

    private long getAlarmDifference(int alarm) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(time));
        cal.set(Calendar.HOUR_OF_DAY, alarm / 60);
        cal.set(Calendar.MINUTE, alarm % 60);
        cal.set(Calendar.SECOND, 0);
        long diff = cal.getTimeInMillis() - time;
        if (diff < 0) {
            cal.add(Calendar.DAY_OF_YEAR, 1);
            diff = cal.getTimeInMillis() - time;
        }
        return diff;
    }

    private void setAlarm(int alarm) {
        int alarmTime;
        if (alarm == 1) {
            alarmTime = alarm1;
            display.showAlarm1(true);
            display.showAlarm2(false);
        } else if (alarm == 2) {
            alarmTime = alarm2;
            display.showAlarm2(true);
            display.showAlarm1(false);
        } else {
            throw new IllegalArgumentException();
        }
        String str = String.format("%02d%02d", alarmTime / 60, alarmTime % 60);
        display.setAlarmHour(str.charAt(0), str.charAt(1));
        display.setAlarmMinute(str.charAt(2), str.charAt(3));
    }

    private void handleRadioReceiver() {
        if (radioConnection) time += 1000 / TICKS_PER_SECOND;
        if (!radioReceiver.hasConnection() && radioTimeout > 0) {
            radioTimeout -= TICKS_PER_SECOND;
        } else {
            radioConnection = true;
            if (time == 0) time = radioReceiver.getTime();
        }
    }

    private void handleSensor() {
        boolean s = sensor.pullStatus();
        if (sensorTimeout > 0) sensorTimeout -= TICKS_PER_SECOND;
        if (s) sensorTimeout = LIGHT_SENSOR_TIMEOUT * TICKS_PER_SECOND;
        display.setLight(sensorTimeout > 0);

        if (alarm1Active && !alarm1Snooze && s) {
            alarm1Snooze = true;
            alarm1RepeatRemaining--;
        }
        if (alarm2Active && !alarm2Snooze && s) {
            alarm2Snooze = true;
            alarm2RepeatRemaining--;
        }

        if (alarm1RepeatRemaining < 0) alarm1Active = false;
        if (alarm2RepeatRemaining < 0) alarm2Active = false;
    }

    private void handleButtons() {
        boolean b1 = buttonBell1.pullStatus();
        boolean b2 = buttonBell2.pullStatus();
        boolean m = buttonMode.pullStatus();
        boolean plus = buttonPlus.pullStatus();
        boolean minus = buttonMinus.pullStatus();

        if (alarm1Active && b1) alarm1Active = false;
        if (alarm2Active && b2) alarm2Active = false;

        if (mode == Mode.AL1 || mode == Mode.AL2) {
            if (b1 && buttonBell1.isPressed()) alarm1Timeout = LONG_PRESS_SECONDS * TICKS_PER_SECOND - 1;
            else if (buttonBell1.isPressed()) alarm1Timeout -= 1;
            if (buttonBell1.isPressed() && alarm1Timeout == 0) mode = Mode.AL1HOUR;

            if (b2 && buttonBell2.isPressed()) alarm2Timeout = LONG_PRESS_SECONDS * TICKS_PER_SECOND - 1;
            else if (buttonBell2.isPressed()) alarm2Timeout -= 1;
            if (buttonBell2.isPressed() && alarm2Timeout == 0) mode = Mode.AL2HOUR;
        } else if (b1 && (mode == Mode.AL1HOUR || mode == Mode.AL1MINUTE || mode == Mode.AL1REPEATS)) {
            switch (mode) {
                case AL1HOUR: mode = Mode.AL1MINUTE; break;
                case AL1MINUTE: mode = Mode.AL1REPEATS; break;
                case AL1REPEATS: mode = Mode.AL1; break;
            }
        } else if (b2 && (mode == Mode.AL2HOUR || mode == Mode.AL2MINUTE || mode == Mode.AL2REPEATS)) {
            switch (mode) {
                case AL2HOUR: mode = Mode.AL2MINUTE; break;
                case AL2MINUTE: mode = Mode.AL2REPEATS; break;
                case AL2REPEATS: mode = Mode.AL2; break;
            }
        }

        if (mode == Mode.NORMAL) {
            if (m && buttonMode.isPressed()) modeTimeout = LONG_PRESS_SECONDS * TICKS_PER_SECOND - 1;
            else if (buttonMode.isPressed()) modeTimeout -= 1;
            if (buttonMode.isPressed() && modeTimeout == 0) {
                mode = Mode.HOUR;
                modeTimeout = 30 * TICKS_PER_SECOND;
            }
        } else if (m) {
            switch (mode) {
                case HOUR: mode = Mode.MINUTE; break;
                case MINUTE: mode = Mode.YEAR; break;
                case YEAR: mode = Mode.MONTH; break;
                case MONTH: mode = Mode.DATE; break;
                case DATE: mode = Mode.AL1; break;
                case AL1: mode = Mode.AL2; break;
                case AL2: mode = Mode.AL1; break;
            }
        } else {
            int delta = 0;
            if (plus) delta++;
            if (minus) delta--;
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(time));
            switch (mode) {
                case HOUR: cal.add(Calendar.HOUR_OF_DAY, delta); break;
                case MINUTE: cal.add(Calendar.MINUTE, delta); break;
                case YEAR: cal.add(Calendar.YEAR, delta); break;
                case MONTH: cal.add(Calendar.MONTH, delta); break;
                case DATE: cal.add(Calendar.DAY_OF_MONTH, delta); break;
                case AL1HOUR: alarm1 += delta * 60; break;
                case AL1MINUTE: alarm1 += delta; break;
                case AL1REPEATS: alarm1RepeatAfter += delta; break;
                case AL2HOUR: alarm2 += delta * 60; break;
                case AL2MINUTE: alarm2 += delta; break;
                case AL2REPEATS: alarm2RepeatAfter += delta; break;
            }
            time = cal.getTimeInMillis();
            alarm1 = (alarm1 + 1440) % 1440;
            alarm2 = (alarm2 + 1440) % 1440;
            alarm1RepeatAfter = ((alarm1RepeatAfter + MAX_ALARM_REPEATS - 1) % MAX_ALARM_REPEATS) + 1;
            alarm2RepeatAfter = ((alarm2RepeatAfter + MAX_ALARM_REPEATS - 1) % MAX_ALARM_REPEATS) + 1;
        }

        if (mode == Mode.NORMAL) {
            if (b1 && !buttonBell1.isPressed()) alarm1Activated = !alarm1Activated;
            if (b2 && !buttonBell2.isPressed()) alarm2Activated = !alarm2Activated;
        }
    }

    private void updateDisplay() {
        updateTime();
        if (mode == Mode.HOUR) display.showHour(blink);
        else display.showHour(true);
        if (mode == Mode.MINUTE) display.showMinute(blink);
        else display.showMinute(true);
        if (mode == Mode.YEAR) {
            display.showHour(blink);
            display.showMinute(blink);
        } else {
            if (mode != Mode.HOUR) display.showHour(true);
            if (mode != Mode.MINUTE) display.showMinute(true);
        }
        if (mode == Mode.MONTH) display.showMonth(blink);
        else display.showMonth(true);
        if (mode == Mode.DATE) display.showDate(blink);
        else display.showDate(true);
        if (mode == Mode.AL1HOUR || mode == Mode.AL2HOUR || mode == Mode.AL1REPEATS || mode == Mode.AL2REPEATS)
            display.showAlarmHour(blink);
        else display.showAlarmHour(true);
        if (mode == Mode.AL1MINUTE || mode == Mode.AL2MINUTE) display.showAlarmMinute(blink);
        else display.showAlarmMinute(true);
        display.showAlarmBell1(alarm1Activated);
        display.showAlarmBell2(alarm2Activated);

        if (mode == Mode.AL1REPEATS) {
            display.setAlarmMinute(' ', ' ');
            String s = String.format("%2d", alarm1RepeatAfter);
            display.setAlarmHour(s.charAt(0), s.charAt(1));
        } else if (mode == Mode.AL2REPEATS) {
            display.setAlarmMinute(' ', ' ');
            String s = String.format("%2d", alarm2RepeatAfter);
            display.setAlarmHour(s.charAt(0), s.charAt(1));
        } else if (mode == Mode.AL1 || mode == Mode.AL1HOUR || mode == Mode.AL1MINUTE) {
            setAlarm(1);
        } else if (mode == Mode.AL2 || mode == Mode.AL2HOUR || mode == Mode.AL2MINUTE) {
            setAlarm(2);
        } else if (alarm1Activated && alarm2Activated) {
            long diff1 = getAlarmDifference(alarm1);
            long diff2 = getAlarmDifference(alarm2);
            if (diff1 < diff2) {
                setAlarm(1);
            } else {
                setAlarm(2);
            }
        } else if (alarm1Activated) {
            setAlarm(1);
        } else if (alarm2Activated) {
            setAlarm(2);
        } else {
            display.setAlarmHour(' ', ' ');
            display.setAlarmMinute(' ', ' ');
            display.showAlarm1(false);
            display.showAlarm2(false);
        }
    }

    private void handleAlarms() {
        int t = getHourMinute();
        if (alarm1Activated && !alarm1Active && alarm1 == t && time / 60000 != alarm1LastTime / 60000) {
            alarm1Active = true;
            alarm1LastTime = time;
            alarm1RepeatRemaining = MAX_ALARM_REPEATS;
        }
        if (alarm2Activated && !alarm2Active && alarm2 == t && time / 60000 != alarm2LastTime / 60000) {
            alarm2Active = true;
            alarm1LastTime = time;
            alarm2RepeatRemaining = MAX_ALARM_REPEATS;
        }

        if (alarm1Active && alarm1Snooze && t == (alarm1 + alarm1RepeatAfter * (7 - alarm1RepeatRemaining)))
            alarm1Snooze = false;
        if (alarm1RepeatRemaining == 0) alarm1Active = false;

        if (alarm2Active && alarm2Snooze && t == (alarm2 + alarm2RepeatAfter * (7 - alarm2RepeatRemaining)))
            alarm2Snooze = false;
        if (alarm2RepeatRemaining == 0) alarm2Active = false;

        beeper.beep((alarm1Active && !alarm1Snooze) || (alarm2Active && !alarm2Snooze));
    }

    @Override
    public void tick() {
        handleRadioReceiver();
        handleSensor();
        handleButtons();
        if (--blinkTicksTimeout == 0) {
            blinkTicksTimeout = TICKS_PER_SECOND;
            blink = !blink;
        }
        updateDisplay();
        handleAlarms();
    }

}
