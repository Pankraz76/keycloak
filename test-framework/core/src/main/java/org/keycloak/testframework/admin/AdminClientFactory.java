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

import javax.net.ssl.SSLContext;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class AdminClientFactory {

    private final Supplier<KeycloakBuilder> delegateSupplier;

    private final List<Keycloak> instanceToClose = new LinkedList<>();

    AdminClientFactory(String serverUrl) {
        delegateSupplier = () -> KeycloakBuilder.builder().serverUrl(serverUrl);
    }

    AdminClientFactory(String serverUrl, SSLContext sslContext) {
            delegateSupplier = () ->
                    KeycloakBuilder.builder()
                            .serverUrl(serverUrl)
                            .resteasyClient(Keycloak.getClientProvider().newRestEasyClient(null, sslContext, false));
    }

    public AdminClientBuilder create() {
        return new AdminClientBuilder(this, delegateSupplier.get());
    }

    void addToClose(Keycloak keycloak) {
        instanceToClose.add(keycloak);
    }

    public void close() {
        instanceToClose.forEach(Keycloak::close);
    }

}
