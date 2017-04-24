package org.unbrokendome.siren.ap;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.AnnotatedConstruct;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public final class AnnotationUtils {

    private AnnotationUtils() { }


    @Nullable
    public static AnnotationMirror findAnnotationMirror(AnnotatedConstruct annotatedElement, TypeElement annotationType) {
        return annotatedElement.getAnnotationMirrors().stream()
                .filter(annotationMirror -> annotationMirror.getAnnotationType().asElement().equals(annotationType))
                .findFirst().orElse(null);
    }


    public static boolean hasAnnotation(Element annotatedElement, TypeElement annotationType) {
        return findAnnotationMirror(annotatedElement, annotationType) != null;
    }


    @Nullable
    public static AnnotationMirror findAnnotationOnElementOrType(Element annotatedElement, TypeElement annotationType) {
        AnnotationMirror elementAnnotation = findAnnotationMirror(annotatedElement, annotationType);
        if (elementAnnotation != null) {
            return elementAnnotation;
        }

        TypeMirror elementType = ElementUtils.getType(annotatedElement);
        return findAnnotationMirror(elementType, annotationType);
    }


    @Nullable
    public static <A extends Annotation> A findAnnotationOnElementOrType(Element annotatedElement, Class<A> annotationType) {
        A elementAnnotation = annotatedElement.getAnnotation(annotationType);
        if (elementAnnotation != null) {
            return elementAnnotation;
        }

        TypeMirror elementType = ElementUtils.getType(annotatedElement);
        return elementType.getAnnotation(annotationType);
    }


    @Nonnull
    public static Map<String, AnnotationValue> getElementValuesByName(
            AnnotationMirror annotation) {
        ImmutableMap.Builder<String, AnnotationValue> builder = ImmutableMap.builder();
        annotation.getElementValues().forEach((element, value) ->
            builder.put(element.getSimpleName().toString(), value)
        );
        return builder.build();
    }


    @Nullable
    public static AnnotationValue getElementValueByName(
            AnnotationMirror annotation,
            String elementName) {
        return annotation.getElementValues().entrySet().stream()
                .filter(entry -> entry.getKey().getSimpleName().contentEquals(elementName))
                .map(entry -> (AnnotationValue) entry.getValue())
                .findFirst()
                .orElse(null);
    }


    @SuppressWarnings("unchecked")
    @Nonnull
    public static <T> List<T> getElementValueAsList(@Nullable AnnotationValue elementValue) {
        if (elementValue == null) {
            return ImmutableList.of();
        }

        Object value = elementValue.getValue();
        if (value instanceof List<?>) {
            List<AnnotationValue> listValue = (List<AnnotationValue>) value;
            return listValue.stream()
                    .map(it -> (T) it.getValue())
                    .collect(Collectors.toList());
        } else {
            return ImmutableList.of((T) value);
        }
    }


    @Nonnull
    public static <T> List<T> getElementValueAsList(AnnotationMirror annotation, String elementName) {
        AnnotationValue elementValue = getElementValueByName(annotation, elementName);
        return getElementValueAsList(elementValue);
    }


    @Nullable
    public static String getElementValueAsString(@Nullable AnnotationValue elementValue) {
        if (elementValue != null) {
            Object value = elementValue.getValue();
            return value.toString();
        }
        return null;
    }


    @Nullable
    public static String getElementValueAsString(AnnotationMirror annotation, String elementName) {
        AnnotationValue elementValue = getElementValueByName(annotation, elementName);
        return getElementValueAsString(elementValue);
    }


    @Nullable
    public static Boolean getElementValueAsBoolean(@Nullable AnnotationValue elementValue) {
        if (elementValue != null) {
            Object value = elementValue.getValue();
            return (Boolean) value;
        }
        return null;
    }


    @Nullable
    public static Boolean getElementValueAsBoolean(AnnotationMirror annotation, String elementName) {
        AnnotationValue elementValue = getElementValueByName(annotation, elementName);
        return getElementValueAsBoolean(elementValue);
    }
}
