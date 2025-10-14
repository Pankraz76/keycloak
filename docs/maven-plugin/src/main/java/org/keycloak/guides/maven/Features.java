/*
 * Copyright 2025 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.keycloak.guides.maven;

import org.keycloak.common.Profile;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Features {

    private final List<Feature> features;

    public Features() {
        this.features = Arrays.stream(Profile.Feature.values())
                .filter(f -> !f.getType().equals(Profile.Feature.Type.EXPERIMENTAL))
                .map(Feature::new)
                .sorted(Comparator.comparing(Feature::getName))
                .collect(Collectors.toList());
    }

    public List<Feature> getSupported() {
        return features.stream().filter(f -> f.getType().equals(Profile.Feature.Type.DEFAULT)).collect(Collectors.toList());
    }

    public List<Feature> getSupportedDisabledByDefault() {
        return features.stream().filter(f -> f.getType().equals(Profile.Feature.Type.DISABLED_BY_DEFAULT)).collect(Collectors.toList());
    }

    public List<Feature> getDeprecated() {
        return features.stream().filter(f -> f.getType().equals(Profile.Feature.Type.DEPRECATED)).collect(Collectors.toList());
    }

    public List<Feature> getPreview() {
        return features.stream().filter(f -> f.getType().equals(Profile.Feature.Type.PREVIEW)).collect(Collectors.toList());
    }

    public List<Feature> getUpdatePolicyShutdown() {
        return features.stream().filter(f -> f.profileFeature.getUpdatePolicy() == Profile.FeatureUpdatePolicy.SHUTDOWN).collect(Collectors.toList());
    }

    public List<Feature> getUpdatePolicyRollingNoUpgrade() {
        return features.stream().filter(f -> f.profileFeature.getUpdatePolicy() == Profile.FeatureUpdatePolicy.ROLLING_NO_UPGRADE).collect(Collectors.toList());
    }

    public static class Feature {

        private final Profile.Feature profileFeature;

        public Feature(Profile.Feature profileFeature) {
            this.profileFeature = profileFeature;
        }

        public String getName() {
            return profileFeature.getKey();
        }

        public String getDescription() {
            return profileFeature.getLabel();
        }

        public String getVersionedKey() {
            return profileFeature.getVersionedKey();
        }

        public String getUpdatePolicy() {
            return profileFeature.getUpdatePolicy().toString();
        }

        private Profile.Feature.Type getType() {
            return profileFeature.getType();
        }
    }
}
