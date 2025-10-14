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
package org.keycloak.testsuite.actions;

import org.jboss.arquillian.graphene.page.Page;
import org.junit.Before;
import org.junit.Test;
import org.keycloak.locale.LocaleSelectorProvider;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.testsuite.AbstractTestRealmKeycloakTest;
import org.keycloak.testsuite.Assert;
import org.keycloak.testsuite.admin.ApiUtil;
import org.keycloak.testsuite.pages.AppPage;
import org.keycloak.testsuite.pages.RegisterPage;

public class AppInitiatedRegistrationTest extends AbstractTestRealmKeycloakTest {

    @Page
    protected AppPage appPage;

    @Page
    protected RegisterPage registerPage;

    @Override
    public void configureTestRealm(RealmRepresentation testRealm) {
    }

    @Before
    public void before() {
        ApiUtil.removeUserByUsername(testRealm(), "test-user@localhost");
    }

    @Test
    public void ensureLocaleParameterIsPropagatedDuringAppInitiatedRegistration() {

        oauth.registrationForm()
                .param(LocaleSelectorProvider.KC_LOCALE_PARAM, "en")
                .open();

        registerPage.assertCurrent();
        registerPage.register("first", "last", "test-user@localhost", "test-user", "test","test");

        appPage.assertCurrent();

        UserRepresentation user = testRealm().users().searchByEmail("test-user@localhost", true).get(0);
        // ensure that the locale was set on the user
        Assert.assertEquals("en", user.getAttributes().get("locale").get(0));
    }
}
