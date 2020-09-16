package ue01_junit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bezahlung {
    private static final Pattern word = Pattern.compile("[A-z]+");

    public static void main(String[] args) {
        System.out.println(count("Ich mag SEW"));
    }

    public static int count(String s){
        int wordcount = 0;
        String[] allwords = s.split(" ");
        for (String n:allwords) {
            Matcher m = word.matcher(n);
            if (m.matches()){
                wordcount++;
            }
        }
        return wordcount;
    }
}
