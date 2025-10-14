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
package org.keycloak.config;

import org.keycloak.common.Profile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FeatureOptions {

    public static final Option<List<String>> FEATURES = OptionBuilder.listOptionBuilder("features", String.class)
            .category(OptionCategory.FEATURE)
            .description("Enables a set of one or more features.")
            .defaultValue(Optional.empty())
            .expectedValues(getFeatureValues(true))
            .buildTime(true)
            .build();

    public static final Option<List<String>> FEATURES_DISABLED = OptionBuilder.listOptionBuilder("features-disabled", String.class)
            .category(OptionCategory.FEATURE)
            .description("Disables a set of one or more features.")
            .expectedValues(getFeatureValues(false))
            .buildTime(true)
            .build();

    public static List<String> getFeatureValues(boolean toEnable) {
        List<String> features = new ArrayList<>();

        if (toEnable) {
            Profile.getAllUnversionedFeatureNames().forEach(f -> {
                features.add(f + "[:" + Profile.getFeatureVersions(f).stream().sorted().map(v -> "v" + v.getVersion())
                        .collect(Collectors.joining(",")) + "]");
            });
        } else {
            features.addAll(Profile.getDisableableUnversionedFeatureNames());
        }

        features.add(Profile.Feature.Type.PREVIEW.name().toLowerCase());

        Collections.sort(features);

        return features;
    }
}
