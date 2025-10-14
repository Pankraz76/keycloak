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
package org.keycloak.admin.ui.rest;

import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.services.resources.admin.AdminEventBuilder;
import org.keycloak.services.resources.admin.fgap.AdminPermissionEvaluator;

import jakarta.ws.rs.Path;

public final class AdminExtResource {
    private KeycloakSession session;
    private RealmModel realm;
    private AdminPermissionEvaluator auth;
    private AdminEventBuilder adminEvent;

    public AdminExtResource(KeycloakSession session, RealmModel realm, AdminPermissionEvaluator auth, AdminEventBuilder adminEvent) {
        this.session = session;
        this.realm = realm;
        this.auth = auth;
        this.adminEvent = adminEvent;
    }

    @Path("/authentication-management")
    public AuthenticationManagementResource authenticationManagement() {
        return new AuthenticationManagementResource(session, realm, auth);
    }

    @Path("/brute-force-user")
    public BruteForceUsersResource bruteForceUsers() {
        return new BruteForceUsersResource(session, realm, auth);
    }

    @Path("/available-roles")
    public AvailableRoleMappingResource availableRoles() {
        return new AvailableRoleMappingResource(session, realm, auth);
    }

    @Path("/available-event-listeners")
    public AvailableEventListenersResource availableEventListeners() {
        return new AvailableEventListenersResource(session, auth);
    }

    @Path("/effective-roles")
    public EffectiveRoleMappingResource effectiveRoles() {
        return new EffectiveRoleMappingResource(session, realm, auth);
    }

    @Path("/sessions")
    public SessionsResource sessions() {
        return new SessionsResource(session, realm, auth);
    }

    @Path("/realms")
    public UIRealmsResource realms() {
        return new UIRealmsResource(session, auth);
    }

    @Path("/")
    public UIRealmResource realm() {
        return new UIRealmResource(session, auth, adminEvent);
    }
}
