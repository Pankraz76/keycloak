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
import org.keycloak.protocol.oidc.OIDCLoginProtocol;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.testsuite.Assert;

import java.util.List;
import java.util.Map;

import static org.keycloak.testsuite.broker.BrokerTestTools.waitForPage;

public class KcOidcBrokerPromptParameterTest extends AbstractBrokerTest {

    private static final String PROMPT_CONSENT = "consent";
    private static final String PROMPT_LOGIN = "login";

    @Override
    protected BrokerConfiguration getBrokerConfiguration() {
        return new KcOidcBrokerConfiguration2();
    }


    @Override
    protected void loginUser() {
        oauth.clientId("broker-app");
        loginPage.open(bc.consumerRealmName());

        driver.navigate().to(driver.getCurrentUrl() + "&" + OIDCLoginProtocol.PROMPT_PARAM + "=" + PROMPT_CONSENT);

        log.debug("Clicking social " + bc.getIDPAlias());
        loginPage.clickSocial(bc.getIDPAlias());

        waitForPage(driver, "sign in to", true);

        Assert.assertTrue("Driver should be on the provider realm page right now",
                driver.getCurrentUrl().contains("/auth/realms/" + bc.providerRealmName() + "/"));

        Assert.assertFalse(OIDCLoginProtocol.PROMPT_PARAM + "=" + PROMPT_LOGIN + " should not be part of the url",
                driver.getCurrentUrl().contains(OIDCLoginProtocol.PROMPT_PARAM + "=" + PROMPT_LOGIN));

        Assert.assertTrue(OIDCLoginProtocol.PROMPT_PARAM + "=" + PROMPT_CONSENT + " should be part of the url",
                driver.getCurrentUrl().contains(OIDCLoginProtocol.PROMPT_PARAM + "=" + PROMPT_CONSENT));

        log.debug("Logging in");
        loginPage.login(bc.getUserLogin(), bc.getUserPassword());

        waitForPage(driver, "update account information", false);

        updateAccountInformationPage.assertCurrent();
        Assert.assertTrue("We must be on correct realm right now",
                driver.getCurrentUrl().contains("/auth/realms/" + bc.consumerRealmName() + "/"));


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

    private class KcOidcBrokerConfiguration2 extends KcOidcBrokerConfiguration {
        @Override
        protected void applyDefaultConfiguration(final Map<String, String> config, IdentityProviderSyncMode syncMode) {
            super.applyDefaultConfiguration(config, syncMode);
            config.remove("prompt");
        }
    }
}
