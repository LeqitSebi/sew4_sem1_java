package ue01_whp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ue01_whp.Bundesland.BUNDESLAENDER;

public class streams {
    public static void main(String[] args) {
        System.out.println("Liste aller Bundesländer mit weniger als 500000 Einwohner: " + population_lower_than());
        System.out.println("Fläche des größten Bundeslandes: " + biggest_bundesland());
        System.out.println("Liste der Länge der Namen der Landeshauptstätde" + capital_name());
    }

    public static List<String> population_lower_than(){
        return BUNDESLAENDER.stream().filter(n -> n.getEinwohner() < 500000).map(Bundesland::getName).collect(Collectors.toList());
    }

    public static int biggest_bundesland(){
        return BUNDESLAENDER.stream().mapToInt(Bundesland::getFlaeche).max().getAsInt();
    }

    public static List<Integer> capital_name(){
        return BUNDESLAENDER.stream().map(Bundesland::getLandeshauptstadt).mapToInt(String::length).boxed().collect(Collectors.toList());
    }
}
