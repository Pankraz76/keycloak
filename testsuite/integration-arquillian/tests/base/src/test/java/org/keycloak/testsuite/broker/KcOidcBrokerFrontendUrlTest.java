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

import static org.junit.Assert.assertTrue;
import static org.keycloak.testsuite.broker.BrokerTestConstants.IDP_OIDC_ALIAS;
import static org.keycloak.testsuite.broker.BrokerTestConstants.REALM_CONS_NAME;
import static org.keycloak.testsuite.broker.BrokerTestTools.waitForPage;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.testsuite.util.ReverseProxy;

public final class KcOidcBrokerFrontendUrlTest extends AbstractBrokerTest {

    @Rule
    public ReverseProxy proxy = new ReverseProxy();

    @Override
    protected BrokerConfiguration getBrokerConfiguration() {
        return new KcOidcBrokerConfiguration() {
            @Override 
            public RealmRepresentation createConsumerRealm() {
                RealmRepresentation realm = super.createConsumerRealm();

                Map<String, String> attributes = new HashMap<>();

                attributes.put("frontendUrl", proxy.getUrl());

                realm.setAttributes(attributes);
                
                return realm;
            }

            @Override 
            public List<ClientRepresentation> createProviderClients() {
                List<ClientRepresentation> clients = super.createProviderClients();

                List<String> redirectUris = new ArrayList<>();

                redirectUris.add(proxy.getUrl() + "/realms/" + REALM_CONS_NAME + "/broker/" + IDP_OIDC_ALIAS + "/endpoint/*");

                clients.get(0).setRedirectUris(redirectUris);
                
                return clients;
            }
        };
    }

    @Test
    @Override 
    public void testLogInAsUserInIDP() {
        updateExecutions(AbstractBrokerTest::disableUpdateProfileOnFirstLogin);
        createUser(bc.consumerRealmName(), "consumer", "password", "FirstName", "LastName", "consumer@localhost.com");

        oauth.clientId("broker-app");
        oauth.realm(bc.consumerRealmName());
        oauth.baseUrl(proxy.getUrl());
        oauth.openLoginForm();

        log.debug("Clicking social " + bc.getIDPAlias());
        loginPage.clickSocial(bc.getIDPAlias());
        waitForPage(driver, "sign in to", true);
        log.debug("Logging in");

        // make sure the frontend url is used to build the redirect uri when redirecting to the broker
        assertTrue(driver.getCurrentUrl().contains("redirect_uri=" + URLEncoder.encode(proxy.getUrl(), StandardCharsets.UTF_8)));

        loginPage.login(bc.getUserLogin(), bc.getUserPassword());
        waitForPage(driver, "AUTH_RESPONSE", true);
        appPage.assertCurrent();
    }

    @Ignore
    @Test
    @Override
    public void loginWithExistingUser() {
        // no-op
    }
}
