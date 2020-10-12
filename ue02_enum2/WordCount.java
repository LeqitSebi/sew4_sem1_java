package ue02_enum2;

public class WordCount implements Increment{
    public int words;

    public WordCount(String html){
        StateMachine currentState = StateMachine.OUT_WORD;
        for (int i = 0; i < html.length(); i++) {
            currentState = currentState.nextState(this, html.charAt(i));
        }
    }

    public void incWords(){
        words++;
    }

    public int getWords() {
        return words;
    }
}
