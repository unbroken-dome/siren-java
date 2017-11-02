package org.unbrokendome.siren.spring.ap.util;

public final class ClassUtils {

    public static boolean isPresent(String className) {
        try {
            Class.forName(className);
            return true;
        }
        catch (Throwable ex) {
            return false;
        }
    }
}
