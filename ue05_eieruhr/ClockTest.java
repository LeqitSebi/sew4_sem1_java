package ue05_eieruhr;

import org.junit.jupiter.api.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class ClockTest {

    private static Display display;
    private static Beeper beeper;
    private static Button buttonMode;
    private static Button buttonPlus;
    private static Button buttonMinus;
    private static Button buttonBell1;
    private static Button buttonBell2;
    private static Sensor sensor;
    private static RadioReceiver radioReceiver;
    private static Timer timer;
    private static ClockController controller;

    @BeforeEach
    void createController() {
        display = new Display();
        beeper = new Beeper();
        buttonMode = new Button();
        buttonPlus = new Button();
        buttonMinus = new Button();
        buttonBell1 = new Button();
        buttonBell2 = new Button();
        sensor = new Sensor();
        radioReceiver = new RadioReceiver();
        timer = new Timer();
        controller = new ClockController(display, beeper, buttonMode, buttonPlus, buttonMinus,
                buttonBell1, buttonBell2, sensor, radioReceiver, timer);
        timer.setController(controller);
        controller.powerOn();
    }

    @AfterEach
    void deleteController() {
        controller.powerOff();
    }

    private void assertDisplay(long millis) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E", Locale.GERMAN);
        String d = sdf.format(new Date(millis)).toUpperCase();
        assertEquals(d.substring(11, 13), display.getHour());
        assertEquals(d.substring(14, 16), display.getMinute());
        assertEquals(d.substring(17, 19), display.getSecond());
        assertEquals(d.substring(8, 10), display.getDate());
        assertEquals(d.substring(5, 7), display.getMonth());
        assertEquals(d.substring(20, 22), display.getWeekday());
    }

    @Test
    void test_01_Init() {
        assertEquals("12", display.getHour());
        assertEquals("00", display.getMinute());
        assertEquals("00", display.getSecond());
        assertEquals("00", display.getDate());
        assertEquals("00", display.getMonth());
        assertEquals("NA", display.getWeekday());
        assertTrue(display.getShowDate());
        assertTrue(display.getShowAlarmHour());
        assertTrue(display.getShowAlarmMinute());
        assertTrue(display.getShowHour());
        assertTrue(display.getShowMinute());
        assertTrue(display.getShowMonth());
        assertTrue(display.getShowSecond());
        assertFalse(display.isLight());
        String disp = display.toString();
    }

    @Test
    void test_02_Tick() {
        timer.tick();
    }

    @Test
    void test_03_TickTime() {
        timer.tick();
        assertDisplay(System.currentTimeMillis());
    }

    @Test
    void test_04_TickMany() {
        long millis = System.currentTimeMillis();
        timer.tick();
        timer.tickSeconds(3600);
        assertDisplay(millis + 3600 * 1000);
    }

    @Test
    void test_05_LightSensor() {
        assertFalse(display.isLight());
        sensor.trigger();
        timer.tick();
        assertTrue(display.isLight());
        timer.tick();
        assertTrue(display.isLight());
        timer.tick();
        assertTrue(display.isLight());
        timer.tick();
        assertFalse(display.isLight());
        timer.tick();
        assertFalse(display.isLight());
    }

    @Test
    void test_06_ActivateAlarm() {
        assertFalse(display.getAlarmBell1());
        assertFalse(display.getAlarmBell2());
        timer.tick();

        buttonBell1.press();
        buttonBell1.release();
        timer.tick();

        assertTrue(display.getAlarmBell1());
        assertFalse(display.getAlarmBell2());
        timer.tick();

        buttonBell2.press();
        buttonBell2.release();
        timer.tick();

        assertTrue(display.getAlarmBell1());
        assertTrue(display.getAlarmBell2());
        timer.tick();

        buttonBell1.press();
        buttonBell1.release();
        timer.tick();

        assertFalse(display.getAlarmBell1());
        assertTrue(display.getAlarmBell2());
        timer.tick();

        buttonBell2.press();
        buttonBell2.release();
        timer.tick();

        assertFalse(display.getAlarmBell1());
        assertFalse(display.getAlarmBell2());
    }

    @Test
    void test_07_ChangeHour() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        timer.tick();

        buttonMode.press();
        timer.tickSeconds(ClockController.LONG_PRESS_SECONDS);
        buttonMode.release();

        assertTrue(display.getShowHour());
        assertTrue(display.getShowMinute());
        timer.tickSeconds(1);
        assertFalse(display.getShowHour());
        assertTrue(display.getShowMinute());
        timer.tickSeconds(1);
        assertTrue(display.getShowHour());
        assertTrue(display.getShowMinute());
        timer.tickSeconds(1);
        assertFalse(display.getShowHour());
        assertTrue(display.getShowMinute());

        buttonPlus.press();
        buttonPlus.release();
        timer.tick();
        cal.add(Calendar.HOUR_OF_DAY, 1);
        assertEquals(new SimpleDateFormat("HH", Locale.GERMAN).format(cal.getTime()), display.getHour());
        buttonPlus.press();
        buttonPlus.release();
        timer.tick();
        cal.add(Calendar.HOUR_OF_DAY, 1);
        assertEquals(new SimpleDateFormat("HH", Locale.GERMAN).format(cal.getTime()), display.getHour());
        buttonMinus.press();
        buttonMinus.release();
        timer.tick();
        cal.add(Calendar.HOUR_OF_DAY, -1);
        assertEquals(new SimpleDateFormat("HH", Locale.GERMAN).format(cal.getTime()), display.getHour());
        buttonMinus.press();
        buttonMinus.release();
        timer.tick();
        cal.add(Calendar.HOUR_OF_DAY, -1);
        assertEquals(new SimpleDateFormat("HH", Locale.GERMAN).format(cal.getTime()), display.getHour());
        buttonMinus.press();
        buttonMinus.release();
        timer.tick();
        cal.add(Calendar.HOUR_OF_DAY, -1);
        assertEquals(new SimpleDateFormat("HH", Locale.GERMAN).format(cal.getTime()), display.getHour());
    }

    @Test
    void test_08_ChangeMinute() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        timer.tick();

        buttonMode.press();
        timer.tickSeconds(ClockController.LONG_PRESS_SECONDS);
        buttonMode.release();

        timer.tick();
        buttonMode.press();
        buttonMode.release();
        timer.tick();

        assertTrue(display.getShowHour());
        assertTrue(display.getShowMinute());
        timer.tickSeconds(1);
        assertFalse(display.getShowMinute());
        assertTrue(display.getShowHour());
        timer.tickSeconds(1);
        assertTrue(display.getShowMinute());
        assertTrue(display.getShowHour());
        timer.tickSeconds(1);
        assertFalse(display.getShowMinute());
        assertTrue(display.getShowHour());
        cal.add(Calendar.SECOND, 3 + ClockController.LONG_PRESS_SECONDS);

        buttonPlus.press();
        buttonPlus.release();
        timer.tick();

        cal.add(Calendar.MINUTE, 1);
        assertEquals(new SimpleDateFormat("mm", Locale.GERMAN).format(cal.getTime()), display.getMinute());
        buttonPlus.press();
        buttonPlus.release();
        timer.tick();
        cal.add(Calendar.MINUTE, 1);
        assertEquals(new SimpleDateFormat("mm", Locale.GERMAN).format(cal.getTime()), display.getMinute());
        buttonMinus.press();
        buttonMinus.release();
        timer.tick();
        cal.add(Calendar.MINUTE, -1);
        assertEquals(new SimpleDateFormat("mm", Locale.GERMAN).format(cal.getTime()), display.getMinute());
        buttonMinus.press();
        buttonMinus.release();
        timer.tick();
        cal.add(Calendar.MINUTE, -1);
        assertEquals(new SimpleDateFormat("mm", Locale.GERMAN).format(cal.getTime()), display.getMinute());
        buttonMinus.press();
        buttonMinus.release();
        timer.tick();
        cal.add(Calendar.MINUTE, -1);
        assertEquals(new SimpleDateFormat("mm", Locale.GERMAN).format(cal.getTime()), display.getMinute());
    }

    @Test
    void test_09_ChangeYear() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        timer.tick();

        buttonMode.press();
        timer.tickSeconds(ClockController.LONG_PRESS_SECONDS);
        buttonMode.release();

        timer.tick();
        buttonMode.press();
        buttonMode.release();
        timer.tick();
        buttonMode.press();
        buttonMode.release();
        timer.tick();

        assertTrue(display.getShowHour());
        assertTrue(display.getShowMinute());
        timer.tickSeconds(1);
        assertFalse(display.getShowMinute());
        assertFalse(display.getShowHour());
        timer.tickSeconds(1);
        assertTrue(display.getShowMinute());
        assertTrue(display.getShowHour());
        timer.tickSeconds(1);
        assertFalse(display.getShowMinute());
        assertFalse(display.getShowHour());

        buttonPlus.press();
        buttonPlus.release();
        timer.tick();

        cal.add(Calendar.YEAR, 1);
        assertEquals(new SimpleDateFormat("YYYY", Locale.GERMAN).format(cal.getTime()), display.getHour() + display.getMinute());
        buttonPlus.press();
        buttonPlus.release();
        timer.tick();
        cal.add(Calendar.YEAR, 1);
        assertEquals(new SimpleDateFormat("YYYY", Locale.GERMAN).format(cal.getTime()), display.getHour() + display.getMinute());
        buttonMinus.press();
        buttonMinus.release();
        timer.tick();
        cal.add(Calendar.YEAR, -1);
        assertEquals(new SimpleDateFormat("YYYY", Locale.GERMAN).format(cal.getTime()), display.getHour() + display.getMinute());
        buttonMinus.press();
        buttonMinus.release();
        timer.tick();
        cal.add(Calendar.YEAR, -1);
        assertEquals(new SimpleDateFormat("YYYY", Locale.GERMAN).format(cal.getTime()), display.getHour() + display.getMinute());
        buttonMinus.press();
        buttonMinus.release();
        timer.tick();
        cal.add(Calendar.YEAR, -1);
        assertEquals(new SimpleDateFormat("YYYY", Locale.GERMAN).format(cal.getTime()), display.getHour() + display.getMinute());
    }

    @Test
    void test_10_ChangeMonth() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        timer.tick();

        buttonMode.press();
        timer.tickSeconds(ClockController.LONG_PRESS_SECONDS);
        buttonMode.release();

        timer.tick();
        buttonMode.press();
        buttonMode.release();
        timer.tick();
        buttonMode.press();
        buttonMode.release();
        timer.tick();
        buttonMode.press();
        buttonMode.release();
        timer.tick();

        assertTrue(display.getShowMonth());
        assertTrue(display.getShowDate());
        timer.tickSeconds(1);
        assertFalse(display.getShowMonth());
        assertTrue(display.getShowDate());
        timer.tickSeconds(1);
        assertTrue(display.getShowMonth());
        assertTrue(display.getShowDate());
        timer.tickSeconds(1);
        assertFalse(display.getShowMonth());
        assertTrue(display.getShowDate());

        buttonPlus.press();
        buttonPlus.release();
        timer.tick();

        cal.add(Calendar.MONTH, 1);
        assertEquals(new SimpleDateFormat("MM", Locale.GERMAN).format(cal.getTime()), display.getMonth());
        buttonPlus.press();
        buttonPlus.release();
        timer.tick();
        cal.add(Calendar.MONTH, 1);
        assertEquals(new SimpleDateFormat("MM", Locale.GERMAN).format(cal.getTime()), display.getMonth());
        buttonMinus.press();
        buttonMinus.release();
        timer.tick();
        cal.add(Calendar.MONTH, -1);
        assertEquals(new SimpleDateFormat("MM", Locale.GERMAN).format(cal.getTime()), display.getMonth());
        buttonMinus.press();
        buttonMinus.release();
        timer.tick();
        cal.add(Calendar.MONTH, -1);
        assertEquals(new SimpleDateFormat("MM", Locale.GERMAN).format(cal.getTime()), display.getMonth());
        buttonMinus.press();
        buttonMinus.release();
        timer.tick();
        cal.add(Calendar.MONTH, -1);
        assertEquals(new SimpleDateFormat("MM", Locale.GERMAN).format(cal.getTime()), display.getMonth());
    }

    @Test
    void test_11_ChangeDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        timer.tick();

        buttonMode.press();
        timer.tickSeconds(ClockController.LONG_PRESS_SECONDS);
        buttonMinus.release();

        timer.tick();
        for (int i = 0; i < 4; i++) {
            buttonMode.press();
            buttonMode.release();
            timer.tick();
        }

        assertTrue(display.getShowDate());
        assertTrue(display.getShowMonth());
        timer.tickSeconds(1);
        assertFalse(display.getShowDate());
        assertTrue(display.getShowMonth());
        timer.tickSeconds(1);
        assertTrue(display.getShowDate());
        assertTrue(display.getShowMonth());
        timer.tickSeconds(1);
        assertFalse(display.getShowDate());
        assertTrue(display.getShowMonth());

        buttonPlus.press();
        buttonPlus.release();
        timer.tick();

        cal.add(Calendar.DAY_OF_MONTH, 1);
        assertEquals(new SimpleDateFormat("dd", Locale.GERMAN).format(cal.getTime()), display.getDate());
        buttonPlus.press();
        buttonPlus.release();
        timer.tick();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        assertEquals(new SimpleDateFormat("dd", Locale.GERMAN).format(cal.getTime()), display.getDate());
        buttonMinus.press();
        buttonMinus.release();
        timer.tick();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        assertEquals(new SimpleDateFormat("dd", Locale.GERMAN).format(cal.getTime()), display.getDate());
        buttonMinus.press();
        buttonMinus.release();
        timer.tick();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        assertEquals(new SimpleDateFormat("dd", Locale.GERMAN).format(cal.getTime()), display.getDate());
        buttonMinus.press();
        buttonMinus.release();
        timer.tick();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        assertEquals(new SimpleDateFormat("dd", Locale.GERMAN).format(cal.getTime()), display.getDate());
    }

    @Test
    void test_12_DefaultAlarm() {
        timer.tick();
        buttonBell2.press();
        buttonBell2.release();
        timer.tick();
        assertEquals("07", display.getAlarmHour());
        assertEquals("00", display.getAlarmMinute());
        assertTrue(display.getShowAlarm2());
        assertFalse(display.getShowAlarm1());

        timer.tick();
        buttonBell1.press();
        buttonBell1.release();
        timer.tick();
        assertEquals("06", display.getAlarmHour());
        assertEquals("00", display.getAlarmMinute());
        assertTrue(display.getShowAlarm1());
        assertFalse(display.getShowAlarm2());

        timer.tick();
        buttonBell2.press();
        buttonBell2.release();
        timer.tick();
        assertEquals("06", display.getAlarmHour());
        assertEquals("00", display.getAlarmMinute());
        assertTrue(display.getShowAlarm1());
        assertFalse(display.getShowAlarm2());

        timer.tick();
        buttonBell1.press();
        buttonBell1.release();
        timer.tick();
        assertEquals("  ", display.getAlarmHour());
        assertEquals("  ", display.getAlarmMinute());
        assertFalse(display.getShowAlarm1());
        assertFalse(display.getShowAlarm2());

    }

    @Test
    void test_13_ChangeAlarm1() {
        timer.tick();

        buttonMode.press();
        timer.tickSeconds(ClockController.LONG_PRESS_SECONDS);
        buttonMode.release();

        timer.tick();
        for (int i = 0; i < 5; i++) {
            buttonMode.press();
            buttonMode.release();
            timer.tick();
        }

        buttonBell1.press();
        timer.tickSeconds(ClockController.LONG_PRESS_SECONDS);
        buttonBell1.release();
        timer.tick();

        assertFalse(display.getShowAlarmHour());
        assertTrue(display.getShowAlarmMinute());
        timer.tickSeconds(1);
        assertTrue(display.getShowAlarmHour());
        assertTrue(display.getShowAlarmMinute());
        timer.tickSeconds(1);
        assertFalse(display.getShowAlarmHour());
        assertTrue(display.getShowAlarmMinute());
        timer.tickSeconds(1);
        assertTrue(display.getShowAlarmHour());
        assertTrue(display.getShowAlarmMinute());

        buttonPlus.press();
        buttonPlus.release();
        timer.tick();
        assertEquals("07", display.getAlarmHour());

        buttonPlus.press();
        buttonPlus.release();
        timer.tick();
        assertEquals("08", display.getAlarmHour());

        buttonMinus.press();
        buttonMinus.release();
        timer.tick();
        assertEquals("07", display.getAlarmHour());

        buttonMinus.press();
        buttonMinus.release();
        timer.tick();
        assertEquals("06", display.getAlarmHour());

        for (int i = 0; i < 8; i++) {
            buttonMinus.press();
            buttonMinus.release();
            timer.tick();
        }
        assertEquals("22", display.getAlarmHour());

        buttonBell1.press();
        buttonBell1.release();
        timer.tick();

        assertTrue(display.getShowAlarmMinute());
        assertTrue(display.getShowAlarmHour());
        timer.tickSeconds(1);
        assertFalse(display.getShowAlarmMinute());
        assertTrue(display.getShowAlarmHour());
        timer.tickSeconds(1);
        assertTrue(display.getShowAlarmMinute());
        assertTrue(display.getShowAlarmHour());
        timer.tickSeconds(1);
        assertFalse(display.getShowAlarmMinute());
        assertTrue(display.getShowAlarmHour());

        buttonPlus.press();
        buttonPlus.release();
        timer.tick();
        assertEquals("01", display.getAlarmMinute());

        buttonPlus.press();
        buttonPlus.release();
        timer.tick();
        assertEquals("02", display.getAlarmMinute());

        for (int i = 0; i < 4; i ++) {
            buttonMinus.press();
            buttonMinus.release();
            timer.tick();
        }
        assertEquals("58", display.getAlarmMinute());

        buttonBell1.press();
        buttonBell1.release();
        timer.tick();

        assertEquals("  ", display.getAlarmMinute());
        assertEquals(" 4", display.getAlarmHour());
        assertFalse(display.getShowAlarmHour());
        timer.tickSeconds(1);
        assertTrue(display.getShowAlarmHour());
        timer.tickSeconds(1);
        assertFalse(display.getShowAlarmHour());
        timer.tickSeconds(1);
        assertTrue(display.getShowAlarmHour());

        buttonPlus.press();
        buttonPlus.release();
        timer.tick();
        assertEquals("  ", display.getAlarmMinute());
        assertEquals(" 5", display.getAlarmHour());

        buttonPlus.press();
        buttonPlus.release();
        timer.tick();
        assertEquals("  ", display.getAlarmMinute());
        assertEquals(" 6", display.getAlarmHour());

        for (int i = 0; i < 6; i++) {
            buttonMinus.press();
            buttonMinus.release();
            timer.tick();
        }

        assertEquals("  ", display.getAlarmMinute());
        assertEquals(" 7", display.getAlarmHour());


        buttonBell1.press();
        buttonBell1.release();
        timer.tick();
    }

    @Test
    void test_14_ChangeAlarm2() {
        timer.tick();

        buttonMode.press();
        timer.tickSeconds(ClockController.LONG_PRESS_SECONDS);
        buttonMode.release();

        timer.tick();
        for (int i = 0; i < 5; i++) {
            buttonMode.press();
            buttonMode.release();
            timer.tick();
        }

        buttonBell2.press();
        timer.tickSeconds(ClockController.LONG_PRESS_SECONDS);
        buttonBell2.release();
        timer.tick();

        assertFalse(display.getShowAlarmHour());
        assertTrue(display.getShowAlarmMinute());
        timer.tickSeconds(1);
        assertTrue(display.getShowAlarmHour());
        assertTrue(display.getShowAlarmMinute());
        timer.tickSeconds(1);
        assertFalse(display.getShowAlarmHour());
        assertTrue(display.getShowAlarmMinute());
        timer.tickSeconds(1);
        assertTrue(display.getShowAlarmHour());
        assertTrue(display.getShowAlarmMinute());

        buttonPlus.press();
        buttonPlus.release();
        timer.tick();
        assertEquals("08", display.getAlarmHour());

        buttonPlus.press();
        buttonPlus.release();
        timer.tick();
        assertEquals("09", display.getAlarmHour());

        buttonMinus.press();
        buttonMinus.release();
        timer.tick();
        assertEquals("08", display.getAlarmHour());

        buttonMinus.press();
        buttonMinus.release();
        timer.tick();
        assertEquals("07", display.getAlarmHour());

        for (int i = 0; i < 8; i++) {
            buttonMinus.press();
            buttonMinus.release();
            timer.tick();
        }
        assertEquals("23", display.getAlarmHour());

        buttonBell2.press();
        buttonBell2.release();
        timer.tick();

        assertTrue(display.getShowAlarmMinute());
        assertTrue(display.getShowAlarmHour());
        timer.tickSeconds(1);
        assertFalse(display.getShowAlarmMinute());
        assertTrue(display.getShowAlarmHour());
        timer.tickSeconds(1);
        assertTrue(display.getShowAlarmMinute());
        assertTrue(display.getShowAlarmHour());
        timer.tickSeconds(1);
        assertFalse(display.getShowAlarmMinute());
        assertTrue(display.getShowAlarmHour());

        buttonPlus.press();
        buttonPlus.release();
        timer.tick();
        assertEquals("01", display.getAlarmMinute());

        buttonPlus.press();
        buttonPlus.release();
        timer.tick();
        assertEquals("02", display.getAlarmMinute());

        for (int i = 0; i < 4; i ++) {
            buttonMinus.press();
            buttonMinus.release();
            timer.tick();
        }
        assertEquals("58", display.getAlarmMinute());


        buttonBell2.press();
        buttonBell2.release();
        timer.tick();

        assertEquals("  ", display.getAlarmMinute());
        assertEquals(" 4", display.getAlarmHour());
        assertFalse(display.getShowAlarmHour());
        timer.tickSeconds(1);
        assertTrue(display.getShowAlarmHour());
        timer.tickSeconds(1);
        assertFalse(display.getShowAlarmHour());
        timer.tickSeconds(1);
        assertTrue(display.getShowAlarmHour());

        buttonPlus.press();
        buttonPlus.release();
        timer.tick();
        assertEquals("  ", display.getAlarmMinute());
        assertEquals(" 5", display.getAlarmHour());

        buttonPlus.press();
        buttonPlus.release();
        timer.tick();
        assertEquals("  ", display.getAlarmMinute());
        assertEquals(" 6", display.getAlarmHour());

        for (int i = 0; i < 6; i++) {
            buttonMinus.press();
            buttonMinus.release();
            timer.tick();
        }

        assertEquals("  ", display.getAlarmMinute());
        assertEquals(" 7", display.getAlarmHour());


        buttonBell2.press();
        buttonBell2.release();
        timer.tick();
    }

    @Test
    void test_15_RingAlarm1() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        timer.tick();
        assertFalse(display.getAlarmBell1());
        assertFalse(display.getAlarmBell2());
        assertFalse(beeper.isBeeping());

        buttonBell1.press();
        buttonBell1.release();
        timer.tick();
        assertTrue(display.getAlarmBell1());

        int deltaSeconds = 60 * ((6 * 60 - (cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE)) + 1440) % 1440);
        timer.tickSeconds(deltaSeconds);

        for (int i = 0; i < ClockController.MAX_ALARM_REPEATS; i++) {
            assertTrue(beeper.isBeeping());
            timer.tick();
            sensor.trigger();
            timer.tick();
            assertFalse(beeper.isBeeping());
            timer.tickSeconds(ClockController.DEFAULT_ALARM_REPEAT_MINUTES * 60);
        }

        assertFalse(beeper.isBeeping());
    }

    @Test
    void test_16_StopAlarm1() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        timer.tick();
        assertFalse(display.getAlarmBell1());
        assertFalse(display.getAlarmBell2());
        assertFalse(beeper.isBeeping());

        buttonBell1.press();
        buttonBell1.release();
        timer.tick();
        assertTrue(display.getAlarmBell1());


        int deltaSeconds = 60 * ((6 * 60 - (cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE)) + 1440) % 1440);
        timer.tickSeconds(deltaSeconds);
        assertTrue(beeper.isBeeping());
        timer.tick();

        buttonBell1.press();
        buttonBell1.release();
        timer.tick();
        assertFalse(beeper.isBeeping());

        timer.tickSeconds(60 * ClockController.DEFAULT_ALARM_REPEAT_MINUTES * 2);
        assertFalse(beeper.isBeeping());
    }

    @Test
    void test_17_RingAlarm2() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        timer.tick();
        assertFalse(display.getAlarmBell1());
        assertFalse(display.getAlarmBell2());
        assertFalse(beeper.isBeeping());

        buttonBell2.press();
        buttonBell2.release();
        timer.tick();
        assertTrue(display.getAlarmBell2());

        int deltaSeconds = 60 * ((7 * 60 - (cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE)) + 1440) % 1440);
        timer.tickSeconds(deltaSeconds);

        for (int i = 0; i < ClockController.MAX_ALARM_REPEATS; i++) {
            assertTrue(beeper.isBeeping());
            timer.tick();
            sensor.trigger();
            timer.tick();
            assertFalse(beeper.isBeeping());
            timer.tickSeconds(ClockController.DEFAULT_ALARM_REPEAT_MINUTES * 60);
        }

        assertFalse(beeper.isBeeping());
    }

    @Test
    void test_18_StopAlarm2() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        timer.tick();
        assertFalse(display.getAlarmBell1());
        assertFalse(display.getAlarmBell2());
        assertFalse(beeper.isBeeping());

        buttonBell2.press();
        buttonBell2.release();
        timer.tick();
        assertTrue(display.getAlarmBell2());


        int deltaSeconds = 60 * ((7 * 60 - (cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE)) + 1440) % 1440);
        timer.tickSeconds(deltaSeconds);
        assertTrue(beeper.isBeeping());
        timer.tick();

        buttonBell2.press();
        buttonBell2.release();
        timer.tick();
        assertFalse(beeper.isBeeping());

        timer.tickSeconds(60 * ClockController.DEFAULT_ALARM_REPEAT_MINUTES * 2);
        assertFalse(beeper.isBeeping());
    }

}