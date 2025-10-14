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
import org.keycloak.testframework.annotations.InjectAdminClient;
import org.keycloak.testframework.annotations.InjectRealm;
import org.keycloak.testframework.annotations.InjectUser;
import org.keycloak.testframework.annotations.KeycloakIntegrationTest;
import org.keycloak.testframework.realm.ManagedRealm;
import org.keycloak.testframework.realm.ManagedUser;
import org.keycloak.testframework.realm.RealmConfig;
import org.keycloak.testframework.realm.RealmConfigBuilder;

@KeycloakIntegrationTest
public class MultipleInstancesTest {

    private final String REALM_A_REF = "realm";
    private final String USER_A_REF = "user";

    @InjectAdminClient
    Keycloak adminClient;

    @InjectRealm
    ManagedRealm realmDef;

    @InjectRealm(ref = REALM_A_REF, config = CustomRealmConfig.class)
    ManagedRealm realmA;

    @InjectUser
    ManagedUser userDef;

    @InjectUser(ref = USER_A_REF, realmRef = REALM_A_REF)
    ManagedUser userA;

    @Test
    public void testMultipleInstances() {
        Assertions.assertEquals("default", realmDef.getName());
        Assertions.assertEquals(REALM_A_REF, realmA.getName());
    }

    @Test
    public void testRealmRef() {
        var realmDefUsers = adminClient.realm("default").users().search("default");
        var realmAUsers = adminClient.realm(REALM_A_REF).users().search(USER_A_REF);
        Assertions.assertEquals(1, realmDefUsers.size());
        Assertions.assertEquals(1, realmAUsers.size());

        Assertions.assertEquals("default", realmDefUsers.get(0).getUsername());
        Assertions.assertEquals(USER_A_REF, realmAUsers.get(0).getUsername());
    }


    public static class CustomRealmConfig implements RealmConfig {

        @Override
        public RealmConfigBuilder configure(RealmConfigBuilder realm) {
            return realm;
        }

    }

}
