package ue99_huffman;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Kapselt den (bitweisen) Zugriff auf das Huffman-komprimierte-Bild
 */
public class HuffmanImage {
    /** Die Bits des Huffman-komprimierte-Bild */
    private byte[] img;

    /**
     * die aktuelle Leseposition, mit {@link #getBit()}
     * und {@link #getNColor()} wird diese Position automatisch weitergerückt
     */
    private int pos = 8*8;

    /**
     * Liest das Huffman-komprimierte-Bild ein.
     *
     * @param src           der Dateiname inkl. Pfad zum Huffman-komprimierte-Bild
     * @throws IOException  bei Lese-Problemen
     */
    public HuffmanImage(String src) throws IOException {
        img = Files.readAllBytes(Paths.get(src));
    }

    /**
     * Liest aus dem Huffman-kodierten-Bild (dem byte-Array "bytes")
     * eine vier-byte big-endian Integer-Zahl aus.
     *
     * @param bytes das Huffman-kodierten-Bild
     * @param pos die Leseposition
     * @return der Integer-Wert
     */
    public static int bigEndian2int(byte[] bytes, int pos) {
        // TODO: ersetze den folgenden Dummy-Code, so dass tatsaechlich
        // TODO: die Breite und die Hoehe aus dem Huffman-kodierten-Bild ausgelesen wird
        if (pos == 0) {
            return 1200; // = width (Bild-Breite)
        } else {
            return 374;  // = height (Bild-Hoehe)
        }
    }

    /**
     * @return die Bild-Hoehe (bei unserem Beispiel-Bild: 374 Pixel)
     */
    public int getHeight() {
        return bigEndian2int(img, 4);
    }

    /**
     * @return die Bild-Breite (bei unserem Beispiel-Bild: 1.200 Pixel)
     */
    public int getWidth() {
        return bigEndian2int(img, 0);
    }

    /**
     * Liefert bei RLE-komprimierten-Bildern die Anzahl, wie oft eine Farbe vorkommt.<br>
     * <b>Achtung: </b>Bei <b>nicht RLE-komprimierten-Bildern</b> darf die Methode nicht verwendet werden!!
     * @return wie viele Pixel die danach folgende FarbE (gegeben durch einen Huffman-bit-code) haben
     */
    public int getNColor(){
        int nColor = getBit();

        if (nColor==0) {
            for (int i=0; i<7; i++) {
                nColor = (nColor<<1) | getBit();
            }
        }

        return nColor;
    }

    /**
     * @return das naechste Bit ({@link #pos} wird um eins erhoeht)
     */
    public int getBit() {
        int byteNr = pos / 8;
        int bitNr  = pos % 8;
        pos++;

        return (img[byteNr] >> bitNr) & 1;
    }
}