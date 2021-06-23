package ue99_huffman;

/**
 * Mit Hilfe der State-Machine und der ColorCodes-Klasse
 * kann ein beliebiges Huffman-komprmiertes Bild in
 * ein unkomprimiertes Bild umgewandelt werden
 */
public enum Huffman {
    START {
        @Override
        public Huffman nextState(IColor iColor, int bit) {
            if (bit == 1) {
                iColor.addColor(colorCodes.getRGB("1"));
                return START;
            }
            return CODE0;
        }
    },
    CODE0 {
        @Override
        public Huffman nextState(IColor iColor, int bit) {
            if (bit == 1) {
                iColor.addColor(colorCodes.getRGB("01"));
                return START;
            }
            return CODE00;
        }
    },
    CODE00 {
        @Override
        public Huffman nextState(IColor iColor, int bit) {
            if (bit == 1) {
                return CODE001;
            }
            return CODE000;
        }
    },
    CODE001 {
        @Override
        public Huffman nextState(IColor iColor, int bit) {
            if (bit == 1) {
                iColor.addColor(colorCodes.getRGB("0011"));
            } else if (bit == 0) {
                iColor.addColor(colorCodes.getRGB("0010"));
            }
            return START;
        }
    },
    CODE000 {
        @Override
        public Huffman nextState(IColor iColor, int bit) {
            if (bit == 0) {
                iColor.addColor(colorCodes.getRGB("0000"));
                return START;
            }
            return CODE0001;
        }
    },
    CODE0001 {
        @Override
        public Huffman nextState(IColor iColor, int bit) {
            if (bit == 1) {
                iColor.addColor(colorCodes.getRGB("00011"));
            } else if (bit == 0) {
                iColor.addColor(colorCodes.getRGB("00010"));
            }
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
     * @return der neue Zustand
     */
    public abstract Huffman nextState(IColor iColor, int bit);
}