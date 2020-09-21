package ue01_junit;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bezahlung {
    public static void main(String[] args) {
        System.out.println(count("eins <gilt nicht> zwei.drei      vier"));
    }

    public static int count(String s) {
        int wordcount = 0;
        s = s.replaceAll("<[^>]*>", " ");
        String[] words = s.split("\\W");
        for (String n:words) {
            if (!n.equals("")){
                wordcount++;
            }
        }
        return wordcount;
    }
}
