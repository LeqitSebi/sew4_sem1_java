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
        int htmlFlag = 0;
        String[] words = s.split("[^A-z0-9<>]");
        for (String n : words) {
            if (n.contains("<")) {
                htmlFlag++;
            }
            if (htmlFlag == 0 && n.length() != 0) {
                wordcount++;
            }
            if (n.contains(">")) {
                htmlFlag--;
            }
        }
        return wordcount;
    }
}
