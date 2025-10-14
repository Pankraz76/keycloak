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
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.testframework.annotations.InjectClient;
import org.keycloak.testframework.annotations.InjectRealm;
import org.keycloak.testframework.annotations.KeycloakIntegrationTest;
import org.keycloak.testframework.injection.LifeCycle;
import org.keycloak.testframework.realm.ManagedClient;
import org.keycloak.testframework.realm.ManagedRealm;

import java.util.List;

@KeycloakIntegrationTest
public class ManagedResources2Test {

    @InjectRealm(lifecycle = LifeCycle.CLASS)
    ManagedRealm realm;

    @InjectClient
    ManagedClient client;

    @Test
    public void testCreatedRealm() {
        Assertions.assertEquals("http://localhost:8080/realms/default", realm.getBaseUrl());
        Assertions.assertEquals("default", realm.getName());
        Assertions.assertEquals("default", realm.admin().toRepresentation().getRealm());
    }

    @Test
    public void testCreatedClient() {
        Assertions.assertEquals("default", client.getClientId());

        List<ClientRepresentation> clients = realm.admin().clients().findByClientId("default");
        Assertions.assertEquals(1, clients.size());
    }

}
