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

import org.jboss.logging.Logger;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;


public class AddRequiredActionStepProvider implements WorkflowStepProvider {

    public static String REQUIRED_ACTION_KEY = "action";

    private final KeycloakSession session;
    private final ComponentModel stepModel;
    private final Logger log = Logger.getLogger(AddRequiredActionStepProvider.class);

    public AddRequiredActionStepProvider(KeycloakSession session, ComponentModel model) {
        this.session = session;
        this.stepModel = model;
    }

    @Override
    public void run(WorkflowExecutionContext context) {
        RealmModel realm = session.getContext().getRealm();
        UserModel user = session.users().getUserById(realm, context.getResourceId());

        if (user != null) {
            try {
                UserModel.RequiredAction action = UserModel.RequiredAction.valueOf(stepModel.getConfig().getFirst(REQUIRED_ACTION_KEY));
                log.debugv("Adding required action {0} to user {1})", action, user.getId());
                user.addRequiredAction(action);
            } catch (IllegalArgumentException e) {
                log.warnv("Invalid required action {0} configured in AddRequiredActionProvider", stepModel.getConfig().getFirst(REQUIRED_ACTION_KEY));
            }
        }
    }

    @Override
    public void close() {
    }
}
