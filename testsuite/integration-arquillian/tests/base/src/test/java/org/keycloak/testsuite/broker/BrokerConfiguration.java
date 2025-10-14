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

import org.keycloak.models.IdentityProviderSyncMode;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.IdentityProviderRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;

import java.util.List;

/**
 *
 * @author hmlnarik
 */
public interface BrokerConfiguration {

    /**
     * @return Representation of the realm at the identity provider side.
     */
    RealmRepresentation createProviderRealm();

    /**
     * @return Representation of the realm at the broker side.
     */
    RealmRepresentation createConsumerRealm();

    List<ClientRepresentation> createProviderClients();

    List<ClientRepresentation> createConsumerClients();

    /**
     * @return Representation of the identity provider for declaration in the broker
     */
    default IdentityProviderRepresentation setUpIdentityProvider() {
        return setUpIdentityProvider(IdentityProviderSyncMode.IMPORT);
    }

    /**
     * @return Representation of the identity provider for declaration in the broker
     */
    IdentityProviderRepresentation setUpIdentityProvider(IdentityProviderSyncMode force);

    /**
     * @return Name of realm containing identity provider. Must be consistent with {@link #createProviderRealm()}
     */
    String providerRealmName();

    /**
     * @return Realm name of the broker. Must be consistent with {@link #createConsumerRealm()}
     */
    String consumerRealmName();

    /**
     * @return Client ID of the identity provider as set in provider realm.
     */
    String getIDPClientIdInProviderRealm();

    /**
     * @return User login name of the brokered user
     */
    String getUserLogin();

    /**
     * @return Password of the brokered user
     */
    String getUserPassword();

    /**
     * @return E-mail of the brokered user
     */
    String getUserEmail();

    /**
     * @return Alias of the identity provider as defined in the broker realm
     */
    String getIDPAlias();
}
