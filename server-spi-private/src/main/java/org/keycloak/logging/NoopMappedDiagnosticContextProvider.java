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
package org.keycloak.logging;

import org.keycloak.models.ClientModel;
import org.keycloak.models.KeycloakContext;
import org.keycloak.models.OrganizationModel;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserSessionModel;
import org.keycloak.sessions.AuthenticationSessionModel;

public class NoopMappedDiagnosticContextProvider implements MappedDiagnosticContextProvider {

    @Override
    public void update(KeycloakContext keycloakContext, AuthenticationSessionModel session) {
        // no-op
    }

    @Override
    public void update(KeycloakContext keycloakContext, RealmModel realm) {
        // no-op
    }

    @Override
    public void update(KeycloakContext keycloakContext, ClientModel client) {
        // no-op
    }

    @Override
    public void update(KeycloakContext keycloakContext, OrganizationModel organization) {
        // no-op
    }

    @Override
    public void update(KeycloakContext keycloakContext, UserSessionModel userSession) {
        // no-op
    }

    @Override
    public void close() {
        // no-op
    }
}
