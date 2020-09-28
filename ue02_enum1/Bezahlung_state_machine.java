package ue02_enum1;

import static ue02_enum1.State.*;

public class Bezahlung_state_machine {

    public static void main(String[] args) {
        System.out.println(wordcount_stateMachine("eins     zwei.drei <sicher nicht vier>"));
    }

    public static int wordcount_stateMachine(String n) {
        State activeState = OUT_WORD;
        int counter = 0;
        for (int i = 0; i < n.length(); i++) {
            char current = n.charAt(i);
            switch (activeState) {
                case OUT_WORD:
                    if (current == '<'){
                     activeState = IN_TAG;
                    } else if (Character.isDigit(current) || Character.isLetter(current)){
                        counter++;
                        activeState = IN_WORD;
                    }
                    break;

                case IN_WORD:
                    if (!Character.isDigit(current) && !Character.isLetter(current)) {
                        activeState = OUT_WORD;
                    }
                    break;

                case IN_TAG:
                    if (current == '>'){
                        activeState = OUT_WORD;
                    } else if (current == '\"'){
                        activeState = IN_STRING;
                    }
                    break;

                case IN_STRING:
                    if (current == '\"'){
                        activeState = IN_TAG;
                    } else if (current == '\\'){
                        activeState = IGNORE;
                    }
                    break;

                case IGNORE:
                    if (current != '\\'){
                        activeState = IN_STRING;
                    }
                    break;
            }
        }
        return counter;
    }
}
