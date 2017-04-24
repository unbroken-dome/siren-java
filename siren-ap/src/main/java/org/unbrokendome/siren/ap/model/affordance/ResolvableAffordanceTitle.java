package org.unbrokendome.siren.ap.model.affordance;


import javax.annotation.Nonnull;


public final class ResolvableAffordanceTitle implements AffordanceTitle {

    private final String titleKey;


    public ResolvableAffordanceTitle(String titleKey) {
        this.titleKey = titleKey;
    }


    @Override
    @Nonnull
    public String getValue() {
        return "{" + titleKey + "}";
    }


    @Override
    public boolean isResolvable() {
        return true;
    }


    @Override
    @Nonnull
    public String getResolvableTitleKey() {
        return titleKey;
    }
}
