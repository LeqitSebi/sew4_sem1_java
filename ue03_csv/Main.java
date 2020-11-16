package ue03_csv;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> top = null;
        for (List<String> i : new CSVFileReader("res/hor.csv", ';')) {
            if (top == null) {
                top = i;
            } else {
                System.out.print(i.get(0) + ":");
                boolean first = true;
                for (int j = 1; j < i.size(); j++) {
                    if (!i.get(j).isEmpty()) {
                        if (!first) {
                            System.out.print(",");
                        }
                        System.out.print(" nach " + top.get(j) + ":" + i.get(j));
                        first = false;
                    }
                }
                System.out.println();
            }
        }
    }

}
