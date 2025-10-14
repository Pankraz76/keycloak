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
package org.keycloak.testsuite.model;

import org.junit.Test;
import org.keycloak.common.Profile;
import org.keycloak.models.SingleUseObjectProvider;
import org.keycloak.models.UserLoginFailureProvider;
import org.keycloak.models.UserSessionProvider;
import org.keycloak.models.sessions.infinispan.PersistentUserSessionProvider;
import org.keycloak.models.sessions.infinispan.remote.RemoteInfinispanAuthenticationSessionProvider;
import org.keycloak.models.sessions.infinispan.remote.RemoteInfinispanSingleUseObjectProvider;
import org.keycloak.models.sessions.infinispan.remote.RemoteUserLoginFailureProvider;
import org.keycloak.sessions.AuthenticationSessionProvider;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assume.assumeTrue;

public class MultiSiteProfileTest extends KeycloakModelTest {

    @Test
    public void testMultiSiteConfiguredCorrectly() {
        assumeTrue(Profile.isFeatureEnabled(Profile.Feature.MULTI_SITE));
        assumeTrue(Profile.isFeatureEnabled(Profile.Feature.PERSISTENT_USER_SESSIONS));

        inComittedTransaction(session -> {
            UserSessionProvider sessions = session.sessions();
            assertThat(sessions, instanceOf(PersistentUserSessionProvider.class));

            AuthenticationSessionProvider authenticationSessionProvider = session.authenticationSessions();
            assertThat(authenticationSessionProvider, instanceOf(RemoteInfinispanAuthenticationSessionProvider.class));

            UserLoginFailureProvider userLoginFailureProvider = session.loginFailures();
            assertThat(userLoginFailureProvider, instanceOf(RemoteUserLoginFailureProvider.class));

            SingleUseObjectProvider singleUseObjectProvider = session.singleUseObjects();
            assertThat(singleUseObjectProvider, instanceOf(RemoteInfinispanSingleUseObjectProvider.class));
        });
    }
}
