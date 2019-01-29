package ipwrc.helpers;

import java.text.Normalizer;

public class TextFormatter {

    public static String toTitle(String name) {
        return Normalizer.normalize(name, Normalizer.Form.NFD)
                .toLowerCase()
                .replaceAll("[^\\p{ASCII}]", "") // Replace to ASCII
                .replace("[^A-Za-z0-9]", "-") // Replace all the unknown characters to '-'
                .replace(" ", "-")
        ;
    }


}
