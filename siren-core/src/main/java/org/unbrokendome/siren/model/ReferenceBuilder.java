package org.unbrokendome.siren.model;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public abstract class ReferenceBuilder<T extends Reference, B extends ReferenceBuilder<T, B>>
        extends ElementBuilder<T, B> {

    private String href;
    private String type;


    @Nullable
    public final String getHref() {
        return href;
    }


    @Nonnull
    public final B setHref(String href) {
        this.href = href;
        return self();
    }


    @Nullable
    public final String getType() {
        return type;
    }


    @Nonnull
    public final B setType(String type) {
        this.type = type;
        return self();
    }
}
