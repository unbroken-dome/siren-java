package org.unbrokendome.siren.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@JsonPropertyOrder({ "rel", "class", "title", "href", "type" })
public final class Link extends Reference {

    private final List<String> rel;


    public Link(List<String> rel,
                String href,
                @Nullable String type,
                List<String> classes,
                @Nullable String title,
                Map<String, Object> customProperties) {
        super(href, type, classes, title, customProperties);
        this.rel = rel;
    }


    @Nonnull
    public final List<String> getRel() {
        return rel;
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof Link
                && equals((Link) obj);
    }


    private boolean equals(Link other) {
        return super.equals(other)
                && Objects.equals(rel, other.rel);
    }


    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), rel);
    }

    @Override
    public String toString() {
        return "Link{" +
                "rel=" + rel +
                ", href='" + getHref() + '\'' +
                ", type='" + getType() + '\'' +
                ", classes=" + getClasses() +
                ", title='" + getTitle() + '\'' +
                ", customProperties=" + getCustomProperties() +
                '}';
    }
}
