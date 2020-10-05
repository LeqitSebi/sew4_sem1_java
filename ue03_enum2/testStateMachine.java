package ue03_enum2;

public class testStateMachine {
    public static void main(String[] args) {
        WordCount counter1 = new WordCount("eins zwei.drei     vier");
        System.out.println(counter1.getWords());
    }
}
