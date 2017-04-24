package org.unbrokendome.siren.ap.model.affordance.action;

import com.google.common.collect.ImmutableMap;
import org.unbrokendome.siren.annotation.ActionFieldType;
import org.unbrokendome.siren.annotation.SirenClass;
import org.unbrokendome.siren.annotation.Title;
import org.unbrokendome.siren.ap.AnnotationUtils;
import org.unbrokendome.siren.ap.ElementUtils;
import org.unbrokendome.siren.model.ActionField;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public final class ActionFields {

    private static final Map<String, ActionField.Type> DEFAULT_MAPPING = ImmutableMap.<String, ActionField.Type>builder()
            .put("java.lang.String", ActionField.Type.TEXT)
            .put("java.net.URI", ActionField.Type.URL)
            .put("java.net.URL", ActionField.Type.URL)
            .put("java.util.Date", ActionField.Type.DATETIME)
            .put("java.time.Instant", ActionField.Type.DATETIME)
            .put("java.time.ZonedDateTime", ActionField.Type.DATETIME)
            .put("java.time.LocalDate", ActionField.Type.DATE)
            .put("java.time.YearMonth", ActionField.Type.MONTH)
            .put("java.time.LocalTime", ActionField.Type.TIME)
            .put("java.time.LocalDateTime", ActionField.Type.DATETIME_LOCAL)
            .put("java.lang.Integer", ActionField.Type.NUMBER)
            .put("java.lang.Long", ActionField.Type.NUMBER)
            .put("java.lang.Float", ActionField.Type.NUMBER)
            .put("java.lang.Double", ActionField.Type.NUMBER)
            .put("java.math.BigDecimal", ActionField.Type.NUMBER)
            .put("java.lang.Boolean", ActionField.Type.CHECKBOX)
            .build();

    private static final Map<TypeKind, ActionField.Type> PRIMITIVES_MAPPING = ImmutableMap.<TypeKind, ActionField.Type>builder()
            .put(TypeKind.BOOLEAN, ActionField.Type.CHECKBOX)
            .put(TypeKind.INT, ActionField.Type.NUMBER)
            .put(TypeKind.LONG, ActionField.Type.NUMBER)
            .put(TypeKind.FLOAT, ActionField.Type.NUMBER)
            .put(TypeKind.DOUBLE, ActionField.Type.NUMBER)
            .build();


    private ActionFields() { }


    public static ActionField fromElement(Element element, String name) {
        return new ActionField(name,
                fieldTypeFromElement(element),
                null,
                fieldClassesFromElement(element),
                fieldTitleFromElement(element),
                Collections.emptyMap());
    }


    @Nonnull
    public static ActionField.Type fieldTypeFromElement(Element element) {
        ActionFieldType annotation = AnnotationUtils.findAnnotationOnElementOrType(element, ActionFieldType.class);
        if (annotation != null) {
            return annotation.value();
        }
        return getDefaultFieldType(ElementUtils.getType(element));
    }


    @Nonnull
    public static ActionField.Type getDefaultFieldType(TypeMirror type) {
        if (type.getKind().isPrimitive()) {
            return PRIMITIVES_MAPPING.get(type.getKind());

        } else if (type.getKind() == TypeKind.DECLARED) {
            TypeElement typeElement = (TypeElement) ((DeclaredType) type).asElement();
            String qualifiedName = typeElement.getQualifiedName().toString();
            return DEFAULT_MAPPING.get(qualifiedName);

        } else {
            return ActionField.Type.TEXT;
        }
    }


    @Nonnull
    public static List<String> fieldClassesFromElement(Element element) {
        SirenClass annotation = AnnotationUtils.findAnnotationOnElementOrType(element, SirenClass.class);
        return (annotation != null) ? Arrays.asList(annotation.value()) : Collections.emptyList();
    }


    @Nullable
    public static String fieldTitleFromElement(Element element) {
        Title annotation = AnnotationUtils.findAnnotationOnElementOrType(element, Title.class);
        return (annotation != null) ? annotation.value() : null;
    }
}
