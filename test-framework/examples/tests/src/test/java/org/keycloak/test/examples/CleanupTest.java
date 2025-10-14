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
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.testframework.annotations.InjectRealm;
import org.keycloak.testframework.annotations.KeycloakIntegrationTest;
import org.keycloak.testframework.realm.ManagedRealm;

@KeycloakIntegrationTest
public class CleanupTest {

    @InjectRealm
    ManagedRealm managedRealm;

    @Test
    public void dirty() {
        checkCleanState();

        managedRealm.dirty();

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername("foobar");
        managedRealm.admin().users().create(userRepresentation);
    }

    @Test
    public void cleanupTask() {
        checkCleanState();

        managedRealm.cleanup().add(r -> r.users().list().forEach(u -> r.users().delete(u.getId()).close()));

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername("foobar");
        managedRealm.admin().users().create(userRepresentation).close();
    }

    @Test
    public void cleanupTaskReusableTasks() {
        checkCleanState();

        managedRealm.cleanup().deleteUsers();

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername("foobar");
        managedRealm.admin().users().create(userRepresentation).close();
    }

    @Test
    public void test() {
        checkCleanState();

        managedRealm.updateWithCleanup(r -> r.registrationEmailAsUsername(true));

        Assertions.assertTrue(managedRealm.admin().toRepresentation().isRegistrationEmailAsUsername());
    }

    @Test
    public void test2() {
        checkCleanState();

        managedRealm.updateWithCleanup(r -> r.registrationEmailAsUsername(true));

        Assertions.assertTrue(managedRealm.admin().toRepresentation().isRegistrationEmailAsUsername());
    }

    private void checkCleanState() {
        Assertions.assertTrue(managedRealm.admin().users().list().isEmpty());
        Assertions.assertFalse(managedRealm.admin().toRepresentation().isRegistrationEmailAsUsername());
    }

}
