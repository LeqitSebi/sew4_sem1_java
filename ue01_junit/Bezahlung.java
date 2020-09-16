package ue01_junit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bezahlung {
    private static final Pattern word = Pattern.compile("([A-z]+)[ ._:]*");

    public static int count(String s){
        int wordcount = 0;
        Matcher m = word.matcher(s);
        while (m.find()){
            wordcount++;
        }
        return wordcount;
    }
}
