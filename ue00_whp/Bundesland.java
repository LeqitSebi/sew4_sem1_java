package ue00_whp;

import java.util.Arrays;
import java.util.List;

public class Bundesland {
    public static final List<Bundesland> BUNDESLAENDER = Arrays.asList(
            new Bundesland("Oberösterreich (OÖ)","Linz",1482095,11980),
            new Bundesland("Niederösterreich (NÖ)","St. Pölten",1677542,19186),
            new Bundesland("Kärnten (Ktn.)","Klagenfurt am Wörthersee",560939,9538),
            new Bundesland("Steiermark (Stmk.)","Graz",1243052,16401),
            new Bundesland("Salzburg (Sbg.)","Salzburg",555221,7156),
            new Bundesland("Tirol (T)","Innsbruck",754705,12640),
            new Bundesland("Burgenland (Bgld.)","Eisenstadt",293433,3962),
            new Bundesland("Vorarlberg (Vbg.)","Bregenz",397094,2601),
            new Bundesland("Wien (W)","–",1897491,415)
            );

    private String name;
    private String landeshauptstadt;
    private int einwohner;
    private int flaeche;

    public Bundesland(String name, String landeshauptstadt, int einwohner, int flaeche) {
        this.name = name;
        this.landeshauptstadt = landeshauptstadt;
        this.einwohner = einwohner;
        this.flaeche = flaeche;
    }

    public String getName() {
        return name;
    }

    public String getLandeshauptstadt() {
        return landeshauptstadt;
    }

    public int getEinwohner() {
        return einwohner;
    }

    public int getFlaeche() {
        return flaeche;
    }
}
