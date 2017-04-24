package org.unbrokendome.siren.annotation;

import java.lang.annotation.*;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
@Documented
public @interface LinkTitles {

    LinkTitle[] value();
}
