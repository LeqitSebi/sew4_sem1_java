package ue00_whp;

public class Pair {
    int n;
    int m;

    public Pair(int n, int m) {
        this.n = n;
        this.m = m;
    }

    public Pair() {
        this.n = 0;
        this.m = 0;
    }

    public void printPair(int[] pair) {
        System.out.println("(" + this.n + ", " + m + ")");
    }

    @Override
    public String toString() {
        return "(" + this.n + ", " + m + ")";
    }

    public int getDifference() {
        return Math.abs(this.n - this.m);
    }

    public static int getDifference(int n, int m) {
        return Math.abs(n - m);
    }
}
