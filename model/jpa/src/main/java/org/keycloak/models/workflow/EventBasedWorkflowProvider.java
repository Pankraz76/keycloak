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

import static org.keycloak.representations.workflows.WorkflowConstants.CONFIG_CONDITIONS;
import static org.keycloak.representations.workflows.WorkflowConstants.CONFIG_ON_EVENT;
import static org.keycloak.representations.workflows.WorkflowConstants.CONFIG_RESET_ON;

import java.util.List;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;

public class EventBasedWorkflowProvider implements WorkflowProvider {

    private final KeycloakSession session;
    private final ComponentModel model;
    private final WorkflowsManager manager;

    public EventBasedWorkflowProvider(KeycloakSession session, ComponentModel model) {
        this.session = session;
        this.model = model;
        this.manager = new WorkflowsManager(session);
    }

    @Override
    public List<String> getEligibleResourcesForInitialStep() {
        return List.of();
    }

    @Override
    public boolean supports(ResourceType type) {
        return ResourceType.USERS.equals(type);
    }

    @Override
    public boolean activateOnEvent(WorkflowEvent event) {
        if (!supports(event.getResourceType())) {
            return false;
        }

        if (!isActivationEvent(event)) {
            return false;
        }

        return evaluate(event);
    }

    @Override
    public boolean deactivateOnEvent(WorkflowEvent event) {
        if (!supports(event.getResourceType())) {
            return false;
        }

        List<String> events = model.getConfig().getOrDefault(CONFIG_ON_EVENT, List.of());

        for (String activationEvent : events) {
            ResourceOperationType a = ResourceOperationType.valueOf(activationEvent);

            if (a.isDeactivationEvent(event.getEvent().getClass())) {
                return !evaluate(event);
            }
        }

        return false;
    }

    @Override
    public boolean resetOnEvent(WorkflowEvent event) {
        return isResetEvent(event) && evaluate(event);
    }

    @Override
    public void close() {

    }

    protected boolean evaluate(WorkflowEvent event) {
        List<String> conditions = getModel().getConfig().getOrDefault(CONFIG_CONDITIONS, List.of());

        for (String providerId : conditions) {
            WorkflowConditionProvider condition = manager.getConditionProvider(providerId, model.getConfig());

            if (!condition.evaluate(event)) {
                return false;
            }
        }

        return true;
    }

    protected boolean isActivationEvent(WorkflowEvent event) {
        ResourceOperationType operation = event.getOperation();

        if (ResourceOperationType.AD_HOC.equals(operation)) {
            return true;
        }

        List<String> events = model.getConfig().getOrDefault(CONFIG_ON_EVENT, List.of());

        return events.contains(operation.name());
    }

    protected ComponentModel getModel() {
        return model;
    }

    protected KeycloakSession getSession() {
        return session;
    }

    protected WorkflowsManager getManager() {
        return manager;
    }

    protected boolean isResetEvent(WorkflowEvent event) {
        return model.getConfig()
                .getOrDefault(CONFIG_RESET_ON, List.of())
                .contains(event.getOperation().name());
    }
}
