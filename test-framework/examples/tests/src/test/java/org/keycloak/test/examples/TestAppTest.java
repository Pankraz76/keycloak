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
import org.keycloak.representations.adapters.action.PushNotBeforeAction;
import org.keycloak.testframework.annotations.InjectRealm;
import org.keycloak.testframework.annotations.KeycloakIntegrationTest;
import org.keycloak.testframework.oauth.OAuthClient;
import org.keycloak.testframework.oauth.TestApp;
import org.keycloak.testframework.oauth.annotations.InjectOAuthClient;
import org.keycloak.testframework.oauth.annotations.InjectTestApp;
import org.keycloak.testframework.realm.ManagedRealm;

@KeycloakIntegrationTest
public class TestAppTest {

    @InjectOAuthClient(kcAdmin = true)
    OAuthClient oauth;

    @InjectTestApp
    TestApp testApp;

    @InjectRealm
    ManagedRealm managedRealm;

    @Test
    public void testPushNotBefore() throws InterruptedException {
        String clientUuid = managedRealm.admin().clients().findByClientId("test-app").stream().findFirst().get().getId();
        managedRealm.admin().clients().get(clientUuid).pushRevocation();

        PushNotBeforeAction adminPushNotBefore = testApp.kcAdmin().getAdminPushNotBefore();
        Assertions.assertNotNull(adminPushNotBefore);
    }

}
