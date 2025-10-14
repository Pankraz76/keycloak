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

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class DefaultSamlArtifactResolverFactory implements ArtifactResolverFactory {
    
    /** SAML 2 artifact type code (0x0004). */
    public static final byte[] TYPE_CODE = {0, 4};

    private DefaultSamlArtifactResolver artifactResolver;

    @Override
    public DefaultSamlArtifactResolver create(KeycloakSession session) {
        return artifactResolver;
    }

    @Override
    public void init(Config.Scope config) {
        // Nothing to initialize
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        artifactResolver = new DefaultSamlArtifactResolver();
    }

    @Override
    public void close() {
        // Nothing to close
    }

    @Override
    public String getId() {
        return "default";
    }

}
