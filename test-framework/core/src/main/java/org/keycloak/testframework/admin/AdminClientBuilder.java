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
package org.keycloak.testframework.admin;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;

public class AdminClientBuilder {

    private final AdminClientFactory adminClientFactory;
    private final KeycloakBuilder delegate;
    private boolean close = false;

    public AdminClientBuilder(AdminClientFactory adminClientFactory, KeycloakBuilder delegate) {
        this.adminClientFactory = adminClientFactory;
        this.delegate = delegate;
    }

    public AdminClientBuilder realm(String realm) {
        delegate.realm(realm);
        return this;
    }

    public AdminClientBuilder grantType(String grantType) {
        delegate.grantType(grantType);
        return this;
    }

    public AdminClientBuilder username(String username) {
        delegate.username(username);
        return this;
    }

    public AdminClientBuilder password(String password) {
        delegate.password(password);
        return this;
    }

    public AdminClientBuilder clientId(String clientId) {
        delegate.clientId(clientId);
        return this;
    }

    public AdminClientBuilder scope(String scope) {
        delegate.scope(scope);
        return this;
    }

    public AdminClientBuilder clientSecret(String clientSecret) {
        delegate.clientSecret(clientSecret);
        return this;
    }

    public AdminClientBuilder authorization(String accessToken) {
        delegate.authorization(accessToken);
        return this;
    }

    public AdminClientBuilder autoClose() {
        this.close = true;
        return this;
    }

    public Keycloak build() {
        Keycloak keycloak = delegate.build();
        if (close) {
            adminClientFactory.addToClose(keycloak);
        }
        return keycloak;
    }
}
