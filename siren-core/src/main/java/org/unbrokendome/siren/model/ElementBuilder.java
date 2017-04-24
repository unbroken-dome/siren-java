package org.unbrokendome.siren.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Consumer;


public abstract class ElementBuilder<T extends Element, B extends ElementBuilder<T, B>> {

    private String title;
    private List<String> classNames = Collections.emptyList();
    private Map<String, Object> customProperties = new LinkedHashMap<>();


    @Nullable
    public final String getTitle() {
        return title;
    }


    @Nonnull
    public final B setTitle(String title) {
        this.title = title;
        return self();
    }


    @Nonnull
    public final B setClassName(String className) {
        classNames = Collections.singletonList(className);
        return self();
    }


    @Nonnull
    public final B setClassNames(String... classNames) {
        this.classNames = Arrays.asList(classNames);
        return self();
    }


    public final B setClassNames(Collection<String> classNames) {
        this.classNames = new ArrayList<>(classNames);
        return self();
    }


    @Nonnull
    public final List<String> getClassNames() {
        return classNames;
    }


    public Map<String, Object> getCustomProperties() {
        return Collections.unmodifiableMap(customProperties);
    }


    public final B setCustomProperty(String name, Object value) {
        customProperties.put(name, value);
        return self();
    }


    @Nonnull
    public final B with(Consumer<B> spec) {
        spec.accept(self());
        return self();
    }


    @Nonnull
    @SuppressWarnings("unchecked")
    protected final B self() {
        return (B) this;
    }


    @Nonnull
    public abstract T build();
}
