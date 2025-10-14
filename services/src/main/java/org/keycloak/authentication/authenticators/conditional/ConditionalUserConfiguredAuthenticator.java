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
package org.keycloak.authentication.authenticators.conditional;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

public class ConditionalUserConfiguredAuthenticator implements ConditionalAuthenticator {
    public static final ConditionalUserConfiguredAuthenticator SINGLETON = new ConditionalUserConfiguredAuthenticator();

    @Override
    public boolean matchCondition(AuthenticationFlowContext context) {
        return matchConditionInFlow(context, context.getExecution().getParentFlow());
    }

    private boolean matchConditionInFlow(AuthenticationFlowContext context, String flowId) {
        List<AuthenticationExecutionModel> requiredExecutions = new LinkedList<>();
        List<AuthenticationExecutionModel> alternativeExecutions = new LinkedList<>();
        context.getRealm().getAuthenticationExecutionsStream(flowId)
                //Check if the execution's authenticator is a conditional authenticator, as they must not be evaluated here.
                .filter(e -> !isConditionalExecution(context, e))
                .filter(e -> !Objects.equals(context.getExecution().getId(), e.getId()) && !e.isAuthenticatorFlow())
                .forEachOrdered(e -> {
                    if (e.isRequired()) {
                        requiredExecutions.add(e);
                    } else if (e.isAlternative()) {
                        alternativeExecutions.add(e);
                    }
                });
        if (!requiredExecutions.isEmpty()) {
            return requiredExecutions.stream().allMatch(e -> isConfiguredFor(e, context));
        } else  if (!alternativeExecutions.isEmpty()) {
            return alternativeExecutions.stream().anyMatch(e -> isConfiguredFor(e, context));
        }
        return true;
    }

    private boolean isConditionalExecution(AuthenticationFlowContext context, AuthenticationExecutionModel e) {
        AuthenticatorFactory factory = (AuthenticatorFactory) context.getSession().getKeycloakSessionFactory()
                .getProviderFactory(Authenticator.class, e.getAuthenticator());
        if (factory != null) {
            Authenticator auth = factory.create(context.getSession());
            return (auth instanceof ConditionalAuthenticator);
        }
        return false;
    }

    private boolean isConfiguredFor(AuthenticationExecutionModel model, AuthenticationFlowContext context) {
        if (model.isAuthenticatorFlow()) {
            return matchConditionInFlow(context, model.getId());
        }
        AuthenticatorFactory factory = (AuthenticatorFactory) context.getSession().getKeycloakSessionFactory().getProviderFactory(Authenticator.class, model.getAuthenticator());
        Authenticator authenticator = factory.create(context.getSession());
        return authenticator.configuredFor(context.getSession(), context.getRealm(), context.getUser());
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        // Not used
    }

    @Override
    public boolean requiresUser() {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
        // Not used
    }

    @Override
    public void close() {
        // Does nothing
    }
}
