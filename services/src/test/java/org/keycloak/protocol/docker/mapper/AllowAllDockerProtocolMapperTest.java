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


import org.junit.Test;
import org.keycloak.models.AuthenticatedClientSessionModel;
import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.protocol.TestAuthenticatedClientSessionModel;
import org.keycloak.protocol.docker.DockerAuthV2Protocol;
import org.keycloak.representations.docker.DockerAccess;
import org.keycloak.representations.docker.DockerResponseToken;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class AllowAllDockerProtocolMapperTest {

    @Test
    public void transformsResourceScope() {
        DockerResponseToken dockerResponseToken = new DockerResponseToken();
        AuthenticatedClientSessionModel authenticatedClientSessionModel = new TestAuthenticatedClientSessionModel();
        authenticatedClientSessionModel.setNote(DockerAuthV2Protocol.SCOPE_PARAM, "repository:my-image:pull,push");

        DockerResponseToken result = new AllowAllDockerProtocolMapper().transformDockerResponseToken(dockerResponseToken, new ProtocolMapperModel(), null, null, authenticatedClientSessionModel);

        assertThat(result.getAccessItems(), containsInAnyOrder(new DockerAccess("repository:my-image:pull,push")));
    }

    @Test
    public void transformsResourceScopeNull() {
        DockerResponseToken dockerResponseToken = new DockerResponseToken();
        AuthenticatedClientSessionModel authenticatedClientSessionModel = new TestAuthenticatedClientSessionModel();
        authenticatedClientSessionModel.setNote(DockerAuthV2Protocol.SCOPE_PARAM, null);

        DockerResponseToken result = new AllowAllDockerProtocolMapper().transformDockerResponseToken(dockerResponseToken, new ProtocolMapperModel(), null, null, authenticatedClientSessionModel);

        assertThat(result.getAccessItems(), containsInAnyOrder());
    }

    @Test
    public void transformsMultipleResourceScopes() {
        DockerResponseToken dockerResponseToken = new DockerResponseToken();
        AuthenticatedClientSessionModel authenticatedClientSessionModel = new TestAuthenticatedClientSessionModel();
        authenticatedClientSessionModel.setNote(DockerAuthV2Protocol.SCOPE_PARAM, "repository:my-image:pull,push repository:my-base-image:pull");

        DockerResponseToken result = new AllowAllDockerProtocolMapper().transformDockerResponseToken(dockerResponseToken, new ProtocolMapperModel(), null, null, authenticatedClientSessionModel);

        assertThat(result.getAccessItems(), containsInAnyOrder(new DockerAccess("repository:my-image:pull,push"), new DockerAccess("repository:my-base-image:pull")));
    }

}