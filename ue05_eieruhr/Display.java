package ue05_eieruhr;

public class Display implements IDisplay {

    private String hour = "  ";
    private String minute = "  ";
    private String second = "  ";
    private boolean showHour = false;
    private boolean showMinute = false;
    private boolean showSecond = false;
    private boolean radioConnection = false;
    private boolean summerTime = false;
    private boolean forcedTimeZone = false;
    private boolean showHeader = false;
    private String date = "  ";
    private String month = "  ";
    private String weekday = "  ";
    private boolean showDate = false;
    private boolean showMonth = false;
    private String alarmHour = "  ";
    private String alarmMinute = "  ";
    private boolean alarm1 = false;
    private boolean alarm2 = false;
    private boolean alarmBell1 = false;
    private boolean alarmBell2 = false;
    private boolean showAlarmHour = false;
    private boolean showAlarmMinute = false;
    private boolean light = false;

    @Override
    public String toString() {
        String flags = "";
        if (light) flags += "LIGHT ";
        if (!showHeader) flags += "HOUR_HIDDEN ";
        if (!showMinute) flags += "MIN_HIDDEN ";
        if (!showSecond) flags += "SEC_HIDDEN ";
        if (radioConnection) flags += "RADIO ";
        if (forcedTimeZone) flags += "F ";
        if (summerTime) flags += "S ";
        if (flags.length() > 0) flags = flags.substring(0, flags.length() - 1);

        String dateFlags = "";
        if (!showDate) dateFlags += "HIDDEN ";
        if (showHeader) dateFlags += "DATE_MONTH ";
        if (dateFlags.length() > 0) dateFlags = dateFlags.substring(0, dateFlags.length() - 1);

        String alarmFlags = "";
        if (!showAlarmHour) alarmFlags += "HOUR_HIDDEN ";
        if (!showAlarmMinute) alarmFlags += "MIN_HIDDEN ";
        if (alarm1) alarmFlags += "AL1 ";
        if (alarm2) alarmFlags += "AL2 ";
        if (alarmBell1) alarmFlags += "\uD83D\uDD6D1 ";
        if (alarmBell2) alarmFlags += "\uD83D\uDD6D2 ";
        if (alarmFlags.length() > 0) alarmFlags = alarmFlags.substring(0, alarmFlags.length() - 1);

        return String.format("T: %s:%s:%s [%s]\nD: %s.%s.%s [%s]\nA: %s:%s    [%s]",
                hour, minute, second, flags,
                date, month, weekday, dateFlags,
                alarmHour, alarmMinute, alarmFlags);
    }

    @Override
    public void setHour(char h1, char h2) {
        hour = new String(new char[] {h1, h2});
    }

    @Override
    public void setMinute(char m1, char m2) {
        minute = new String(new char[] {m1, m2});
    }

    @Override
    public void setSecond(char s1, char s2) {
        second = new String(new char[] {s1, s2});
    }

    @Override
    public void showHour(boolean on) {
        showHour = on;
    }

    @Override
    public void showMinute(boolean on) {
        showMinute = on;
    }

    @Override
    public void showSecond(boolean on) {
        showSecond = on;
    }

    @Override
    public void showRadioConnection(boolean on) {
        radioConnection = on;
    }

    @Override
    public void showSummerTime(boolean on) {
        summerTime = on;
    }

    @Override
    public void showForcedTimeZone(boolean on) {
        forcedTimeZone = on;
    }

    @Override
    public void showHeader(boolean on) {
        showHeader = on;
    }

    @Override
    public void setDate(char d1, char d2) {
        date = new String(new char[] {d1, d2});
    }

    @Override
    public void setMonth(char m1, char m2) {
        month = new String(new char[] {m1, m2});
    }

    @Override
    public void setWeekday(char w1, char w2) {
        weekday = new String(new char[] {w1, w2});
    }

    @Override
    public void showDate(boolean on) {
        showDate = on;
    }

    @Override
    public void showMonth(boolean on) {
        showMonth = on;
    }

    @Override
    public void setAlarmHour(char h1, char h2) {
        alarmHour = new String(new char[] {h1, h2});
    }

    @Override
    public void setAlarmMinute(char m1, char m2) {
        alarmMinute = new String(new char[] {m1, m2});
    }

    @Override
    public void showAlarm1(boolean on) {
        alarm1 = on;
    }

    @Override
    public void showAlarm2(boolean on) {
        alarm2 = on;
    }

    @Override
    public void showAlarmBell1(boolean on) {
        alarmBell1 = on;
    }

    @Override
    public void showAlarmBell2(boolean on) {
        alarmBell2 = on;
    }

    @Override
    public void showAlarmHour(boolean on) {
        showAlarmHour = on;
    }

    @Override
    public void showAlarmMinute(boolean on) {
        showAlarmMinute = on;
    }

    @Override
    public void setLight(boolean on) {
        light = on;
    }

    public String getHour() {
        return hour;
    }

    public String getMinute() {
        return minute;
    }

    public String getSecond() {
        return second;
    }

    public String getDate() {
        return date;
    }

    public String getMonth() {
        return month;
    }

    public String getWeekday() {
        return weekday;
    }

    public String getAlarmHour() {
        return alarmHour;
    }

    public String getAlarmMinute() {
        return alarmMinute;
    }

    public boolean getShowHour() {
        return showHour;
    }

    public boolean getShowMinute() {
        return showMinute;
    }

    public boolean getShowSecond() {
        return showSecond;
    }

    public boolean getShowDate() {
        return showDate;
    }

    public boolean getShowMonth() {
        return showMonth;
    }

    public boolean getShowAlarmHour() {
        return showAlarmHour;
    }

    public boolean getShowAlarmMinute() {
        return showAlarmMinute;
    }

    public boolean getShowAlarm1() {
        return alarm1;
    }

    public boolean getShowAlarm2() {
        return alarm2;
    }

    public boolean getAlarmBell1() {
        return alarmBell1;
    }

    public boolean getAlarmBell2() {
        return alarmBell2;
    }

    public boolean isLight() {
        return light;
    }

    public boolean getForcedTimeZone() {
        return forcedTimeZone;
    }

    public boolean getSummerTime() {
        return summerTime;
    }

    public boolean getRadioConnection() {
        return radioConnection;
    }

}
