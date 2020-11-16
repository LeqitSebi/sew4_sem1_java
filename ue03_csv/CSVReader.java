package ue03_csv;

import java.util.LinkedList;
import java.util.List;

public class CSVReader {

    private String current;
    private List<String> row;

    private char delimiter = ',';
    private char doublequote = '"';
    private boolean skipInitialWhitespace = false;

    public CSVReader() {
        row = new LinkedList<>();
        current = "";
    }

    public CSVReader(char delimiter) {
        this();
        this.delimiter = delimiter;
    }

    public CSVReader(char delimiter, char doublequote) {
        this(delimiter);
        this.doublequote = doublequote;
    }

    public CSVReader(char delimiter, char doublequote, boolean skipInitialWhitespace) {
        this(delimiter, doublequote);
        this.skipInitialWhitespace = skipInitialWhitespace;
    }

    public static String[] split(String line) {
        CSVState state = CSVState.NOCELL;
        CSVReader context = new CSVReader();
        for (char ch : line.toCharArray()) {
            state = state.handleChar(ch, context);
        }
        context.row.add(context.current);
        context.current = "";
        return context.row.toArray(new String[] {});
    }

    public String[] next(String line) {
        CSVState state = CSVState.NOCELL;
        for (char ch : line.toCharArray()) {
            state = state.handleChar(ch, this);
        }
        row.add(current);
        current = "";
        String[] arr = row.toArray(new String[] {});
        row = new LinkedList<>();
        return arr;
    }

    enum CSVState {
        NOCELL {
            @Override
            public CSVState handleChar(char ch, CSVReader context) {
                if (ch == context.doublequote) {
                    return STRING;
                } else if (ch == context.delimiter) {
                    context.row.add("");
                    return NOCELL;
                } else if (Character.isWhitespace(ch)) {
                    if (!context.skipInitialWhitespace) {
                        throw new IllegalArgumentException("Invalid Cell");
                    }
                    return NOCELL;
                } else {
                    context.current += ch;
                    return CELL;
                }
            }
        }, CELL {
            @Override
            public CSVState handleChar(char ch, CSVReader context) {
                if (ch == context.delimiter) {
                    context.row.add(context.current);
                    context.current = "";
                    return NOCELL;
                } else {
                    context.current += ch;
                    return CELL;
                }
            }
        }, STRING {
            @Override
            public CSVState handleChar(char ch, CSVReader context) {
                if (ch == context.doublequote) {
                    return ESCAPED;
                } else {
                    context.current += ch;
                    return STRING;
                }
            }
        }, ESCAPED {
            @Override
            public CSVState handleChar(char ch, CSVReader context) {
                if (ch == context.doublequote) {
                    context.current += ch;
                    return STRING;
                } else {
                    return FINISHEDCELL.handleChar(ch, context);
                }
            }
        }, FINISHEDCELL {
            @Override
            public CSVState handleChar(char ch, CSVReader context) {
                if (ch == context.delimiter) {
                    context.row.add(context.current);
                    context.current = "";
                    return NOCELL;
                } else if (!Character.isWhitespace(ch) || !context.skipInitialWhitespace) {
                    throw new IllegalArgumentException("Invalid Cell");
                } else {
                    return FINISHEDCELL;
                }
            }
        };
        public abstract CSVState handleChar(char ch, CSVReader context);
    }

}
