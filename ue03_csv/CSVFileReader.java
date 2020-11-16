package ue03_csv;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CSVFileReader implements Closeable, Iterable<List<String>> {

    private BufferedReader reader;
    private CSVReader csv = new CSVReader();
    private String nextLine;

    public CSVFileReader(String filename) {
        try {
            reader = Files.newBufferedReader(Paths.get(filename));
            nextLine = reader.readLine();
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid Filename");
        }
    }

    public CSVFileReader(String filename, char delimiter) {
        this(filename);
        csv = new CSVReader(delimiter);
    }

    public CSVFileReader(String filename, char delimiter, char doubleqoute) {
        this(filename);
        csv = new CSVReader(delimiter, doubleqoute);
    }

    public CSVFileReader(String filename, char delimiter, char doubleqoute, boolean skipInitialWhitespace) {
        this(filename);
        csv = new CSVReader(delimiter, doubleqoute, skipInitialWhitespace);
    }

    public List<String> next() {
        try {
            if (nextLine == null) {
                reader.close();
                return null;
            }
            String line = nextLine;
            nextLine = reader.readLine();
            return Arrays.asList(csv.next(line));
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }

    @Override
    public Iterator<List<String>> iterator() {
        CSVFileReader context = this;
        return new Iterator<List<String>>() {

            @Override
            public boolean hasNext() {
                return context.nextLine != null;
            }

            @Override
            public List<String> next() {
                return context.next();
            }
        };
    }
}
