package org.unbrokendome.siren.ap.model.affordance;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public interface AffordanceTitle {

    @Nonnull
    String getValue();


    boolean isResolvable();


    @Nullable
    String getResolvableTitleKey();


    @Nonnull
    static AffordanceTitle create(String value) {
        if (value.startsWith("{") && value.endsWith("}")) {
            String titleKey = value.substring(1, value.length() - 1);
            return new ResolvableAffordanceTitle(titleKey);
        } else {
            return new SimpleAffordanceTitle(value);
        }
    }
}
