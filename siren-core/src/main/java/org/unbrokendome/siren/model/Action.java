package org.unbrokendome.siren.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public final class Action extends Reference {

    private final String name;
    private final String method;
    private final List<ActionField> fields;


    public Action(String name,
                  @Nullable String method,
                  String href,
                  @Nullable String type,
                  List<ActionField> fields,
                  List<String> classNames,
                  @Nullable String title,
                  Map<String, Object> customProperties) {
        super(href, type, classNames, title, customProperties);
        this.name = name;
        this.method = method;
        this.fields = fields;
    }


    @Nonnull
    public final String getName() {
        return name;
    }


    @Nullable
    public final String getMethod() {
        return method;
    }


    @Nonnull
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public final List<ActionField> getFields() {
        return fields;
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        return (obj instanceof Action) && equals((Action) obj);
    }


    private boolean equals(Action other) {
        return super.equals(other)
                && Objects.equals(name, other.name)
                && Objects.equals(method, other.method)
                && Objects.equals(fields, other.fields);
    }


    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, method, fields);
    }
}
