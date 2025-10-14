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

import static org.keycloak.common.util.CollectionUtil.collectionEquals;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.workflow.WorkflowConditionProvider;
import org.keycloak.models.workflow.WorkflowEvent;
import org.keycloak.models.workflow.ResourceType;

public class UserAttributeWorkflowConditionProvider implements WorkflowConditionProvider {

    private final Map<String, List<String>> expectedAttributes;
    private final KeycloakSession session;

    public UserAttributeWorkflowConditionProvider(KeycloakSession session, Map<String, List<String>> expectedAttributes) {
        this.session = session;
        this.expectedAttributes = expectedAttributes;;
    }

    @Override
    public boolean evaluate(WorkflowEvent event) {
        if (!ResourceType.USERS.equals(event.getResourceType())) {
            return false;
        }

        String userId = event.getResourceId();
        RealmModel realm = session.getContext().getRealm();
        UserModel user = session.users().getUserById(realm, userId);

        if (user == null) {
            return false;
        }

        for (Entry<String, List<String>> expected : expectedAttributes.entrySet()) {
            List<String> values = user.getAttributes().getOrDefault(expected.getKey(), List.of());
            List<String> expectedValues = expected.getValue();

            if (!collectionEquals(expectedValues, values)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void validate() {
        // no-op
    }

    @Override
    public void close() {

    }
}
