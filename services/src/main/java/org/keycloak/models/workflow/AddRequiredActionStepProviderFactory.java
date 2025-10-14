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
package org.keycloak.models.workflow;

import java.util.List;

import org.keycloak.Config;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ConfiguredProvider;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.provider.ProviderConfigurationBuilder;

public class AddRequiredActionStepProviderFactory implements WorkflowStepProviderFactory<AddRequiredActionStepProvider>, ConfiguredProvider {

    public static final String ID = "set-user-required-action";

    @Override
    public AddRequiredActionStepProvider create(KeycloakSession session, ComponentModel model) {
        return new AddRequiredActionStepProvider(session, model);
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
        return ID;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return ProviderConfigurationBuilder.create()
                .property()
                .name("action")
                .label("Required Action")
                .helpText("The required action to add to the user (e.g., UPDATE_PASSWORD)")
                .type(ProviderConfigProperty.STRING_TYPE)
                .add()
                .build();
    }

    @Override
    public ResourceType getType() {
        return ResourceType.USERS;
    }

    @Override
    public String getHelpText() {
        return "";
    }
}
