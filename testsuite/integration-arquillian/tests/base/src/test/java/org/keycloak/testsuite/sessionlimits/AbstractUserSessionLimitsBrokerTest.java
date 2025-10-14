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

import org.junit.Test;
import org.keycloak.authentication.authenticators.sessionlimits.UserSessionLimitsAuthenticatorFactory;
import org.keycloak.models.AuthenticationFlowModel;
import org.keycloak.models.IdentityProviderModel;
import org.keycloak.models.RealmModel;
import org.keycloak.testsuite.broker.AbstractInitializedBaseBrokerTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import static org.keycloak.testsuite.sessionlimits.UserSessionLimitsUtil.assertSessionCount;
import static org.keycloak.testsuite.sessionlimits.UserSessionLimitsUtil.configureSessionLimits;
import static org.keycloak.testsuite.sessionlimits.UserSessionLimitsUtil.ERROR_TO_DISPLAY;

public abstract class AbstractUserSessionLimitsBrokerTest extends AbstractInitializedBaseBrokerTest {
    @Test
    public void testSessionCountExceededAndNewSessionDeniedFirstBrokerLoginFlow() {
        configureFlow(UserSessionLimitsAuthenticatorFactory.DENY_NEW_SESSION, "0", "1");
        loginTwiceAndVerifyBehavior(UserSessionLimitsAuthenticatorFactory.DENY_NEW_SESSION);
    }

    @Test
    public void testSessionCountExceededAndOldestSessionRemovedFirstBrokerLoginFlow() {
        configureFlow(UserSessionLimitsAuthenticatorFactory.TERMINATE_OLDEST_SESSION, "0", "1");
        loginTwiceAndVerifyBehavior(UserSessionLimitsAuthenticatorFactory.TERMINATE_OLDEST_SESSION);
    }

    @Test
    public void testRealmSessionCountExceededAndNewSessionDeniedFirstBrokerLoginFlow() {
        configureFlow(UserSessionLimitsAuthenticatorFactory.DENY_NEW_SESSION, "1", "0");
        loginTwiceAndVerifyBehavior(UserSessionLimitsAuthenticatorFactory.DENY_NEW_SESSION);
    }

    @Test
    public void testRealmSessionCountExceededAndOldestFirstBrokerLoginFlow() {
        configureFlow(UserSessionLimitsAuthenticatorFactory.TERMINATE_OLDEST_SESSION, "1", "0");
        loginTwiceAndVerifyBehavior(UserSessionLimitsAuthenticatorFactory.TERMINATE_OLDEST_SESSION);
    }

    private void configureFlow(String behavior, String realmLimit, String clientLimit)
    {
        String realmName = bc.consumerRealmName();
        String idpAlias = bc.getIDPAlias();
        testingClient.server().run(session -> {
            RealmModel realm = session.realms().getRealmByName(realmName);
            session.getContext().setRealm(realm);
            AuthenticationFlowModel postBrokerFlow = new AuthenticationFlowModel();
            postBrokerFlow.setAlias("post-broker");
            postBrokerFlow.setDescription("post-broker flow with session limits");
            postBrokerFlow.setProviderId("basic-flow");
            postBrokerFlow.setTopLevel(true);
            postBrokerFlow.setBuiltIn(false);
            postBrokerFlow = realm.addAuthenticationFlow(postBrokerFlow);

            configureSessionLimits(realm, postBrokerFlow, behavior, realmLimit, clientLimit);

            IdentityProviderModel idp = session.identityProviders().getByAlias(idpAlias);
            idp.setPostBrokerLoginFlowId(postBrokerFlow.getId());
            session.identityProviders().update(idp);
        });
    }

    private void loginTwiceAndVerifyBehavior(String behavior) {
        logInAsUserInIDPForFirstTime();

        deleteAllCookiesForRealm(bc.consumerRealmName());
        deleteAllCookiesForRealm(bc.providerRealmName());

        logInAsUserInIDP();

        if (UserSessionLimitsAuthenticatorFactory.TERMINATE_OLDEST_SESSION.equals(behavior)) {
            appPage.assertCurrent();
            testingClient.server(bc.consumerRealmName()).run(assertSessionCount(bc.consumerRealmName(), bc.getUserLogin(), 1));
        }
        else if (UserSessionLimitsAuthenticatorFactory.DENY_NEW_SESSION.equals(behavior)) {
            errorPage.assertCurrent();
            assertEquals(ERROR_TO_DISPLAY, errorPage.getError());
        }
        else {
            fail("Invalid behavior " + behavior);
        }
    }
}
