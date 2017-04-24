package org.unbrokendome.siren.ap.model.affordance;

import javax.annotation.Nullable;


public class AffordanceTitleUtils {

    private AffordanceTitleUtils() { }


    public static boolean isResolvableTitle(@Nullable String title) {
        return title != null
                && title.length() >= 2
                && title.charAt(0) == '{'
                && title.charAt(title.length() - 1) == '}';
    }
}
