package org.unbrokendome.siren.titles;

import javax.annotation.Nullable;


public interface LinkTitleLookup {

    @Nullable
    String get(Iterable<String> rels);


    static LinkTitleLookupBuilder builder() {
        return new LinkTitleLookupBuilder();
    }
}
