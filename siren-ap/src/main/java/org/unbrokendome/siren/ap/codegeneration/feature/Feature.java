package org.unbrokendome.siren.ap.codegeneration.feature;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public enum Feature {

    /**
     * Will generate methods that take an explicit "request" parameter to derive the contextual URI.
     */
    EXPLICIT_REQUEST,

    /**
     * Will generate methods that use an implicit "current request" to derive the contextual URI.
     */
    IMPLICIT_REQUEST;


    private final boolean enabledByDefault;


    Feature(boolean enabledByDefault) {
        this.enabledByDefault = enabledByDefault;
    }

    Feature() {
        this(true);
    }


    public boolean isEnabledByDefault() {
        return enabledByDefault;
    }


    public static Set<Feature> getEnabledFeatures(Iterable<FeatureVoter> featureVoters) {

        Map<String, FeatureVoteResult> voteResults = new HashMap<>();
        for (FeatureVoter featureVoter : featureVoters) {
            FeatureVoteResult voteResult = featureVoter.vote();
            Set<Feature> conflicts = Sets.intersection(voteResult.mustEnableFeatures(), voteResult.mustDisableFeatures());
            if (!conflicts.isEmpty()) {
                throw new FeatureConflictException("Feature voter " + featureVoter +
                        " reported feature(s) as both mustEnable and mustDisable: " + conflicts);
            }
            voteResults.put(featureVoter.toString(), voteResult);
        }

        List<Map.Entry<String, FeatureVoteResult>> entries = ImmutableList.copyOf(voteResults.entrySet());
        for (List<Map.Entry<String, FeatureVoteResult>> pairs : Lists.cartesianProduct(entries, entries)) {
            Map.Entry<String, FeatureVoteResult> entry1 = pairs.get(0), entry2 = pairs.get(1);
            if (entry1.getKey().equals(entry2.getKey())) {
                continue;
            }

            Set<Feature> conflicts = Sets.intersection(entry1.getValue().mustEnableFeatures(),
                    entry2.getValue().mustDisableFeatures());
            if (!conflicts.isEmpty()) {
                throw new FeatureConflictException("Feature conflict: Feature(s) " + conflicts + " were voted as" +
                        " mustEnable by " + entry1.getKey() + ", but as mustDisable by " + entry2.getKey());
            }
        }

        Set<Feature> enabledFeatures = Arrays.stream(Feature.values())
                .filter(Feature::isEnabledByDefault)
                .collect(Collectors.toCollection(() -> EnumSet.noneOf(Feature.class)));

        for (FeatureVoteResult voteResult : voteResults.values()) {
            enabledFeatures.addAll(voteResult.mustEnableFeatures());
            enabledFeatures.removeAll(voteResult.mustDisableFeatures());
        }

        return Collections.unmodifiableSet(enabledFeatures);
    }
}
