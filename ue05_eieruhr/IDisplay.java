package ue05_eieruhr;

public interface IDisplay {

    void setHour(char h1, char h2);
    void setMinute(char m1, char m2);
    void setSecond(char s1, char s2);
    void showHour(boolean on);
    void showMinute(boolean on);
    void showSecond(boolean on);

    void showRadioConnection(boolean on);
    void showSummerTime(boolean on);
    void showForcedTimeZone(boolean on);
    void showHeader(boolean on);

    void setDate(char d1, char d2);
    void setMonth(char m1, char m2);
    void setWeekday(char w1, char w2);
    void showDate(boolean on);
    void showMonth(boolean on);

    void setAlarmHour(char h1, char h2);
    void setAlarmMinute(char m1, char m2);

    void showAlarm1(boolean on);
    void showAlarm2(boolean on);
    void showAlarmBell1(boolean on);
    void showAlarmBell2(boolean on);
    void showAlarmHour(boolean on);
    void showAlarmMinute(boolean on);

    void setLight(boolean on);

}
