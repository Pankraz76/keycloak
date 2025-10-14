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
package org.keycloak.testsuite.forms;

import org.jboss.arquillian.graphene.page.Page;
import org.junit.Test;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.testsuite.AbstractTestRealmKeycloakTest;
import org.keycloak.testsuite.pages.LoginPage;

import org.keycloak.theme.ThemeSelectorProvider;
import static org.junit.Assert.assertEquals;

public class ThemeSelectorTest extends AbstractTestRealmKeycloakTest {

    private static final String SYSTEM_DEFAULT_LOGIN_THEME = ThemeSelectorProvider.DEFAULT_V2;

    @Page
    protected LoginPage loginPage;

    @Override
    public void configureTestRealm(RealmRepresentation testRealm) {
    }

    @Test
    public void clientOverride() {
        loginPage.open();
        assertEquals(System.getProperty(PROPERTY_LOGIN_THEME_DEFAULT, SYSTEM_DEFAULT_LOGIN_THEME), detectTheme());

        ClientRepresentation rep = testRealm().clients().findByClientId("test-app").get(0);

        try {
            rep.getAttributes().put("login_theme", "base");
            testRealm().clients().get(rep.getId()).update(rep);

            loginPage.open();
            assertEquals("base", detectTheme());

            // assign a theme that does not exist, should use the default keycloak
            rep.getAttributes().put("login_theme", "unavailable-theme");
            testRealm().clients().get(rep.getId()).update(rep);

            loginPage.open();
            assertEquals(SYSTEM_DEFAULT_LOGIN_THEME, detectTheme());
        } finally {
            rep.getAttributes().put("login_theme", "");
            testRealm().clients().get(rep.getId()).update(rep);
        }
    }

    private String detectTheme() {
        // for the purpose of the test does not matter which profile is used (product or community)
        if(driver.getPageSource().contains("/login/keycloak/css/login.css") || driver.getPageSource().contains("/login/rh-sso/css/login.css")) {
            return "keycloak";
        } else if (driver.getPageSource().contains("/login/keycloak.v2/css/styles.css") || driver.getPageSource().contains("/login/rh-sso/css/styles.css")) {
            return "keycloak.v2";
        } else {
            return "base";
        }
    }

}
