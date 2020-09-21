package ue01_junit;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static ue01_junit.Bezahlung.count;

class BezahlungTest {

    @Test
    public void easyTests(){
        assertEquals(1, count("eins"));
        assertEquals(1, count("eins "));
        assertEquals(3, count("Ich liebe SEW"));
    }

    @Test
    public void normalTests(){
        assertEquals(3, count("Ich.liebe.SEW"));
        assertEquals(1, count(":eins:"));
        assertEquals(1, count(":  eins  :"));
        assertEquals(3, count(".ein erster:Text"));
    }

    @Test
    public void htmlTests(){
        assertEquals(1, count("eins <html>"));
    }

    @Test
    public void hardTest(){
        assertEquals(2, count("eins<img alt=\\\"<bild \\\\\\\" keinwort keinwort\\\" keinwort>zwei)"));
    }
}