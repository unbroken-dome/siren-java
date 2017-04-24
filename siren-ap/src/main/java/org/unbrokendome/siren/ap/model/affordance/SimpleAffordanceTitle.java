package org.unbrokendome.siren.ap.model.affordance;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class SimpleAffordanceTitle implements AffordanceTitle {

    private final String value;


    public SimpleAffordanceTitle(String value) {
        this.value = value;
    }


    @Override
    @Nonnull
    public String getValue() {
        return value;
    }


    @Override
    public boolean isResolvable() {
        return false;
    }


    @Override
    @Nullable
    public String getResolvableTitleKey() {
        return null;
    }


    @Override
    public String toString() {
        return value;
    }
}
