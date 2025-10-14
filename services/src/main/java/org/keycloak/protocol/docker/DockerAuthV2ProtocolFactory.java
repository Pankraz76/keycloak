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
package org.keycloak.protocol.docker;

import org.keycloak.Config;
import org.keycloak.common.Profile;
import org.keycloak.events.EventBuilder;
import org.keycloak.models.ClientModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.models.RealmModel;
import org.keycloak.protocol.AbstractLoginProtocolFactory;
import org.keycloak.protocol.LoginProtocol;
import org.keycloak.protocol.docker.mapper.AllowAllDockerProtocolMapper;
import org.keycloak.provider.EnvironmentDependentProviderFactory;
import org.keycloak.representations.idm.ClientRepresentation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DockerAuthV2ProtocolFactory extends AbstractLoginProtocolFactory implements EnvironmentDependentProviderFactory {

    static Map<String, ProtocolMapperModel> builtins = new HashMap<>();
    static List<ProtocolMapperModel> defaultBuiltins = new ArrayList<>();

    static {
        final ProtocolMapperModel addAllRequestedScopeMapper = new ProtocolMapperModel();
        addAllRequestedScopeMapper.setName(AllowAllDockerProtocolMapper.PROVIDER_ID);
        addAllRequestedScopeMapper.setProtocolMapper(AllowAllDockerProtocolMapper.PROVIDER_ID);
        addAllRequestedScopeMapper.setProtocol(DockerAuthV2Protocol.LOGIN_PROTOCOL);
        addAllRequestedScopeMapper.setConfig(Collections.emptyMap());
        builtins.put(AllowAllDockerProtocolMapper.PROVIDER_ID, addAllRequestedScopeMapper);
        defaultBuiltins.add(addAllRequestedScopeMapper);
    }

    @Override
    protected void createDefaultClientScopesImpl(RealmModel newRealm) {
        // no-op
    }

    @Override
    protected void addDefaults(final ClientModel client) {
        defaultBuiltins.forEach(builtinMapper -> client.addProtocolMapper(builtinMapper));
    }

    @Override
    public Map<String, ProtocolMapperModel> getBuiltinMappers() {
        return builtins;
    }

    @Override
    public Object createProtocolEndpoint(final KeycloakSession session, final EventBuilder event) {
        return new DockerV2LoginProtocolService(session, event);
    }

    @Override
    public void setupClientDefaults(final ClientRepresentation rep, final ClientModel newClient) {
        // no-op
    }


    @Override
    public LoginProtocol create(final KeycloakSession session) {
        return new DockerAuthV2Protocol().setSession(session);
    }

    @Override
    public String getId() {
        return DockerAuthV2Protocol.LOGIN_PROTOCOL;
    }

    @Override
    public boolean isSupported(Config.Scope config) {
        return Profile.isFeatureEnabled(Profile.Feature.DOCKER);
    }

    @Override
    public int order() {
        return -100;
    }
}
