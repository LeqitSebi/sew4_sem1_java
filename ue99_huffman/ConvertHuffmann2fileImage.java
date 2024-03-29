package ue99_huffman;

import java.io.IOException;

/**
 * Diese Klasse dient dazu, ein Huffman-(RLE-) komprimiert Bild
 * in ein normales PNG-Bild umzuwandeln.
 */
public class ConvertHuffmann2fileImage {
    /**
     * Wandelt eine Huffman-(RLE-) komprimierte Datei in eine normale Bild-Datei um.<br>
     * Erlaubte Formate: png, jpeg (Die Dateiendung der dest-Datei gibt das Dateiformat an.)
     *
     * @param src   die Huffman (und eventuell auch RLE) komprimiert Datei (Dateiname)
     * @param dest  die zu erstellende Bild-Datei (Dateiname)
     * @param isRLE gibt an, ob die src-Datei auch RLE-komprimiert ist
     * @throws IOException
     */
    public static void convertHuffman2fileImage(String src, String dest, boolean isRLE) throws IOException {
        HuffmanImage hImg = new HuffmanImage(src);
        NormalImage nImg = new NormalImage(hImg.getWidth(), hImg.getHeight());
        Huffman state = Huffman.START;
        try {
            if (isRLE) {
                while (true) {
                    if (state == Huffman.START) {
                        nImg.setNColor(hImg.getNColor());
                    }
                    state = state.nextState(nImg, hImg.getBit());
                }
            }
        } catch (Exception e) {

        }
        try {
            while (true) {
                state = state.nextState(nImg, hImg.getBit());
            }
        } catch (Exception e) {
        }

        nImg.writeImageTo(dest);
    }


    public static void main(String[] args) throws IOException {
        convertHuffman2fileImage("../resources/b_01.huffman", "../resources/a_01.png", false);
        convertHuffman2fileImage("../resources/b_01.huffman", "../resources/a_01.jpeg", false);

        convertHuffman2fileImage("../resources/b_01.huffman.rle", "../resources/a_01RLE.png", true);
        convertHuffman2fileImage("../resources/b_01.huffman.rle", "../resources/a_01RLE.jpeg", true);

        convertHuffman2fileImage("../resources/b_01.huffman", "../resources/b_01.png", false);
        convertHuffman2fileImage("../resources/b_01.huffman", "../resources/b_01.jpeg", false);

        convertHuffman2fileImage("../resources/b_01.huffman.rle", "../resources/b_01RLE.png", true);
        convertHuffman2fileImage("../resources/b_01.huffman.rle", "../resources/b_01RLE.jpeg", true);
        System.out.println(ConvertHuffmann2fileImage.class.getSimpleName() + " finished");
    }
}
