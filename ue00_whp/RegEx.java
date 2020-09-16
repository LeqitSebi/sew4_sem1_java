package ue00_whp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegEx {
    private static final Pattern validEmail = Pattern.compile("((A-z)(A-z0-9-_\\+\\.)*)@(A-z-)\\.(A-z)+(\\.)?(A-z)*");

    public static void main(String[] args) {
        System.out.println(getEmailName("sebi@slanitsch.com"));
    }

    public static String getEmailName(String addr){
        Matcher m = validEmail.matcher(addr);
        if (m.matches()){
            return m.group(1);
        }else{
            throw new IllegalArgumentException("keine gültige Email");
        }
    }
}
