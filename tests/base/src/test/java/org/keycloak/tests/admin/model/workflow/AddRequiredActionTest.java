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
package org.keycloak.tests.admin.model.workflow;

import org.junit.jupiter.api.Test;
import org.keycloak.models.UserModel;
import org.keycloak.models.workflow.AddRequiredActionStepProvider;
import org.keycloak.models.workflow.AddRequiredActionStepProviderFactory;
import org.keycloak.models.workflow.UserCreationTimeWorkflowProviderFactory;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.workflows.WorkflowStepRepresentation;
import org.keycloak.representations.workflows.WorkflowRepresentation;
import org.keycloak.testframework.annotations.InjectRealm;
import org.keycloak.testframework.annotations.KeycloakIntegrationTest;
import org.keycloak.testframework.injection.LifeCycle;
import org.keycloak.testframework.realm.ManagedRealm;
import org.keycloak.testframework.realm.UserConfigBuilder;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@KeycloakIntegrationTest(config = WorkflowsServerConfig.class)
public class AddRequiredActionTest {

    @InjectRealm(lifecycle = LifeCycle.METHOD)
    ManagedRealm managedRealm;

    @Test
    public void testStepRun() {
        managedRealm.admin().workflows().create(WorkflowRepresentation.create()
                .of(UserCreationTimeWorkflowProviderFactory.ID)
                .withSteps(
                        WorkflowStepRepresentation.create()
                                .of(AddRequiredActionStepProviderFactory.ID)
                                .withConfig(AddRequiredActionStepProvider.REQUIRED_ACTION_KEY, "UPDATE_PASSWORD")
                                .build()
                ).build()).close();

        managedRealm.admin().users().create(UserConfigBuilder.create().username("test").build()).close();

        List< UserRepresentation> users = managedRealm.admin().users().search("test");
        assertThat(users, hasSize(1));
        UserRepresentation userRepresentation = users.get(0);
        assertThat(userRepresentation.getRequiredActions(), hasSize(1));
        assertThat(userRepresentation.getRequiredActions().get(0), is(UserModel.RequiredAction.UPDATE_PASSWORD.name()));
    }

}
