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
package org.keycloak.tests.admin.realm;

import jakarta.ws.rs.core.Response;
import org.infinispan.Cache;
import org.junit.jupiter.api.Test;
import org.keycloak.connections.infinispan.InfinispanConnectionProvider;
import org.keycloak.events.admin.OperationType;
import org.keycloak.events.admin.ResourceType;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.testframework.annotations.InjectRealm;
import org.keycloak.testframework.annotations.KeycloakIntegrationTest;
import org.keycloak.testframework.events.AdminEventAssertion;
import org.keycloak.testframework.realm.ManagedRealm;
import org.keycloak.testframework.remote.runonserver.InjectRunOnServer;
import org.keycloak.testframework.remote.runonserver.RunOnServerClient;
import org.keycloak.tests.utils.admin.ApiUtil;
import org.keycloak.tests.utils.admin.AdminEventPaths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@KeycloakIntegrationTest
public class RealmCacheTest extends AbstractRealmTest {

    @InjectRealm(ref = "master", attachTo = "master")
    ManagedRealm masterRealm;

    @InjectRunOnServer(ref = "master", realmRef = "master")
    RunOnServerClient masterRunOnServer;

    @Test
    public void clearRealmCache() {
        String realmId = managedRealm.getId();
        assertTrue(runOnServer.fetch(s -> {
            InfinispanConnectionProvider provider = s.getProvider(InfinispanConnectionProvider.class);
            Cache<Object, Object> cache = provider.getCache("realms");
            return cache.containsKey(realmId);
        }, Boolean.class));

        managedRealm.admin().clearRealmCache();

        // Using master realm to verify that managedRealm cache is empty.
        assertFalse(masterRunOnServer.fetch(s -> {
            InfinispanConnectionProvider provider = s.getProvider(InfinispanConnectionProvider.class);
            Cache<Object, Object> cache = provider.getCache("realms");
            return cache.containsKey(realmId);
        }, Boolean.class));

        // The Admin event must be checked after the verification, because the event poll recreates the cache!
        AdminEventAssertion.assertEvent(adminEvents.poll(), OperationType.ACTION, "clear-realm-cache", ResourceType.REALM);
    }

    @Test
    public void clearUserCache() {
        UserRepresentation user = new UserRepresentation();
        user.setUsername("clearcacheuser");
        Response response = managedRealm.admin().users().create(user);
        String userId = ApiUtil.getCreatedId(response);
        response.close();
        AdminEventAssertion.assertEvent(adminEvents.poll(), OperationType.CREATE, AdminEventPaths.userResourcePath(userId), user, ResourceType.USER);

        managedRealm.admin().users().get(userId).toRepresentation();

        assertTrue(runOnServer.fetch(s -> {
            InfinispanConnectionProvider provider = s.getProvider(InfinispanConnectionProvider.class);
            Cache<Object, Object> cache = provider.getCache("users");
            return cache.containsKey(userId);
        }, Boolean.class));

        managedRealm.admin().clearUserCache();
        AdminEventAssertion.assertEvent(adminEvents.poll(), OperationType.ACTION, "clear-user-cache", ResourceType.REALM);

        assertFalse(runOnServer.fetch(s -> {
            InfinispanConnectionProvider provider = s.getProvider(InfinispanConnectionProvider.class);
            Cache<Object, Object> cache = provider.getCache("users");
            return cache.containsKey(userId);
        }, Boolean.class));
    }

    // NOTE: clearKeysCache tested in KcOIDCBrokerWithSignatureTest
}
