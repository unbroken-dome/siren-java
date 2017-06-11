package org.unbrokendome.siren.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;


public abstract class FullEntityBuilder<T extends FullEntity, B extends FullEntityBuilder<T, B>>
        extends EntityBuilder<T, B> {

    private Object properties;
    private Map<String, Object> propertiesMap;
    private List<Element> entities;
    private List<Link> links;
    private List<Action> actions;


    @Nonnull
    public final B addProperty(String name, @Nullable Object value) {
        if (properties != null) {
            throw new IllegalStateException("Cannot use map-style properties when a properties object has been set");
        }
        if (propertiesMap == null) {
            propertiesMap = new LinkedHashMap<>();
        }

        propertiesMap.put(name, value);
        return self();
    }


    @Nonnull
    public final B addProperties(Map<String, Object> properties) {
        if (this.properties != null) {
            throw new IllegalStateException("Cannot use map-style properties when a properties object has been set");
        }
        if (this.propertiesMap == null) {
            this.propertiesMap = new LinkedHashMap<>();
        }

        this.propertiesMap.putAll(properties);
        return self();
    }


    @Nonnull
    public final B setProperties(@Nullable Object properties) {
        if (propertiesMap != null) {
            throw new IllegalStateException("Cannot use object-style properties when a property map entry has been added");
        }
        this.properties = properties;
        return self();
    }


    @Nullable
    protected final Object getProperties() {
        if (properties != null) {
            return properties;
        } else if (propertiesMap != null) {
            return Collections.unmodifiableMap(propertiesMap);
        } else {
            return null;
        }
    }


    @Nonnull
    public final B addEmbeddedEntity(EmbeddedEntity entity) {
        if (entities == null) {
            entities = new ArrayList<>();
        }
        entities.add(entity);
        return self();
    }


    @Nonnull
    public final B addEmbeddedEntity(String rel, Consumer<EmbeddedEntityBuilder> spec) {
        return addEmbeddedEntity(
                new EmbeddedEntityBuilder(rel).with(spec).build());
    }


    @Nonnull
    public final B addEmbeddedEntity(String[] rels, Consumer<EmbeddedEntityBuilder> spec) {
        return addEmbeddedEntity(
                new EmbeddedEntityBuilder(rels).with(spec).build());
    }


    @Nonnull
    public final B addEmbeddedLink(Link embeddedLink) {
        if (entities == null) {
            entities = new ArrayList<>();
        }
        entities.add(embeddedLink);
        return self();
    }


    @Nonnull
    public final B addEmbeddedLink(String rel, Consumer<LinkBuilder> spec) {
        return addEmbeddedLink(
                new LinkBuilder(rel).with(spec).build());
    }


    @Nonnull
    public final B addEmbeddedLink(String[] rels, Consumer<LinkBuilder> spec) {
        return addEmbeddedLink(
                new LinkBuilder(rels).with(spec).build());
    }


    @Nonnull
    protected final List<Element> getEntities() {
        return entities != null ? Collections.unmodifiableList(entities) : Collections.emptyList();
    }


    @Nonnull
    public final B addLink(Link link) {
        if (links == null) {
            links = new ArrayList<>();
        }
        links.add(link);
        return self();
    }


    @Nonnull
    public final B addLink(String rel, Consumer<LinkBuilder> spec) {
        return addLink(
                new LinkBuilder(rel).with(spec).build());
    }


    @Nonnull
    public final B addLink(String[] rels, Consumer<LinkBuilder> spec) {
        return addLink(
                new LinkBuilder(rels).with(spec).build());
    }


    @Nonnull
    protected final List<Link> getLinks() {
        return links != null ? Collections.unmodifiableList(links) : Collections.emptyList();
    }


    @Nonnull
    public final B addAction(Action action) {
        if (actions == null) {
            actions = new ArrayList<>();
        }
        actions.add(action);
        return self();
    }


    @Nonnull
    public final B addAction(String name, Consumer<ActionBuilder> spec) {
        return addAction(
                new ActionBuilder(name).with(spec).build());
    }


    @Nonnull
    protected final List<Action> getActions() {
        return actions != null ? Collections.unmodifiableList(actions) : Collections.emptyList();
    }
}
