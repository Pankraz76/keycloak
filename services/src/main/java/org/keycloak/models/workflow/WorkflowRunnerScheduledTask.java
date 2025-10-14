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
import org.keycloak.models.KeycloakContext;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.models.RealmModel;
import org.keycloak.models.utils.KeycloakModelUtils;
import org.keycloak.timer.ScheduledTask;

/**
 * A {@link ScheduledTask} that runs all the scheduled steps for resources on a per-realm basis.
 */
final class WorkflowRunnerScheduledTask implements ScheduledTask {

    private final Logger logger = Logger.getLogger(WorkflowRunnerScheduledTask.class);

    private final KeycloakSessionFactory sessionFactory;

    WorkflowRunnerScheduledTask(KeycloakSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void run(KeycloakSession session) {
        // TODO: Depending on how many realms and the steps in use, this task can consume a lot of gears (e.g.: cpu, memory, and network)
        // we need a smarter mechanism that process realms in batches with some window interval
        session.realms().getRealmsStream().map(RealmModel::getId).forEach(this::runScheduledTasksOnRealm);
    }

    private void runScheduledTasksOnRealm(String id) {
        KeycloakModelUtils.runJobInTransaction(sessionFactory, (KeycloakSession session) -> {
            try {
                KeycloakContext context = session.getContext();
                RealmModel realm = session.realms().getRealm(id);

                context.setRealm(realm);
                new WorkflowsManager(session).runScheduledSteps();

                sessionFactory.publish(new WorkflowStepRunnerSuccessEvent(session));
            } catch (Exception e) {
                logger.errorf(e, "Failed to run workflow steps on realm with id '%s'", id);
            }
        });
    }

    @Override
    public String getTaskName() {
        return "workflow-runner-task";
    }
}
