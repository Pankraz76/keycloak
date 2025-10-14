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
package org.keycloak.common.profile;

import org.keycloak.common.Profile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CommaSeparatedListProfileConfigResolver implements ProfileConfigResolver {

    private Set<String> enabledFeatures;
    private Set<String> disabledFeatures;

    public CommaSeparatedListProfileConfigResolver(String enabledFeatures, String disabledFeatures) {
        if (enabledFeatures != null) {
            this.enabledFeatures = new HashSet<>(Arrays.asList(enabledFeatures.split(",")));
        }
        if (disabledFeatures != null) {
            this.disabledFeatures = new HashSet<>(Arrays.asList(disabledFeatures.split(",")));
        }
    }

    @Override
    public Profile.ProfileName getProfileName() {
        if (enabledFeatures != null && enabledFeatures.contains(Profile.ProfileName.PREVIEW.name().toLowerCase())) {
            return Profile.ProfileName.PREVIEW;
        }
        return null;
    }

    @Override
    public FeatureConfig getFeatureConfig(String feature) {
        if (enabledFeatures != null && enabledFeatures.contains(feature)) {
            if (disabledFeatures != null && disabledFeatures.contains(feature)) {
                throw new ProfileException(feature + " is in both the enabled and disabled feature lists.");
            }
            return FeatureConfig.ENABLED;
        }
        if (disabledFeatures != null && disabledFeatures.contains(feature)) {
            return FeatureConfig.DISABLED;
        }
        return FeatureConfig.UNCONFIGURED;
    }
}
