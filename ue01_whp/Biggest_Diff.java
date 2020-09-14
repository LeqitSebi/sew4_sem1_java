package ue01_whp;

import java.util.*;
import java.util.stream.Collectors;

import static ue01_whp.Pair.getDifference;

public class Biggest_Diff {
    public static Pair getBiggestDiffernz(int[] data) {
        int biggestDifference = 0;
        Pair pair = new Pair();
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length; j++) {
                int difference = getDifference(data[i], data[j]);
                if (difference > biggestDifference) {
                    biggestDifference = difference;
                    pair.n = data[i];
                    pair.m = data[j];
                }
            }
        }
        return pair;
    }

    public static void main(String[] args) {
        int[] numbers = new int[]{1, 2, 3, 4, 5, 6};
        System.out.println(getBiggestDiffernz(numbers));

        Pair[] pairs = new Pair[(int) Math.pow(numbers.length, 2)];
        int c = 0;
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers.length; j++) {
                pairs[c] = new Pair(numbers[i], numbers[j]);
                c++;
            }
        }

        ArrayList<Pair> sortedPairs = (ArrayList<Pair>) Arrays
                .stream(pairs)
                .sorted((o1, o2) -> Integer.compare(o2.getDifference(), o1.getDifference()))
                .collect(Collectors.toList());
        System.out.println(sortedPairs);
    }
}
