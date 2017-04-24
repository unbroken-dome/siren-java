package org.unbrokendome.siren.model;


import com.fasterxml.jackson.annotation.JsonValue;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public final class ActionField extends Element {

    private final String name;
    private final Type type;
    private final Object value;


    public ActionField(String name,
                       @Nullable Type type,
                       @Nullable Object value,
                       List<String> classes,
                       @Nullable String title,
                       Map<String, Object> customProperties) {
        super(classes, title, customProperties);
        this.name = name;
        this.type = type;
        this.value = value;
    }


    public ActionField(String name, @Nullable Type type) {
        this(name, type, null, Collections.emptyList(), null, Collections.emptyMap());
    }


    @Nonnull
    public final String getName() {
        return name;
    }


    @Nullable
    public final Type getType() {
        return type;
    }


    @Nullable
    public final Object getValue() {
        return value;
    }


    @SuppressWarnings("unused")
    public enum Type {

        NONE {
            @Override
            public String toJson() {
                return null;
            }
        },
        HIDDEN,
        TEXT,
        SEARCH,
        TEL,
        URL,
        EMAIL,
        PASSWORD,
        DATETIME,
        DATE,
        MONTH,
        WEEK,
        TIME,
        DATETIME_LOCAL,
        NUMBER,
        RANGE,
        COLOR,
        CHECKBOX,
        RADIO,
        FILE,
        IMAGE,
        BUTTON;


        @JsonValue
        public String toJson() {
            return name()
                    .replaceAll("_", "-")
                    .toLowerCase();
        }
    }


    @Override
    public boolean equals(Object obj) {
        return obj instanceof ActionField && equals((ActionField) obj);
    }


    private boolean equals(ActionField other) {
        return super.equals(other)
                && Objects.equals(name, other.name)
                && Objects.equals(type, other.type)
                && Objects.equals(value, other.value);
    }


    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, type, value);
    }
}
