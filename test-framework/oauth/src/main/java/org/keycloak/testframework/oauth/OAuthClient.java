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
package org.keycloak.testframework.oauth;

import org.apache.http.impl.client.CloseableHttpClient;
import org.keycloak.OAuth2Constants;
import org.keycloak.testframework.ui.page.LoginPage;
import org.keycloak.testsuite.util.oauth.AbstractOAuthClient;
import org.keycloak.testsuite.util.oauth.OAuthClientConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class OAuthClient extends AbstractOAuthClient<OAuthClient> {

    public OAuthClient(String baseUrl, CloseableHttpClient httpClient, WebDriver webDriver) {
        super(baseUrl, httpClient, webDriver);

        config = new OAuthClientConfig()
                .responseType(OAuth2Constants.CODE);
    }

    @Override
    public void fillLoginForm(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        PageFactory.initElements(driver, loginPage);
        loginPage.fillLogin(username, password);
        loginPage.submit();
    }

    public void close() {
    }

}
