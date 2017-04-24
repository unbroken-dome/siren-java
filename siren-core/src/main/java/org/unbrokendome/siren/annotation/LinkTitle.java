package org.unbrokendome.siren.annotation;

import java.lang.annotation.*;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)
@Repeatable(LinkTitles.class)
@Documented
public @interface LinkTitle {

    String value();

    String rel() default "";
}
