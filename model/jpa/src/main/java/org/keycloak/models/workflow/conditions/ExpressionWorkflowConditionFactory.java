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

import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.models.workflow.WorkflowConditionProviderFactory;

import java.util.List;
import java.util.Map;

public class ExpressionWorkflowConditionFactory implements WorkflowConditionProviderFactory<ExpressionWorkflowConditionProvider> {

    public static final String ID = "expression";
    public static final String EXPRESSION = "expression";

    @Override
    public ExpressionWorkflowConditionProvider create(KeycloakSession session, Map<String, List<String>> config) {
        return new ExpressionWorkflowConditionProvider(session, config.getOrDefault(EXPRESSION, List.of()).stream().findFirst().orElse(""));
    }

    @Override
    public ExpressionWorkflowConditionProvider create(KeycloakSession session, List<String> configParameters) {
        if (configParameters.size() > 1) {
            throw new IllegalArgumentException("Expected single configuration parameter (expression)");
        }
        return create(session, Map.of(EXPRESSION, configParameters));
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
