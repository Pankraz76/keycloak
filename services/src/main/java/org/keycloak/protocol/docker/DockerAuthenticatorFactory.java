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
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.Collections;
import java.util.List;

import static org.keycloak.models.AuthenticationExecutionModel.Requirement;

public class DockerAuthenticatorFactory implements AuthenticatorFactory {

    @Override
    public String getHelpText() {
        return "Uses HTTP Basic authentication to validate docker users, returning a docker error token on auth failure";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return Collections.emptyList();
    }

    @Override
    public String getDisplayType() {
        return "Docker Authenticator";
    }

    @Override
    public String getReferenceCategory() {
        return "docker";
    }

    @Override
    public boolean isConfigurable() {
        return false;
    }

    private static final Requirement[] REQUIREMENT_CHOICES = {
            Requirement.REQUIRED,
    };

    @Override
    public Requirement[] getRequirementChoices() {
        return REQUIREMENT_CHOICES;
    }


    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public Authenticator create(KeycloakSession session) {
        return new DockerAuthenticator();
    }

    @Override
    public void init(Config.Scope config) {
        // no-op
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        // no-op
    }

    @Override
    public void close() {
        // no-op
    }

    @Override
    public String getId() {
        return DockerAuthenticator.ID;
    }

}
