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
package org.keycloak.admin.ui.rest.model;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.keycloak.models.AuthenticationFlowModel;
import org.keycloak.models.ClientModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;

public class AuthenticationMapper {
    private static final int MAX_USED_BY = 9;

    public static Authentication convertToModel(KeycloakSession session, AuthenticationFlowModel flow, RealmModel realm) {

        final Authentication authentication = new Authentication();
        authentication.setId(flow.getId());
        authentication.setAlias(flow.getAlias());
        authentication.setBuiltIn(flow.isBuiltIn());
        authentication.setDescription(flow.getDescription());

        final List<String> usedByIdp = session.identityProviders().getByFlow(flow.getId(), null,0, MAX_USED_BY).toList();
        if (!usedByIdp.isEmpty()) {
            authentication.setUsedBy(new UsedBy(UsedBy.UsedByType.SPECIFIC_PROVIDERS, usedByIdp));
        }


        Stream<ClientModel> browserFlowOverridingClients = realm.searchClientByAuthenticationFlowBindingOverrides(Collections.singletonMap("browser", flow.getId()), 0, MAX_USED_BY);
        Stream<ClientModel> directGrantFlowOverridingClients = realm.searchClientByAuthenticationFlowBindingOverrides(Collections.singletonMap("direct_grant", flow.getId()), 0, MAX_USED_BY);
        final List<String> usedClients = Stream.concat(browserFlowOverridingClients, directGrantFlowOverridingClients)
                .limit(MAX_USED_BY)
                .map(ClientModel::getClientId).collect(Collectors.toList());

        if (!usedClients.isEmpty()) {
            authentication.setUsedBy(new UsedBy(UsedBy.UsedByType.SPECIFIC_CLIENTS, usedClients));
        }

        final List<String> useAsDefault = Stream.of(realm.getBrowserFlow(), realm.getRegistrationFlow(), realm.getDirectGrantFlow(),
                        realm.getResetCredentialsFlow(), realm.getClientAuthenticationFlow(), realm.getDockerAuthenticationFlow(), realm.getFirstBrokerLoginFlow())
                .filter(f -> f != null && flow.getAlias().equals(f.getAlias())).map(AuthenticationFlowModel::getAlias).collect(Collectors.toList());

        if (!useAsDefault.isEmpty()) {
            authentication.setUsedBy(new UsedBy(UsedBy.UsedByType.DEFAULT, useAsDefault));
        }

        return authentication;
    }
}
