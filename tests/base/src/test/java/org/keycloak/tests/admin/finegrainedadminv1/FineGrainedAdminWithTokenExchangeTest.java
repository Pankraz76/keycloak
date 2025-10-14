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
package org.keycloak.tests.admin.finegrainedadminv1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.common.Profile;
import org.keycloak.models.Constants;
import org.keycloak.testframework.annotations.KeycloakIntegrationTest;
import org.keycloak.testframework.server.KeycloakServerConfig;
import org.keycloak.testframework.server.KeycloakServerConfigBuilder;

@KeycloakIntegrationTest(config = FineGrainedAdminWithTokenExchangeTest.FineGrainedWithTokenExchangeServerConf.class)
public class FineGrainedAdminWithTokenExchangeTest extends AbstractFineGrainedAdminTest {

    /**
     * KEYCLOAK-7406
     */
    @Test
    public void testWithTokenExchange() {
        String exchanged = checkTokenExchange(true);
        try (Keycloak client = adminClientFactory.create()
                .realm("master").authorization(exchanged).clientId(Constants.ADMIN_CLI_CLIENT_ID).build()) {
            Assertions.assertNotNull(client.realm("master").roles().get("offline_access"));
        }
    }

    public static class FineGrainedWithTokenExchangeServerConf implements KeycloakServerConfig {

        @Override
        public KeycloakServerConfigBuilder configure(KeycloakServerConfigBuilder config) {
            config.features(Profile.Feature.TOKEN_EXCHANGE, Profile.Feature.ADMIN_FINE_GRAINED_AUTHZ);

            return config;
        }
    }
}
