package ue02_enum2;

interface Increment{
    void incWords();
}

public enum StateMachine {
    OUT_WORD {
        @Override
        public StateMachine nextState(Increment context, char c){
            if (Character.isLetterOrDigit(c)){
                context.incWords();
                return IN_WORD;
            }

            if (c == '<'){
                return IN_TAG;
            }

            return OUT_WORD;
        }
    },

    IN_WORD {
        @Override
        public StateMachine nextState(Increment context, char c){
            if (!Character.isLetterOrDigit(c)){
                return OUT_WORD;
            }

            return IN_WORD;
        }
    },

    IN_TAG {
        @Override
        public StateMachine nextState(Increment context, char c){
            if (c == '>'){
                return OUT_WORD;
            }

            if (c == '\"'){
                return IN_STRING;
            }

            return IN_TAG;
        }
    },

    IN_STRING {
        @Override
        public StateMachine nextState(Increment context, char c){
            if (c == '\\'){
                return IGNORE;
            }

            return IN_STRING;
        }
    },

    IGNORE {
        @Override
        public StateMachine nextState(Increment context, char c){
            return IN_STRING;
        }
    };

    public abstract StateMachine nextState(Increment context, char c);
}
