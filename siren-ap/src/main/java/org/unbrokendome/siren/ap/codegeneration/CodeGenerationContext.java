package org.unbrokendome.siren.ap.codegeneration;

import org.unbrokendome.siren.ap.codegeneration.feature.Feature;

import javax.annotation.Nonnull;
import java.util.Set;


public interface CodeGenerationContext {

    @Nonnull
    Set<Feature> getEnabledFeatures();

    default boolean isEnabled(Feature feature) {
        return getEnabledFeatures().contains(feature);
    }

    default boolean isDisabled(Feature feature) {
        return !isEnabled(feature);
    }
}
