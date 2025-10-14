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
package org.keycloak.protocol.docker.mapper;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.protocol.ProtocolMapper;
import org.keycloak.protocol.docker.DockerAuthV2Protocol;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.Collections;
import java.util.List;

public abstract class DockerAuthV2ProtocolMapper implements ProtocolMapper {

    public static final String DOCKER_AUTH_V2_CATEGORY = "Docker Auth Mapper";

    @Override
    public String getProtocol() {
        return DockerAuthV2Protocol.LOGIN_PROTOCOL;
    }

    @Override
    public String getDisplayCategory() {
        return DOCKER_AUTH_V2_CATEGORY;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return Collections.emptyList();
    }

    @Override
    public void close() {
        // no-op
    }

    @Override
    public final ProtocolMapper create(final KeycloakSession session) {
        throw new UnsupportedOperationException("The create method is not supported by this mapper");
    }

    @Override
    public void init(final Config.Scope config) {
        // no-op
    }

    @Override
    public void postInit(final KeycloakSessionFactory factory) {
        // no-op
    }
}
