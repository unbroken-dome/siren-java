package org.unbrokendome.siren.annotation;

import org.unbrokendome.siren.model.ActionField;

import java.lang.annotation.*;


@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.CLASS)
@Inherited
@Documented
public @interface ActionFieldType {

    ActionField.Type value();
}
