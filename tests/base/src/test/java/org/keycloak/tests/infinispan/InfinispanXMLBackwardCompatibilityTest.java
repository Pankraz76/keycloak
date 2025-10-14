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
package org.keycloak.tests.infinispan;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.testframework.annotations.InjectRealm;
import org.keycloak.testframework.annotations.KeycloakIntegrationTest;
import org.keycloak.testframework.realm.ManagedRealm;
import org.keycloak.testframework.server.KeycloakServerConfig;
import org.keycloak.testframework.server.KeycloakServerConfigBuilder;

@KeycloakIntegrationTest(config = InfinispanXMLBackwardCompatibilityTest.ServerConfigWithCustomInfinispanXML.class)
public class InfinispanXMLBackwardCompatibilityTest {

    private static final String CONFIG_FILE = "/embedded-infinispan-config/infinispan-xml-kc26.xml";

    @InjectRealm
    ManagedRealm realm;

    @Test
    void testKeycloakStartedSuccessfullyWithOlderInfinispanXML() {
        RealmRepresentation representation = realm.admin().toRepresentation();
        Assertions.assertNotNull(representation);
    }


    public static class ServerConfigWithCustomInfinispanXML implements KeycloakServerConfig {

        @Override
        public KeycloakServerConfigBuilder configure(KeycloakServerConfigBuilder config) {
            return config.cacheConfigFile(CONFIG_FILE);
        }
    }
}
