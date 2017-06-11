package org.unbrokendome.siren.model;

import java.util.function.Consumer;


public interface ActionSpec extends Consumer<ActionBuilder> {

    default String getHref() {
        return new ActionBuilder("dummyAction").with(this).getHref();
    }
}
