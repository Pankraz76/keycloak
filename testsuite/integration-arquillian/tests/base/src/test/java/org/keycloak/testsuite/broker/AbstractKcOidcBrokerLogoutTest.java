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
package org.keycloak.testsuite.broker;

import org.junit.Before;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.UserRepresentation;

import static org.keycloak.testsuite.admin.ApiUtil.createUserWithAdminClient;
import static org.keycloak.testsuite.admin.ApiUtil.resetUserPassword;

public abstract class AbstractKcOidcBrokerLogoutTest extends AbstractBaseBrokerTest {

    @Before
    public void createUser() {
        log.debug("creating user for realm " + bc.providerRealmName());

        final UserRepresentation user = new UserRepresentation();
        user.setUsername(bc.getUserLogin());
        user.setEmail(bc.getUserEmail());
        user.setEmailVerified(true);
        user.setEnabled(true);

        final RealmResource realmResource = adminClient.realm(bc.providerRealmName());
        final String userId = createUserWithAdminClient(realmResource, user);

        resetUserPassword(realmResource.users().get(userId), bc.getUserPassword(), false);
    }

    @Before
    public void addIdentityProviderToProviderRealm() {
        log.debug("adding identity provider to realm " + bc.consumerRealmName());

        final RealmResource realm = adminClient.realm(bc.consumerRealmName());
        realm.identityProviders().create(bc.setUpIdentityProvider()).close();
    }

    @Before
    public void addClients() {
        addClientsToProviderAndConsumer();
    }

}
