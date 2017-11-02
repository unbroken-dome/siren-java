package org.unbrokendome.siren.spring.ap.codegeneration.feature;

import com.google.auto.service.AutoService;
import org.unbrokendome.siren.ap.codegeneration.feature.Feature;
import org.unbrokendome.siren.ap.codegeneration.feature.FeatureVoteResult;
import org.unbrokendome.siren.ap.codegeneration.feature.FeatureVoter;
import org.unbrokendome.siren.spring.ap.util.SpringDependencyUtils;


@AutoService(FeatureVoter.class)
public class SpringFeatureVoter implements FeatureVoter {

    @Override
    public FeatureVoteResult vote() {
        FeatureVoteResult.Builder features = FeatureVoteResult.builder();

        if (SpringDependencyUtils.isSpringWebFluxPresent()) {
            features.mustDisable(Feature.IMPLICIT_REQUEST);
        }

        return features.build();
    }
}
