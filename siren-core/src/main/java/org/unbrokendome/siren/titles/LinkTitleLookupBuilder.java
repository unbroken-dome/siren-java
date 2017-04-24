package org.unbrokendome.siren.titles;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;


public class LinkTitleLookupBuilder {

    private final Map<String, Supplier<String>> titleSuppliers = new HashMap<>();
    private Supplier<String> defaultTitleSupplier;


    public LinkTitleLookupBuilder addTitle(String rel, String title) {
        return addTitleSupplier(rel, () -> title);
    }


    public LinkTitleLookupBuilder addTitleSupplier(String rel, Supplier<String> titleSupplier) {
        titleSuppliers.put(rel, titleSupplier);
        return this;
    }


    public LinkTitleLookupBuilder setDefaultTitle(String defaultTitle) {
        return setDefaultTitleSupplier(() -> defaultTitle);
    }


    public LinkTitleLookupBuilder setDefaultTitleSupplier(Supplier<String> defaultTitleSupplier) {
        this.defaultTitleSupplier = defaultTitleSupplier;
        return this;
    }


    public LinkTitleLookup build() {
        return new DefaultLinkTitleLookup(titleSuppliers, defaultTitleSupplier);
    }
}
