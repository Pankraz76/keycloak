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
package org.keycloak.services.resources.account;

import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.credential.PasswordCredentialModel;

public class PasswordUtil {

    private final UserModel user;

    @Deprecated
    public PasswordUtil(KeycloakSession session, UserModel user) {
        this.user = user;
    }

    public PasswordUtil(UserModel user) {
        this.user = user;
    }

    /**
     * @deprecated Instead, use {@link #isConfigured()}
     */
    @Deprecated
    public boolean isConfigured(KeycloakSession session, RealmModel realm, UserModel user) {
        return user.credentialManager().isConfiguredFor(PasswordCredentialModel.TYPE);
    }

    public boolean isConfigured() {
        return user.credentialManager().isConfiguredFor(PasswordCredentialModel.TYPE);
    }

    public void update() {

    }

}
