package org.unbrokendome.siren.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public abstract class FullEntity extends Entity {

    private final Object properties;
    private final List<Element> entities;
    private final List<Link> links;
    private final List<Action> actions;


    protected FullEntity(@Nullable Object properties,
                         List<Element> entities,
                         List<Link> links,
                         List<Action> actions,
                         List<String> classes,
                         @Nullable String title,
                         Map<String, Object> customProperties) {
        super(classes, title, customProperties);
        this.properties = properties;
        this.entities = entities;
        this.links = links;
        this.actions = actions;
    }


    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public final Object getProperties() {
        return properties;
    }


    @Nonnull
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public final List<Element> getEntities() {
        return entities;
    }


    @Nonnull
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public final List<Link> getLinks() {
        return links;
    }


    @Nonnull
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public final List<Action> getActions() {
        return actions;
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        return obj != null
                && obj.getClass() == this.getClass()
                && equals((FullEntity) obj);
    }


    private boolean equals(FullEntity other) {
        return super.equals(other)
                && Objects.equals(properties, other.properties)
                && Objects.equals(entities, other.entities)
                && Objects.equals(links, other.links)
                && Objects.equals(actions, other.actions);
    }


    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(),
                properties, entities, links, actions);
    }
}
