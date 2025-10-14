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

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.testframework.injection.InstanceContext;
import org.keycloak.testframework.injection.RequestedInstance;
import org.keycloak.testframework.injection.Supplier;
import org.keycloak.testframework.injection.SupplierHelpers;
import org.keycloak.testframework.oauth.annotations.InjectOAuthClient;
import org.keycloak.testframework.realm.ClientConfig;
import org.keycloak.testframework.realm.ClientConfigBuilder;
import org.keycloak.testframework.realm.ManagedRealm;
import org.keycloak.testframework.server.KeycloakUrls;
import org.keycloak.testframework.util.ApiUtil;
import org.openqa.selenium.WebDriver;

public class OAuthClientSupplier implements Supplier<OAuthClient, InjectOAuthClient> {

    @Override
    public OAuthClient getValue(InstanceContext<OAuthClient, InjectOAuthClient> instanceContext) {
        InjectOAuthClient annotation = instanceContext.getAnnotation();

        KeycloakUrls keycloakUrls = instanceContext.getDependency(KeycloakUrls.class);
        CloseableHttpClient httpClient = (CloseableHttpClient) instanceContext.getDependency(HttpClient.class);
        WebDriver webDriver = instanceContext.getDependency(WebDriver.class);
        TestApp testApp = instanceContext.getDependency(TestApp.class);

        ManagedRealm realm = instanceContext.getDependency(ManagedRealm.class, annotation.realmRef());

        String redirectUri = testApp.getRedirectionUri();

        ClientConfig clientConfig = SupplierHelpers.getInstance(annotation.config());
        ClientRepresentation testAppClient = clientConfig.configure(ClientConfigBuilder.create())
                .redirectUris(redirectUri)
                .build();

        if (annotation.kcAdmin()) {
            testAppClient.setAdminUrl(testApp.getAdminUri());
        }

        String clientId = testAppClient.getClientId();
        String clientSecret = testAppClient.getSecret();

        ApiUtil.handleCreatedResponse(realm.admin().clients().create(testAppClient));

        OAuthClient oAuthClient = new OAuthClient(keycloakUrls.getBase(), httpClient, webDriver);
        oAuthClient.config().realm(realm.getName()).client(clientId, clientSecret).redirectUri(redirectUri);
        return oAuthClient;
    }

    @Override
    public boolean compatible(InstanceContext<OAuthClient, InjectOAuthClient> a, RequestedInstance<OAuthClient, InjectOAuthClient> b) {
        return a.getAnnotation().ref().equals(b.getAnnotation().ref());
    }

    @Override
    public void close(InstanceContext<OAuthClient, InjectOAuthClient> instanceContext) {
        instanceContext.getValue().close();
    }
}
