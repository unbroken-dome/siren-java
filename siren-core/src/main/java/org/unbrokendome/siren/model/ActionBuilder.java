package org.unbrokendome.siren.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;


public final class ActionBuilder extends ReferenceBuilder<Action, ActionBuilder> {

    private final String name;
    private String method;
    private List<ActionField> fields;


    public ActionBuilder(String name) {
        this.name = name;
    }


    @Nullable
    public final String getMethod() {
        return method;
    }


    @Nonnull
    public final ActionBuilder setMethod(String method) {
        this.method = method;
        return this;
    }


    @Nonnull
    public final ActionBuilder addField(ActionField field) {
        if (fields == null) {
            fields = new ArrayList<>();
        }
        fields.add(field);
        return this;
    }


    @Nonnull
    protected final List<ActionField> getFields() {
        return fields != null ? Collections.unmodifiableList(fields) : Collections.emptyList();
    }


    @Nonnull
    public final ActionBuilder addField(String name, Consumer<ActionFieldBuilder> spec) {
        return addField(
                new ActionFieldBuilder(name).with(spec).build());
    }


    @Override
    @Nonnull
    public Action build() {

        String href = getHref();
        if (href == null) {
            throw new IllegalStateException("href must be set on Link");
        }

        return new Action(name, method, getHref(), getType(), getFields(), getClassNames(), getTitle(),
                getCustomProperties());
    }
}
