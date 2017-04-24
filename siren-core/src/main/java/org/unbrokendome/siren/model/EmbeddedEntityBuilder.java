package org.unbrokendome.siren.model;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public final class EmbeddedEntityBuilder extends FullEntityBuilder<EmbeddedEntity, EmbeddedEntityBuilder> {

    private final List<String> rel;


    protected EmbeddedEntityBuilder(String rel) {
        this.rel = Collections.singletonList(rel);
    }


    protected EmbeddedEntityBuilder(String... rels) {
        this.rel = Arrays.asList(rels);
    }


    @Nonnull
    @Override
    public EmbeddedEntity build() {
        return new EmbeddedEntity(rel,
                getProperties(),
                getEntities(),
                getLinks(),
                getActions(),
                getClassNames(),
                getTitle(),
                getCustomProperties());
    }
}
