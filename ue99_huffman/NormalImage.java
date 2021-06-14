package ue99_huffman;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * Die Klasse kapselt den Zugriff auf ein normales (nicht Huffman oder REL komprimien) Bild.
 * <br><br>>
 *
 * <b>Achtung: Aendere an dieser Klasse nichts.</b>
 */
public class NormalImage extends BufferedImage implements IColor {
    /**
     * Gibt an, wie viele Pixel mit {@link #addColor(int)} versehen werden sollen.
     * Ohne RLE-Komprimierung ist der Wert immer 1.
     */
    private int nColor = 1;

    /** Gibt die Position des naechsten zu setzenden Pixels an. */
    private int pos;

    /**
     * Erzeugt ein Bild mit frei waehlbarer Groesze.
     *
     * @param width  Bildbreite
     * @param height Bildhoehe
     */
    public NormalImage(int width, int height) {
        super(width, height, BufferedImage.TYPE_INT_RGB);
    }

    /**
     * Diese Funktion wird nur fuer RLE-komprimierte Bilder benoetigt,
     * um anzugeben, wie viele Pixel mti dem naechsten {@link #addColor(int)}
     * Befehl gesetzt werden sollen (1-127 Pixel)
     * @param nColor
     */
    public void setNColor(int nColor) {
        this.nColor = nColor;
    }

    /**
     * Setzt das naechste Pixel auf den angegebenen rgb-Wert, <br>
     * oder setzt bei RLE-komprimierten Bildern die nächsten nColor Pixel.
     *
     * @param rgb die Pixel-Farbe
     */
    public void addColor(int rgb) {
        for (int i = 0; i < nColor; i++) {
            int y = pos / getWidth();
            int x = pos % getWidth();
            pos++;

            setRGB(x, y, rgb);
        }
    }

    /**
     * @return true --> alle Pixel wurden eingefaerbt, das Bild ist vollstaendig aufgebaut
     */
    public boolean isFinished() {
        return pos >= getWidth() * getHeight();
    }

    /**
     * Schreibt das Bild auf die Festplatte hinaus.
     * Die Datei-Endung gibt den Typ an. Erlaubt sind:
     * png, jpeg (Achte genau auf die Schreibweise!)
     *
     * @param dest der Dateiname inkl. Pfad
     * @return ob der Bild-Typ unterstuetzt wird
     * @throws IOException
     */
    public boolean writeImageTo(String dest) throws IOException {
        final String type = dest.substring(dest.lastIndexOf('.') + 1);
        System.out.println("type: " + type);
        return ImageIO.write(this, type, new File(dest));
    }

    /**
     * @return liefert die Pixel-Position, auf die als naechstes geschrieben wird.
     */
    public int getPos() {
        return pos;
    }
}
