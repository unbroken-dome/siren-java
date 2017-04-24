package org.unbrokendome.siren.ap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;


public class ElementUtils {

    @SuppressWarnings("unchecked")
    @Nonnull
    public static <T extends Element> T getEnclosingElementOfType(
            @Nonnull Element element, ElementKind kind) {
        Element enclosingElement = element.getEnclosingElement();
        while (enclosingElement != null && enclosingElement.getKind() != kind) {
            enclosingElement = enclosingElement.getEnclosingElement();
        }

        if (enclosingElement == null) {
            throw new IllegalArgumentException("Element " + element + " is not enclosed in an element of kind " + kind);
        }

        return (T) enclosingElement;
    }


    public static TypeMirror getType(Element element) {
        if (element.getKind() == ElementKind.METHOD) {
            return ((ExecutableElement) element).getReturnType();
        } else {
            return element.asType();
        }
    }


    @Nullable
    public static <T extends Element> T findElementByName(Iterable<T> elements, String name) {
        for (T element : elements) {
            if (element.getSimpleName().contentEquals(name)) {
                return element;
            }
        }
        return null;
    }
}
