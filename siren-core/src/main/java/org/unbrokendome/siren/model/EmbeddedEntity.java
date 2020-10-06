package org.unbrokendome.siren.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public final class EmbeddedEntity extends FullEntity {

    private final List<String> rel;


    protected EmbeddedEntity(List<String> rel,
                             @Nullable Object properties,
                             List<Element> entities,
                             List<Link> links,
                             List<Action> actions,
                             List<String> classes,
                             @Nullable String title,
                             Map<String, Object> customProperties) {
        super(properties, entities, links, actions, classes, title, customProperties);
        this.rel = rel;
    }


    @Nonnull
    public final List<String> getRel() {
        return rel;
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        return obj != null
                && obj instanceof EmbeddedEntity
                && equals((EmbeddedEntity) obj);
    }


    private boolean equals(EmbeddedEntity other) {
        return super.equals(other)
                && Objects.equals(rel, other.rel);
    }


    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), rel);
    }

    @Override
    public String toString() {
        return "EmbeddedEntity{" +
                "rel=" + rel +
                ", properties=" + getProperties() +
                ", entities=" + getEntities() +
                ", links=" + getLinks() +
                ", actions=" + getActions() +
                ", classes=" + getClasses() +
                ", title='" + getTitle() + '\'' +
                ", customProperties=" + getCustomProperties() +
                '}';
    }
}
