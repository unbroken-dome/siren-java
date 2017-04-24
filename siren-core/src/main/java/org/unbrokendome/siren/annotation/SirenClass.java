package org.unbrokendome.siren.annotation;

import java.lang.annotation.*;


@Target({ ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.SOURCE)
@Documented
public @interface SirenClass {

    String[] value();
}
