package org.unbrokendome.siren.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class Element implements Serializable {

    private final List<String> classes;
    private final String title;
    private final Map<String, Object> customProperties;


    protected Element(List<String> classes, @Nullable String title, Map<String, Object> customProperties) {
        this.classes = classes;
        this.title = title;
        this.customProperties = customProperties;
    }


    @JsonProperty("class")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Nonnull
    public final List<String> getClasses() {
        return classes;
    }


    @Nullable
    public final String getTitle() {
        return title;
    }


    @JsonAnyGetter
    public Map<String, Object> getCustomProperties() {
        return customProperties;
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        return obj != null
                && obj.getClass() == this.getClass()
                && equals((Element) obj);
    }


    private boolean equals(Element other) {
        return Objects.equals(classes, other.classes)
                && Objects.equals(title, other.title);
    }


    @Override
    public int hashCode() {
        return Objects.hash(classes, title);
    }
}
