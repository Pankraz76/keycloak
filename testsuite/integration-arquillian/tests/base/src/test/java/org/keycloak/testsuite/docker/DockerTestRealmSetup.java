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
package org.keycloak.testsuite.docker;

import org.keycloak.protocol.docker.DockerAuthV2Protocol;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public final class DockerTestRealmSetup {

    private DockerTestRealmSetup() {
    }

    public static RealmRepresentation createRealm(final String realmId) {
        final RealmRepresentation createdRealm = new RealmRepresentation();
        createdRealm.setId(UUID.randomUUID().toString());
        createdRealm.setRealm(realmId);
        createdRealm.setEnabled(true);
        createdRealm.setAuthenticatorConfig(new ArrayList<>());

        return createdRealm;
    }


    public static void configureDockerRegistryClient(final RealmRepresentation dockerRealm, final String clientId) {
        final ClientRepresentation dockerClient = new ClientRepresentation();
        dockerClient.setClientId(clientId);
        dockerClient.setProtocol(DockerAuthV2Protocol.LOGIN_PROTOCOL);
        dockerClient.setEnabled(true);

        final List<ClientRepresentation> clients = Optional.ofNullable(dockerRealm.getClients()).orElse(new ArrayList<>());
        clients.add(dockerClient);
        dockerRealm.setClients(clients);
    }

    public static void configureUser(final RealmRepresentation dockerRealm, final String username, final String password) {
        final UserRepresentation dockerUser = new UserRepresentation();
        dockerUser.setUsername(username);
        dockerUser.setEnabled(true);
        dockerUser.setEmail("docker-users@localhost.localdomain");
        dockerUser.setFirstName("docker");
        dockerUser.setLastName("user");

        final CredentialRepresentation dockerUserCreds = new CredentialRepresentation();
        dockerUserCreds.setType(CredentialRepresentation.PASSWORD);
        dockerUserCreds.setValue(password);
        dockerUser.setCredentials(Collections.singletonList(dockerUserCreds));

        dockerRealm.setUsers(Collections.singletonList(dockerUser));
    }

}
