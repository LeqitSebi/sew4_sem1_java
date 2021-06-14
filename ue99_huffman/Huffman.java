package ue99_huffman;

/**
 * Mit Hilfe der State-Machine und der ColorCodes-Klasse
 * kann ein beliebiges Huffman-komprmiertes Bild in
 * ein unkomprimiertes Bild umgewandelt werden
 */
public enum Huffman {
    // TODO: da fehlen noch jede Menge States
    START {
        @Override
        public Huffman nextState(IColor iColor, int bit) {
            // TODO: ersetze den Dummy-Code durch etwas Vernuenftiges
            iColor.addColor(colorCodes.getRGB("1")); // colorCodes.getRGB: Wandelt den BitCode in den RGB-Wert um
            return START;
        }
    };

    /**
     * Definition der Zuordnungen der BitCodes zun den png-Werten
     */
    public static ColorCodes colorCodes = new ColorCodes('b');


    /**
     * Berechnet den naechsten Zustand und teilt iColor die Farbe mit
     * wenn alle Bits eines  BitCodes abgearbeitet wurden.
     *
     * @param iColor um die neue Farbe einzutragen
     * @param bit    das naechste Bit, das verarbeitet werden soll
     * @return       der neue Zustand
     */
    public abstract Huffman nextState(IColor iColor, int bit);
}