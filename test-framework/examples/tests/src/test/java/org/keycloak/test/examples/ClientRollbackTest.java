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
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.testframework.annotations.InjectClient;
import org.keycloak.testframework.annotations.KeycloakIntegrationTest;
import org.keycloak.testframework.realm.ClientConfig;
import org.keycloak.testframework.realm.ClientConfigBuilder;
import org.keycloak.testframework.realm.ManagedClient;


@KeycloakIntegrationTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class ClientRollbackTest {

    @InjectClient(config = ClientWithSingleAttribute.class)
    ManagedClient client;

    @Test
    public void test1UpdateWithRollback() {
        client.updateWithCleanup(u -> u.attribute("one", "two").attribute("two", "two"));
        client.updateWithCleanup(u -> u.adminUrl("http://something"));
        client.updateWithCleanup(u -> u.redirectUris("http://something"));
        client.updateWithCleanup(u -> u.attribute("three", "three"));
    }

    @Test
    public void test2CheckRollback() {
        ClientRepresentation current = client.admin().toRepresentation();

        Assertions.assertEquals("one", current.getAttributes().get("one"));
        Assertions.assertFalse(current.getAttributes().containsKey("two"));
        Assertions.assertFalse(current.getAttributes().containsKey("three"));
        Assertions.assertNull(current.getAdminUrl());
        Assertions.assertTrue(current.getRedirectUris().isEmpty());
    }

    public static class ClientWithSingleAttribute implements ClientConfig {

        @Override
        public ClientConfigBuilder configure(ClientConfigBuilder client) {
            return client.attribute("one", "one");
        }

    }
}
