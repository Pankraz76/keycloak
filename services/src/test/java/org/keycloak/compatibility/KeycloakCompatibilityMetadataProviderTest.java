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
package org.keycloak.compatibility;

import static org.keycloak.compatibility.KeycloakCompatibilityMetadataProvider.VERSION_KEY;

import java.util.Map;

import org.junit.Test;
import org.keycloak.common.Profile;
import org.keycloak.common.profile.ProfileConfigResolver;

public class KeycloakCompatibilityMetadataProviderTest extends AbstractCompatibilityMetadataProviderTest {

    @Test
    public void testMicroVersionUpgradeWorksWithRollingUpdateV2() {
        // Enable V2 feature
        Profile.configure(new ProfileConfigResolver() {
            @Override
            public Profile.ProfileName getProfileName() {
                return null;
            }

            @Override
            public FeatureConfig getFeatureConfig(String feature) {
                return Profile.Feature.ROLLING_UPDATES_V2.getVersionedKey().equals(feature) ? FeatureConfig.ENABLED : FeatureConfig.UNCONFIGURED;
            }
        });

        // Make compatibility provider return hardcoded version as we are not able to test this in integration tests with micro versions equal to 0
        KeycloakCompatibilityMetadataProvider compatibilityProvider = new KeycloakCompatibilityMetadataProvider("999.999.999-Final");

        // Test compatible
        assertCompatibility(CompatibilityResult.ExitCode.ROLLING, compatibilityProvider.isCompatible(Map.of(VERSION_KEY, "999.999.999-Final")));
        assertCompatibility(CompatibilityResult.ExitCode.ROLLING, compatibilityProvider.isCompatible(Map.of(VERSION_KEY, "999.999.998-Final")));
        assertCompatibility(CompatibilityResult.ExitCode.ROLLING, compatibilityProvider.isCompatible(Map.of(VERSION_KEY, "999.999.999-Final1")));
        assertCompatibility(CompatibilityResult.ExitCode.ROLLING, compatibilityProvider.isCompatible(Map.of(VERSION_KEY, "999.999.1-Final")));

        // Test incompatible
        assertCompatibility(CompatibilityResult.ExitCode.RECREATE, compatibilityProvider.isCompatible(Map.of(VERSION_KEY, "999.999.1000-Final")));
        assertCompatibility(CompatibilityResult.ExitCode.RECREATE, compatibilityProvider.isCompatible(Map.of(VERSION_KEY, "999.998.999-Final")));
        assertCompatibility(CompatibilityResult.ExitCode.RECREATE, compatibilityProvider.isCompatible(Map.of(VERSION_KEY, "998.999.999-Final")));
        assertCompatibility(CompatibilityResult.ExitCode.RECREATE, compatibilityProvider.isCompatible(Map.of(VERSION_KEY, "999.998.998-Final")));
        assertCompatibility(CompatibilityResult.ExitCode.RECREATE, compatibilityProvider.isCompatible(Map.of(VERSION_KEY, "998.999.998-Final")));

        Profile.reset();
    }

    @Test
    public void testRollingUpgradesV1() {
        Profile.configure();

        // Make compatibility provider return hardcoded version so we can subtract and add to any of major.minor.micro number
        KeycloakCompatibilityMetadataProvider compatibilityProvider = new KeycloakCompatibilityMetadataProvider("999.999.999-Final") ;

        // Test compatible
        assertCompatibility(CompatibilityResult.ExitCode.ROLLING, compatibilityProvider.isCompatible(Map.of(VERSION_KEY, "999.999.999-Final")));

        // Test incompatible
        assertCompatibility(CompatibilityResult.ExitCode.RECREATE, compatibilityProvider.isCompatible(Map.of(VERSION_KEY, "999.999.998-Final")));
        assertCompatibility(CompatibilityResult.ExitCode.RECREATE, compatibilityProvider.isCompatible(Map.of(VERSION_KEY, "999.999.999-Final1")));
        assertCompatibility(CompatibilityResult.ExitCode.RECREATE, compatibilityProvider.isCompatible(Map.of(VERSION_KEY, "999.999.997-Final")));
        assertCompatibility(CompatibilityResult.ExitCode.RECREATE, compatibilityProvider.isCompatible(Map.of(VERSION_KEY, "999.999.1000-Final")));
        assertCompatibility(CompatibilityResult.ExitCode.RECREATE, compatibilityProvider.isCompatible(Map.of(VERSION_KEY, "999.998.999-Final")));
        assertCompatibility(CompatibilityResult.ExitCode.RECREATE, compatibilityProvider.isCompatible(Map.of(VERSION_KEY, "998.999.999-Final")));
        assertCompatibility(CompatibilityResult.ExitCode.RECREATE, compatibilityProvider.isCompatible(Map.of(VERSION_KEY, "999.998.998-Final")));
        assertCompatibility(CompatibilityResult.ExitCode.RECREATE, compatibilityProvider.isCompatible(Map.of(VERSION_KEY, "998.999.998-Final")));
    }

    @Test
    public void testRollingUpgradeRefusedWithOtherMetadataNotEquals() {
        // Enable V2 feature
        Profile.configure(new ProfileConfigResolver() {
            @Override
            public Profile.ProfileName getProfileName() {
                return null;
            }

            @Override
            public FeatureConfig getFeatureConfig(String feature) {
                return Profile.Feature.ROLLING_UPDATES_V2.getVersionedKey().equals(feature) ? FeatureConfig.ENABLED : FeatureConfig.UNCONFIGURED;
            }
        });

        // Make compatibility provider return hardcoded version as we are not able to test this in integration tests with micro versions equal to 0
        KeycloakCompatibilityMetadataProvider compatibilityProvider = new KeycloakCompatibilityMetadataProvider("999.999.999-Final") {
            @Override
            public Map<String, String> metadata() {
                return Map.of(VERSION_KEY, "999.999.999-Final",
                        "key2", "value2");
            }
        };

        // Test compatible
        assertCompatibility(CompatibilityResult.ExitCode.ROLLING, compatibilityProvider.isCompatible(Map.of(VERSION_KEY, "999.999.998-Final", "key2", "value2")));

        // Test incompatible
        assertCompatibility(CompatibilityResult.ExitCode.RECREATE, compatibilityProvider.isCompatible(Map.of(VERSION_KEY, "999.999.998-Final", "key2", "different-value")));
        assertCompatibility(CompatibilityResult.ExitCode.RECREATE, compatibilityProvider.isCompatible(Map.of(VERSION_KEY, "999.999.998-Final")));
    }
}
