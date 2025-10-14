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
import org.keycloak.common.Profile.Feature;

import java.util.Properties;
import java.util.function.UnaryOperator;

public class PropertiesProfileConfigResolver implements ProfileConfigResolver {

    private UnaryOperator<String> getter;

    public PropertiesProfileConfigResolver(Properties properties) {
        this(properties::getProperty);
    }

    public PropertiesProfileConfigResolver(UnaryOperator<String> getter) {
        this.getter = getter;
    }

    @Override
    public Profile.ProfileName getProfileName() {
        String profile = getter.apply("keycloak.profile");

        if (profile != null) {
            try {
                return Profile.ProfileName.valueOf(profile.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new ProfileException(String.format("Invalid profile '%s' specified via 'keycloak.profile' property", profile));
            }
        }
        return null;
    }

    @Override
    public FeatureConfig getFeatureConfig(String feature) {
        String key = getPropertyKey(feature);
        String config = getter.apply(key);
        if (config != null) {
            switch (config) {
                case "enabled":
                    return FeatureConfig.ENABLED;
                case "disabled":
                    return FeatureConfig.DISABLED;
                default:
                    throw new ProfileException("Invalid config value '" + config + "' for feature key " + key);
            }
        }
        return FeatureConfig.UNCONFIGURED;
    }

    public static String getPropertyKey(Feature feature) {
        return getPropertyKey(feature.getKey());
    }

    public static String getPropertyKey(String feature) {
        return "keycloak.profile.feature." + feature.replaceAll("[-:]", "_");
    }
}
