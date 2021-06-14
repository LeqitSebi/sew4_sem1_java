package ue99_huffman;

import java.io.IOException;

/**
 * Diese Klasse dient dazu, ein Huffman-(RLE-) komprimiert Bild
 * in ein normales PNG-Bild umzuwandeln.
 */
public class ConvertHuffmann2fileImage {
    /**
     * Wandelt eine Huffman-(RLE-) komprimierte Datei in eine normale Bild-Datei um.<br>
     *     Erlaubte Formate: png, jpeg (Die Dateiendung der dest-Datei gibt das Dateiformat an.)
     *
     * @param src    die Huffman (und eventuell auch RLE) komprimiert Datei (Dateiname)
     * @param dest   die zu erstellende Bild-Datei (Dateiname)
     * @param isRLE  gibt an, ob die src-Datei auch RLE-komprimiert ist
     * @throws IOException
     */
    public static void convertHuffman2fileImage(String src, String dest, boolean isRLE) throws IOException {
        HuffmanImage hImg = new HuffmanImage(src);
        NormalImage  nImg = new NormalImage(hImg.getWidth(), hImg.getHeight());

        // TODO: Code ergaenzen

        nImg.writeImageTo(dest);
    }


    public static void main(String[] args) throws IOException {

        System.out.println(ConvertHuffmann2fileImage.class.getSimpleName() + " finished");
    }
}
