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
package org.keycloak.testsuite.sessionlimits;

import org.keycloak.authentication.authenticators.sessionlimits.UserSessionLimitsAuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.AuthenticationFlowModel;
import org.keycloak.models.AuthenticatorConfigModel;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.testsuite.runonserver.RunOnServer;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class UserSessionLimitsUtil {
    protected static final String ERROR_TO_DISPLAY = "This account has too many sessions";

    protected static void configureSessionLimits(RealmModel realm, AuthenticationFlowModel flow, String behavior, String realmLimit, String clientLimit) {
        AuthenticationExecutionModel execution = new AuthenticationExecutionModel();
        execution.setParentFlow(flow.getId());
        execution.setRequirement(AuthenticationExecutionModel.Requirement.REQUIRED);
        execution.setAuthenticator(UserSessionLimitsAuthenticatorFactory.USER_SESSION_LIMITS);
        execution.setPriority(30);
        execution.setAuthenticatorFlow(false);

        AuthenticatorConfigModel configModel = new AuthenticatorConfigModel();
        Map<String, String> sessionAuthenticatorConfig = new HashMap<>();
        sessionAuthenticatorConfig.put(UserSessionLimitsAuthenticatorFactory.BEHAVIOR, behavior);
        sessionAuthenticatorConfig.put(UserSessionLimitsAuthenticatorFactory.USER_REALM_LIMIT, realmLimit);
        sessionAuthenticatorConfig.put(UserSessionLimitsAuthenticatorFactory.USER_CLIENT_LIMIT, clientLimit);
        sessionAuthenticatorConfig.put(UserSessionLimitsAuthenticatorFactory.ERROR_MESSAGE, ERROR_TO_DISPLAY);
        configModel.setConfig(sessionAuthenticatorConfig);
        configModel.setAlias("user-session-limits-" + flow.getId());
        configModel = realm.addAuthenticatorConfig(configModel);
        execution.setAuthenticatorConfig(configModel.getId());
        realm.addAuthenticatorExecution(execution);
    }

    static RunOnServer assertClientSessionCount(String realmName, String username, String clientId, int count) {
        return (session) -> {
            RealmModel realm = session.realms().getRealmByName(realmName);
            UserModel user = session.users().getUserByUsername(realm, username);
            assertEquals(count, session.sessions()
                    .getUserSessionsStream(realm, realm.getClientByClientId(clientId))
                    .filter(userSessionModel -> userSessionModel.getUser().getId().equals(user.getId()))
                    .count());
        };
    }

    static RunOnServer assertSessionCount(String realmName, String username, int count) {
        return (session) -> {
            RealmModel realm = session.realms().getRealmByName(realmName);
            UserModel user = session.users().getUserByUsername(realm, username);
            assertEquals(count, session.sessions().getUserSessionsStream(realm, user).count());
        };
    }
}
