package org.unbrokendome.siren.titles;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Supplier;


public class DefaultLinkTitleLookup implements LinkTitleLookup {

    private final Map<String, Supplier<String>> titleSuppliers;
    private final Supplier<String> defaultTitleSupplier;


    public DefaultLinkTitleLookup(Map<String, Supplier<String>> titleSuppliers,
                                  @Nullable Supplier<String> defaultTitleSupplier) {
        this.titleSuppliers = titleSuppliers;
        this.defaultTitleSupplier = defaultTitleSupplier;
    }


    @Override
    @Nullable
    public String get(Iterable<String> rels) {
        for (String rel : rels) {
            Supplier<String> titleSupplier = titleSuppliers.get(rel);
            if (titleSupplier != null) {
                return titleSupplier.get();
            }
        }
        if (defaultTitleSupplier != null) {
            return defaultTitleSupplier.get();
        }
        return null;
    }
}
