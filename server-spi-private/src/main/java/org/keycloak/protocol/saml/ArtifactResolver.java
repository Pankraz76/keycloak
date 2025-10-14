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
package org.keycloak.protocol.saml;

import org.keycloak.models.AuthenticatedClientSessionModel;
import org.keycloak.models.ClientModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.provider.Provider;


/**
 * Provides a way to create and resolve artifacts for SAML Artifact binding
 */
public interface ArtifactResolver extends Provider {

    /**
     * Returns client model that issued artifact
     *
     * @param session KeycloakSession for searching for client corresponding client
     * @param artifact the artifact
     * @return the client model that issued the artifact
     * @throws ArtifactResolverProcessingException When an error occurs during client search
     */
    ClientModel selectSourceClient(KeycloakSession session, String artifact) throws ArtifactResolverProcessingException;

    /**
     * Creates and stores an artifact
     *
     * @param clientSessionModel client session model that can be used for storing the response for artifact
     * @param entityId id of an issuer that issued the artifactResponse
     * @param artifactResponse serialized Saml ArtifactResponse that represents the response for created artifact
     * @return the artifact
     * @throws ArtifactResolverProcessingException When an error occurs during creation of the artifact.
     */
    String buildArtifact(AuthenticatedClientSessionModel clientSessionModel, String entityId, String artifactResponse) throws ArtifactResolverProcessingException;

    /**
     * Returns a serialized Saml ArtifactResponse corresponding to the artifact that was created by
     * {@link #buildArtifact(AuthenticatedClientSessionModel, String, String) buildArtifact}
     *
     * @param clientSessionModel client session model that can be used for obtaining the artifact response
     * @param artifact the artifact
     * @return serialized Saml ArtifactResponse corresponding to the artifact
     * @throws ArtifactResolverProcessingException When an error occurs during resolution of the artifact.
     */
    String resolveArtifact(AuthenticatedClientSessionModel clientSessionModel, String artifact) throws ArtifactResolverProcessingException;
}
