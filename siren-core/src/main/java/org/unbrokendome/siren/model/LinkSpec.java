package org.unbrokendome.siren.model;

import java.util.function.Consumer;


public interface LinkSpec extends Consumer<LinkBuilder> {

    default String getHref() {
        return new LinkBuilder("dummyRel").with(this).getHref();
    }
}
