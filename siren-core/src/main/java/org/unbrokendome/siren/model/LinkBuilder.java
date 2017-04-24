package org.unbrokendome.siren.model;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public final class LinkBuilder extends ReferenceBuilder<Link, LinkBuilder> {

    private final List<String> rel;


    public LinkBuilder(String rel) {
        this.rel = Collections.singletonList(rel);
    }


    public LinkBuilder(String... rels) {
        this.rel = Collections.unmodifiableList(Arrays.asList(rels));
    }


    @Nonnull
    public List<String> getRels() {
        return rel;
    }


    @Nonnull
    @Override
    public Link build() {

        String href = getHref();
        if (href == null) {
            throw new IllegalStateException("href must be set on Link");
        }

        return new Link(rel, href, getType(), getClassNames(), getTitle(), getCustomProperties());
    }
}
