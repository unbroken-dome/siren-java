package org.unbrokendome.siren.annotation;

import java.lang.annotation.*;


@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface Title {

    String value();
}
