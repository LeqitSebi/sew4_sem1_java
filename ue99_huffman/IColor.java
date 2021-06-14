package ue99_huffman;

/**
 * Das Interface dient der State-Machine ({@link Huffman}),
 * um das naechste Pixel mittels {@link #addColor(int)} zu setzen,
 * <br><br>
 * sowie der Konvertierungs-Funktion
 * {@link ConvertHuffmann2fileImage#convertHuffman2fileImage(String, String, boolean)}
 * um bei einer RLE-Komprimierung den Multiplikationsfaktor fuer das naechste
 * {@link #addColor(int)} anzugeben
 * <br><br>
 * <b>Achtung: Aendere an diesem Interface nichts.</b> */
public interface IColor{

    /**
     * Setzt das naechste Pixel auf den rgb-Wert.<br>
     * Bei einem RLE-komprimierten Pixel muss vor
     * dem Setzen des Pixels mittels {@link #addColor(int)}
     * @param color
     */
    public void addColor(int color);
    public void setNColor(int nColor);
}