package ue02_enum1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ue02_enum1.Bezahlung_state_machine.*;

public class Bezahlung_state_machine_test {
    @Test
    public void easyTests(){
        assertEquals(0, wordcount_stateMachine(""));
        assertEquals(0, wordcount_stateMachine(" "));
        assertEquals(0, wordcount_stateMachine("   "));
    }

    @Test
    public void normalTests(){
        assertEquals(1, wordcount_stateMachine("eins"));
        assertEquals(1, wordcount_stateMachine(" eins"));
        assertEquals(1, wordcount_stateMachine("eins "));
        assertEquals(1, wordcount_stateMachine(" eins "));
        assertEquals(1, wordcount_stateMachine(" eins  "));
        assertEquals(1, wordcount_stateMachine("  eins "));
        assertEquals(1, wordcount_stateMachine("  eins  "));
        assertEquals(1, wordcount_stateMachine("eins:"));
        assertEquals(1, wordcount_stateMachine(":eins:"));
        assertEquals(1, wordcount_stateMachine("eins  :   "));
        assertEquals(1, wordcount_stateMachine(":   eins  :"));
        assertEquals(3, wordcount_stateMachine("ein erster Text"));
        assertEquals(3, wordcount_stateMachine("ein     erster    Text     "));
        assertEquals(3, wordcount_stateMachine("ein:erster.Text"));
    }

    @Test
    public void maybeWrong(){
        assertEquals(1, wordcount_stateMachine("a"));
        assertEquals(1, wordcount_stateMachine(" a"));
        assertEquals(1, wordcount_stateMachine("a "));
        assertEquals(1, wordcount_stateMachine(" a "));
    }

    @Test
    public void htmlTests(){
        assertEquals(1, wordcount_stateMachine(" eins <html> "));
    }

    @Test
    public void hardTest(){
        assertEquals(2, wordcount_stateMachine("eins<img alt=\\\"<bild \\\\\\\" keinwort keinwort\\\" keinwort>zwei)"));
    }
}
