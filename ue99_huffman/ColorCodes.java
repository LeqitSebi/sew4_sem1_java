package ue99_huffman;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Verwaltet die Color-Codes und die Bit-Codes.<br><br>
 *
 * siehe vor allem auf die folgenden Methoden und den Konstrukor:
 * <ul>
 *      <li>Erzeugen eines Color-Codes-Objekt fuer die Gruppe A:<br>
 *          <code></code>ColorCodes colorCodes = new ColorCodes('a');
 *          // {@link #ColorCodes(char)}</code><br><br></li>
 *
 *      <li>Ermitteln des rgb-Farbwerts fuer den Bit-Code "10":<br>
 *          <code>int rgb = colorCodes.getRGB("10");
 *          // {@link #getRGB(String)}</code><br><br></li>
 *
 *      <li>Ermitteln des Bit-Codes fuer den rgb-Farbwerts 0xbbd0af:<br>
 *          <code>String bitCode = colorCodes.getBitCode(0xbbd0af);
 *          // {@link #getBitCode(int)}</code><br><br></li>
 *
 *      <li>Ausgabe der gesamten Farb-Code-Tabelle:<br>
 *          <code>System.out.println(colorCodes);
 *          // {@link #toString()}</code><br><br></li>
 * </ul>
 *
 * <b>Achtung: Aendere an dieser Klasse nichts.</b>
 */
public class ColorCodes {
    /**
     * Die Map dient zum Ermitteln des Bit-Codes fuer einen rgb-Werts
     */
    private Map<Integer, String> rgb2bitcode = new HashMap<>();


    /**
     * Die Map dient zum Ermitteln des rgb-Werts fuer einen Bit-Code
     */
    private Map<String, Integer> bitCode2rgb = new LinkedHashMap<>();


    /**
     * Die Funktion gibt den rgb-Wert und den bitCode in die beiden Maps:<br>
     *     {@link #rgb2bitcode} und {@link #bitCode2rgb}
     *
     * @param rgb       der rgb-Wert
     * @param bitCode   der Bit-Code
     */
    private void addCode (int rgb, String bitCode) {
        rgb2bitcode.put(rgb, bitCode);
        bitCode2rgb.put(bitCode, rgb);
    }


    /**
     * Erzeugt ein Color-Objekt fuer die gewuenschte Gruppen-Nummer.
     *
     * @param groupNumber Gruppen-Nummer
     */
    public ColorCodes(char groupNumber) {
        switch (Character.toLowerCase(groupNumber)) {
            case 'a':
                addCode(0xbbd0af, "1");
                addCode(0xebf1e6, "01");
                addCode(0x191a18, "000");
                addCode(0x535f43, "0010");
                addCode(0xf6c488, "00110");
                addCode(0x72a93b, "001110");
                addCode(0x615163, "001111");
                break;
            case 'b':
                addCode(0x1d1a1a, "0000");
                addCode(0xc1031f, "00010");
                addCode(0x605855, "0010");
                addCode(0xa5968b, "0011");
                addCode(0xddc7d7, "1");
                addCode(0xf5cf88, "00011");
                addCode(0xf3eaf0, "01");
                break;
            default: throw new IllegalArgumentException("illegal group number: " + groupNumber);
        }
    }


    /**
     * Liefert den rgb-Wert fuer einen Bit-Code als 3-Byte-Zahl (ohne Alpha-Kanal)
     *
     * @param bitCode der Bit-Code
     * @return der rgb-Wert als 3-Byte-Zahl ohne Alpha-Kanal
     */
    public int getRGB(String bitCode) {
        Integer rgb = bitCode2rgb.get(bitCode);

        if (rgb == null) {
            throw new IllegalArgumentException("illegal bitCode: " + bitCode);
        }

        return rgb;
    }


    /**
     * Liefert den Bit-Code fuer einen rgb-Wert
     *
     * @param rgb der rgb-Wert als 3-Byte-Zahl ohne Alpha-Kanal
     * @return der Bit-Code
     */
    public String getBitCode(int rgb) {
        String bitCode = rgb2bitcode.get(rgb & 0xffffff);

        if (bitCode == null) {
            throw new IllegalArgumentException("unknown rgb value: " + rgb);
        }

        return bitCode;
    }


    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();

        b.append("bit number: rgb value\n");

        for (String bitCode: bitCode2rgb.keySet()) {
            b.append(String.format("%-10s: 0x%6x\n", bitCode, bitCode2rgb.get(bitCode)));
        }

        return b.toString();
    }
}