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
package org.keycloak.test.examples;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.common.Profile;
import org.keycloak.representations.info.FeatureRepresentation;
import org.keycloak.testframework.annotations.InjectAdminClient;
import org.keycloak.testframework.annotations.KeycloakIntegrationTest;
import org.keycloak.testframework.server.KeycloakServerConfigBuilder;
import org.keycloak.testframework.server.KeycloakServerConfig;

import java.util.Optional;

@KeycloakIntegrationTest(config = CustomConfigTest.CustomServerConfig.class)
public class CustomConfigTest {

    @InjectAdminClient
    Keycloak adminClient;

    @Test
    public void testPasskeyFeatureEnabled() {
        Optional<FeatureRepresentation> passKeysFeature = adminClient.serverInfo().getInfo().getFeatures().stream().filter(f -> f.getName().equals(Profile.Feature.PASSKEYS.name())).findFirst();
        Assertions.assertTrue(passKeysFeature.isPresent());
        Assertions.assertTrue(passKeysFeature.get().isEnabled());
    }

    public static class CustomServerConfig implements KeycloakServerConfig {

        @Override
        public KeycloakServerConfigBuilder configure(KeycloakServerConfigBuilder config) {
            return config.features(Profile.Feature.PASSKEYS);
        }

    }

}
