package org.unbrokendome.siren.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * Describes a reference to a resource. Abstract base class for {@link Link} and {@link Action}.
 */
public abstract class Reference extends Element {

    private final String href;
    private final String type;


    protected Reference(String href,
                        @Nullable String type,
                        List<String> classes,
                        @Nullable String title,
                        Map<String, Object> customProperties) {
        super(classes, title, customProperties);
        this.href = href;
        this.type = type;
    }


    @Nonnull
    public final String getHref() {
        return href;
    }


    @Nullable
    public final String getType() {
        return type;
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        return obj != null
                && this.getClass() == obj.getClass()
                && equals((Reference) obj);
    }


    private boolean equals(Reference other) {
        return super.equals(other)
                && Objects.equals(href, other.href)
                && Objects.equals(type, other.type);
    }


    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), href, type);
    }

    @Override
    public String toString() {
        return "Reference{" +
                "href='" + href + '\'' +
                ", type='" + type + '\'' +
                ", classes=" + getClasses() +
                ", title='" + getTitle() + '\'' +
                ", customProperties=" + getCustomProperties() +
                '}';
    }
}
