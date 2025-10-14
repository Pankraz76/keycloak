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
package org.keycloak.models.workflow.conditions;

import java.util.List;
import java.util.Map;

import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.models.workflow.WorkflowConditionProviderFactory;

public class UserAttributeWorkflowConditionFactory implements WorkflowConditionProviderFactory<UserAttributeWorkflowConditionProvider> {

    public static final String ID = "has-user-attribute";

    @Override
    public UserAttributeWorkflowConditionProvider create(KeycloakSession session, Map<String, List<String>> config) {
        return new UserAttributeWorkflowConditionProvider(session, config);
    }

    @Override
    public UserAttributeWorkflowConditionProvider create(KeycloakSession session, List<String> configParameters) {
        if (configParameters.size() % 2 != 0) {
            throw new IllegalArgumentException("Expected even number of configuration parameters (attribute key/value pairs)");
        }
            // Convert list of parameters into map of expected attributes
        Map<String, List<String>> expectedAttributes = new java.util.HashMap<>();
        for (int i = 0; i < configParameters.size(); i += 2) {
            String key = configParameters.get(i);
            String value = configParameters.get(i + 1);
            // value can have multiple values separated by comma
            List<String> values = List.of(value.split(","));
            expectedAttributes.put(key, values);
        }
        return create(session, expectedAttributes);
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public void init(org.keycloak.Config.Scope config) {
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
    }

    @Override
    public void close() {
    }

}
