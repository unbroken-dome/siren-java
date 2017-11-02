package org.unbrokendome.siren.ap.codegeneration.feature;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;


public interface FeatureVoteResult {

    @Nonnull
    Set<Feature> mustEnableFeatures();


    @Nonnull
    Set<Feature> mustDisableFeatures();


    @Nonnull
    static Builder builder() {
        return new Builder();
    }


    class Builder {

        private final EnumSet<Feature> mustEnableFeatures = EnumSet.noneOf(Feature.class);
        private final EnumSet<Feature> mustDisableFeatures = EnumSet.noneOf(Feature.class);

        private Builder() { }

        @Nonnull
        public Builder mustEnable(Feature... features) {
            mustEnableFeatures.addAll(Arrays.asList(features));
            return this;
        }

        @Nonnull
        public Builder mustDisable(Feature... features) {
            mustDisableFeatures.addAll(Arrays.asList(features));
            return this;
        }

        public FeatureVoteResult build() {
            return new FeatureVoteResult() {
                @Nonnull
                @Override
                public Set<Feature> mustEnableFeatures() {
                    return mustEnableFeatures;
                }

                @Nonnull
                @Override
                public Set<Feature> mustDisableFeatures() {
                    return mustDisableFeatures;
                }
            };
        }
    }
}
