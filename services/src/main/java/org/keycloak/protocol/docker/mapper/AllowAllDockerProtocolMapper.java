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

import org.keycloak.models.AuthenticatedClientSessionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.models.UserSessionModel;
import org.keycloak.protocol.docker.DockerAuthV2Protocol;
import org.keycloak.representations.docker.DockerAccess;
import org.keycloak.representations.docker.DockerResponseToken;

/**
 * Populates token with requested scope.  If more scopes are present than what has been requested, they will be removed.
 */
public class AllowAllDockerProtocolMapper extends DockerAuthV2ProtocolMapper implements DockerAuthV2AttributeMapper {

    public static final String PROVIDER_ID = "docker-v2-allow-all-mapper";

    @Override
    public String getDisplayType() {
        return "Allow All";
    }

    @Override
    public String getHelpText() {
        return "Allows all grants, returning the full set of requested access attributes as permitted attributes.";
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public boolean appliesTo(final DockerResponseToken responseToken) {
        return true;
    }

    @Override
    public DockerResponseToken transformDockerResponseToken(final DockerResponseToken responseToken, final ProtocolMapperModel mappingModel,
                                                            final KeycloakSession session, final UserSessionModel userSession, final AuthenticatedClientSessionModel clientSession) {

        responseToken.getAccessItems().clear();

        final String requestedScopes = clientSession.getNote(DockerAuthV2Protocol.SCOPE_PARAM);
        if (requestedScopes != null) {
            for (String requestedScope : requestedScopes.split(" ")) {
                final DockerAccess requestedAccess = new DockerAccess(requestedScope);
                responseToken.getAccessItems().add(requestedAccess);
            }
        }

        return responseToken;
    }
}
