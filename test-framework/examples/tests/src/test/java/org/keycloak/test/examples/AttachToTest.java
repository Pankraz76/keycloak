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
import org.keycloak.admin.client.Keycloak;
import org.keycloak.testframework.annotations.InjectAdminClient;
import org.keycloak.testframework.annotations.InjectClient;
import org.keycloak.testframework.annotations.InjectRealm;
import org.keycloak.testframework.annotations.KeycloakIntegrationTest;
import org.keycloak.testframework.realm.ManagedClient;
import org.keycloak.testframework.realm.ManagedRealm;

@KeycloakIntegrationTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class AttachToTest {

    @InjectAdminClient
    Keycloak adminClient;

    @InjectRealm(attachTo = "master")
    ManagedRealm attachedRealm;

    @InjectClient
    ManagedClient managedClient;

    @InjectClient(ref = "admin-cli", attachTo = "admin-cli")
    ManagedClient attachedClient;

    @Test
    public void aAttachedRealm() {
        Assertions.assertEquals("master", attachedRealm.getName());
        Assertions.assertEquals("master", attachedRealm.admin().toRepresentation().getRealm());
        attachedRealm.updateWithCleanup(r -> r.editUsernameAllowed(true));
    }

    @Test
    public void bRealmCleanup() {
        Assertions.assertFalse(attachedRealm.admin().toRepresentation().isEditUsernameAllowed());
    }

    @Test
    public void cManagedClient() {
        Assertions.assertEquals("default", adminClient.realm("master").clients().get(managedClient.getId()).toRepresentation().getClientId());
    }

    @Test
    public void dAttachedClient() {
        Assertions.assertEquals("admin-cli", attachedClient.getClientId());
        Assertions.assertEquals("admin-cli", attachedClient.admin().toRepresentation().getClientId());
    }

    @Test
    public void eManagedClient() {
        Assertions.assertEquals("default", managedClient.getClientId());
        Assertions.assertEquals("default", managedClient.admin().toRepresentation().getClientId());
    }
}
