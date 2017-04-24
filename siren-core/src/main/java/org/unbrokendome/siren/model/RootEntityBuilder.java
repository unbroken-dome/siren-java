package org.unbrokendome.siren.model;

import javax.annotation.Nonnull;


public final class RootEntityBuilder extends FullEntityBuilder<RootEntity, RootEntityBuilder> {

    @Nonnull
    @Override
    public RootEntity build() {
        return new RootEntity(
                getProperties(),
                getEntities(),
                getLinks(),
                getActions(),
                getClassNames(),
                getTitle(),
                getCustomProperties());
    }
}
