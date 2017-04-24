package org.unbrokendome.siren.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public final class ActionFieldBuilder extends ElementBuilder<ActionField, ActionFieldBuilder> {

    private final String name;
    private ActionField.Type type = ActionField.Type.NONE;
    private Object value;


    public ActionFieldBuilder(String name) {
        this.name = name;
    }


    public final ActionField.Type getType() {
        return type;
    }


    @Nonnull
    public final ActionFieldBuilder setType(ActionField.Type type) {
        this.type = type;
        return this;
    }


    @Nullable
    public final Object getValue() {
        return value;
    }


    @Nonnull
    public final ActionFieldBuilder setValue(@Nullable Object value) {
        this.value = value;
        return this;
    }


    @Nonnull
    @Override
    public ActionField build() {
        return new ActionField(name, type, value, getClassNames(), getTitle(), getCustomProperties());
    }
}
