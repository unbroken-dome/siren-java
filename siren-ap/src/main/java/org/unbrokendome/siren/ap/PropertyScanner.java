package org.unbrokendome.siren.ap;

import com.google.common.base.CaseFormat;
import com.google.common.base.Preconditions;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementScanner8;
import java.util.LinkedHashMap;
import java.util.Map;


public abstract class PropertyScanner<R, P> extends ElementScanner8<R, P> {

    private TypeElement type;
    private final Map<String, PropertyInfo> properties = new LinkedHashMap<>();


    private static final class PropertyInfo {
        private final String name;
        private final TypeMirror type;
        private final ExecutableElement getter;
        private final ExecutableElement setter;


        public PropertyInfo(String name, TypeMirror type,
                            @Nullable ExecutableElement getter,
                            @Nullable ExecutableElement setter) {
            this.name = name;
            this.type = type;
            this.getter = getter;
            this.setter = setter;
        }


        @Nonnull
        public String getName() {
            return name;
        }


        @Nonnull
        public TypeMirror getType() {
            return type;
        }


        @Nullable
        public ExecutableElement getGetter() {
            return getter;
        }


        @Nullable
        public ExecutableElement getSetter() {
            return setter;
        }


        public static PropertyInfo fromElement(ExecutableElement e) {
            String name = e.getSimpleName().toString();
            if (name.startsWith("get") && name.length() > 3 && Character.isUpperCase(name.charAt(3))
                    && e.getReturnType().getKind() != TypeKind.VOID
                    && e.getParameters().isEmpty()) {
                String propertyName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, name.substring(3));
                TypeMirror propertyType = e.getReturnType();
                return new PropertyInfo(propertyName, propertyType, e, null);

            } else if (name.startsWith("is") && name.length() > 2 && Character.isUpperCase(name.charAt(2))
                    && e.getReturnType().getKind() == TypeKind.BOOLEAN
                    && e.getParameters().isEmpty()) {
                String propertyName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, name.substring(2));
                TypeMirror propertyType = e.getReturnType();
                return new PropertyInfo(propertyName, propertyType, e, null);

            } else if (name.startsWith("set") && name.length() > 3 && Character.isUpperCase(name.charAt(3))
                    && e.getReturnType().getKind() == TypeKind.VOID
                    && e.getParameters().size() == 1) {
                String propertyName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, name.substring(3));
                TypeMirror propertyType = e.getParameters().get(0).asType();
                return new PropertyInfo(propertyName, propertyType, null, e);

            } else {
                return null;
            }
        }


        public PropertyInfo merge(PropertyInfo other) {
            Preconditions.checkArgument(name.equals(other.getName()), "Property names do not match");
            return new PropertyInfo(name, type,
                    getter != null ? getter : other.getGetter(),
                    setter != null ? setter : other.getSetter());
        }
    }


    @Override
    public R visitType(TypeElement e, P p) {
        if (this.type != null) {
            return null;
        }
        this.type = e;
        R result = super.visitType(e, p);
        for (PropertyInfo property : properties.values()) {
            result = visitProperty(property.getName(), property.getType(),
                    property.getGetter(), property.getSetter(), p);
        }
        return result;
    }


    @Override
    public R visitExecutable(ExecutableElement e, P p) {
        PropertyInfo propertyInfo = PropertyInfo.fromElement(e);
        if (propertyInfo != null) {
            properties.merge(propertyInfo.getName(), propertyInfo, PropertyInfo::merge);
        }
        return null;
    }


    @Nullable
    public abstract R visitProperty(String propertyName,
                                    TypeMirror propertyType,
                                    @Nullable ExecutableElement getter,
                                    @Nullable ExecutableElement setter,
                                    P p);
}
