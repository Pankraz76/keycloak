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
package org.keycloak.testsuite.broker;

import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.models.IdentityProviderSyncMode;
import org.keycloak.representations.idm.IdentityProviderRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.testsuite.Assert;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.keycloak.OAuth2Constants.*;
import static org.keycloak.testsuite.broker.BrokerTestConstants.*;
import static org.keycloak.testsuite.broker.BrokerTestTools.*;

public class KcOidcBrokerUiLocalesWithIdpHintTest extends AbstractBrokerTest {

    private static final Locale HUNGARIAN = Locale.forLanguageTag("hu");

    @Override
    protected BrokerConfiguration getBrokerConfiguration() {
        return new KcOidcBrokerConfigurationWithUiLocalesEnabled();
    }

    private class KcOidcBrokerConfigurationWithUiLocalesEnabled extends KcOidcBrokerConfiguration {

        @Override
        public IdentityProviderRepresentation setUpIdentityProvider(IdentityProviderSyncMode syncMode) {
            IdentityProviderRepresentation idp = createIdentityProvider(IDP_OIDC_ALIAS, IDP_OIDC_PROVIDER_ID);
            Map<String, String> config = idp.getConfig();
            applyDefaultConfiguration(config, syncMode);
            config.put("uiLocales", "true");
            return idp;
        }
    }

    @Override
    protected void loginUser() {
        oauth.clientId("broker-app");
        loginPage.open(bc.consumerRealmName());

        driver.navigate().to(driver.getCurrentUrl() + "&ui_locales=hu&kc_idp_hint=kc-oidc-idp");

        waitForPage(driver, "belépés ide", true); // sign in to

        assertThat("Driver should be on the provider realm page right now",
                driver.getCurrentUrl(), containsString("/auth/realms/" + bc.providerRealmName() + "/"));

        assertThat(UI_LOCALES_PARAM + "=" + HUNGARIAN.toLanguageTag() + " should be part of the url",
            driver.getCurrentUrl(), containsString(UI_LOCALES_PARAM + "=" + HUNGARIAN.toLanguageTag()));
        assertThat("The provider realm should be in Hungarian because the ui_locales is passed",
            driver.getPageSource(), containsString("Jelentkezzen be a fiókjába")); // Sign in to your account

        loginPage.login(bc.getUserLogin(), bc.getUserPassword());
        waitForPage(driver, "fiók adatainak módosítása", false); // update account information

        assertThat("The consumer realm should be in Hungarian even after the redirect from the IDP.",
                driver.getPageSource(), containsString("Fiók adatainak módosítása"));// update account information

        assertThat("We must be on correct realm right now",
                driver.getCurrentUrl(), containsString("/auth/realms/" + bc.consumerRealmName() + "/"));

        log.debug("Updating info on updateAccount page");
        updateAccountInformationPage.updateAccountInformation(bc.getUserLogin(), bc.getUserEmail(), "Firstname", "Lastname");

        UsersResource consumerUsers = adminClient.realm(bc.consumerRealmName()).users();

        int userCount = consumerUsers.count();
        Assert.assertTrue("There must be at least one user", userCount > 0);

        List<UserRepresentation> users = consumerUsers.search("", 0, userCount);

        boolean isUserFound = false;
        for (UserRepresentation user : users) {
            if (user.getUsername().equals(bc.getUserLogin()) && user.getEmail().equals(bc.getUserEmail())) {
                isUserFound = true;
                break;
            }
        }

        Assert.assertTrue("There must be user " + bc.getUserLogin() + " in realm " + bc.consumerRealmName(),
                isUserFound);
    }
}
