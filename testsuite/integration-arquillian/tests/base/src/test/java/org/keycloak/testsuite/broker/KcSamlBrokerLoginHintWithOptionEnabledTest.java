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

import org.junit.Test;
import org.keycloak.testsuite.Assert;

import static org.junit.Assert.assertEquals;
import static org.keycloak.testsuite.broker.BrokerTestTools.waitForPage;

public class KcSamlBrokerLoginHintWithOptionEnabledTest extends AbstractSamlLoginHintTest {

    // KEYCLOAK-13950
    @Test
    public void testPassLoginHintWithXmlCharShouldEncodeIt() {
        String username = "all-info-set@localhost.com";
        createUser(bc.providerRealmName(), username, "password", "FirstName");

        oauth.clientId("broker-app");
        loginPage.open(bc.consumerRealmName());

        log.debug("Clicking social " + bc.getIDPAlias());
        String fishyLoginHint = "<an-xml-tag>";
        addLoginHintOnSocialButton(fishyLoginHint);
        loginPage.clickSocial(bc.getIDPAlias());
        waitForPage(driver, "sign in to", true);
        Assert.assertTrue("Driver should be on the provider realm page right now",
                driver.getCurrentUrl().contains("/auth/realms/" + bc.providerRealmName() + "/"));
        log.debug("Logging in");

        assertEquals("Username input should contain the SAML subject", loginPage.getUsername(), fishyLoginHint);
    }

    @Override
    boolean isLoginHintOptionEnabled() {
        return true;
    }
}
