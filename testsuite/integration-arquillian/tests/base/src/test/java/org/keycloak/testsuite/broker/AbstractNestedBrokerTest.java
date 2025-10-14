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

import static org.keycloak.testsuite.broker.BrokerTestTools.waitForPage;

import org.junit.After;
import org.junit.Before;

public abstract class AbstractNestedBrokerTest extends AbstractBaseBrokerTest {

    protected NestedBrokerConfiguration nbc = getNestedBrokerConfiguration();

    protected abstract NestedBrokerConfiguration getNestedBrokerConfiguration();

    @Override
    protected BrokerConfiguration getBrokerConfiguration() {
        return getNestedBrokerConfiguration();
    }

    @Before
    public void createSubConsumerRealm() {
        importRealm(nbc.createSubConsumerRealm());
    }

    @After
    public void removeSubConsumerRealm() {
        adminClient.realm(nbc.subConsumerRealmName()).remove();
    }

    /** Logs in subconsumer realm via consumer IDP via provider IDP and updates account information */
    protected void logInAsUserInNestedIDPForFirstTime() {
        String redirectUri = getAuthServerRoot() + "realms/" + nbc.subConsumerRealmName() + "/account";
        oauth.clientId("account").redirectUri(redirectUri);
        loginPage.open(nbc.subConsumerRealmName());

        waitForPage(driver, "sign in to", true);
        log.debug("Clicking social " + nbc.getSubConsumerIDPDisplayName());
        loginPage.clickSocial(nbc.getSubConsumerIDPDisplayName());
        waitForPage(driver, "sign in to", true);
        log.debug("Clicking social " + nbc.getIDPAlias());
        loginPage.clickSocial(nbc.getIDPAlias());
        waitForPage(driver, "sign in to", true);
        log.debug("Logging in");
        loginPage.login(nbc.getUserLogin(), nbc.getUserPassword());
    }
}
