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
package org.keycloak.testsuite.util.oauth;

import org.apache.http.client.utils.URLEncodedUtils;
import org.keycloak.OAuth2Constants;
import org.keycloak.protocol.oidc.utils.OIDCResponseMode;
import org.keycloak.protocol.oidc.utils.OIDCResponseType;
import org.openqa.selenium.WebDriver;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class AuthorizationEndpointResponse {

    private boolean isRedirected;
    private Map<String, String> params;

    public AuthorizationEndpointResponse(AbstractOAuthClient<?> client) {
        WebDriver driver = client.driver;
        String currentUrl = driver.getCurrentUrl();

        boolean fragment = isFragment(client);
        int parametersIndex = fragment ? currentUrl.indexOf('#') : currentUrl.indexOf('?');
        if (parametersIndex != -1) {
            String urlWithoutParameters = currentUrl.substring(0, parametersIndex);
            String parameters = currentUrl.substring(parametersIndex + 1);

            isRedirected = urlWithoutParameters.equals(client.getRedirectUri());

            params = new HashMap<>();
            URLEncodedUtils.parse(parameters, StandardCharsets.UTF_8)
                    .stream().filter(p -> p.getValue() != null)
                    .forEach(p -> params.put(p.getName(), p.getValue()));
        }
    }

    private boolean isFragment(AbstractOAuthClient<?> client) {
        try {
            OIDCResponseType responseType = OIDCResponseType.parse(client.config().getResponseType());
            OIDCResponseMode responseMode = OIDCResponseMode.parse(client.config().getResponseMode(), responseType);
            return switch (responseMode) {
                case FRAGMENT, FRAGMENT_JWT -> true;
                default -> false;
            };
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isRedirected() {
        return isRedirected;
    }

    public String getCode() {
        return params.get(OAuth2Constants.CODE);
    }

    public String getState() {
        return params.get(OAuth2Constants.STATE);
    }

    public String getError() {
        return params.get(OAuth2Constants.ERROR);
    }

    public String getErrorDescription() {
        return params.get(OAuth2Constants.ERROR_DESCRIPTION);
    }

    public String getSessionState() {
        return params.get(OAuth2Constants.SESSION_STATE);
    }

    public String getAccessToken() {
        return params.get(OAuth2Constants.ACCESS_TOKEN);
    }

    public String getIdToken() {
        return params.get(OAuth2Constants.ID_TOKEN);
    }

    public String getTokenType() {
        return params.get(OAuth2Constants.TOKEN_TYPE);
    }

    public String getExpiresIn() {
        return params.get(OAuth2Constants.EXPIRES_IN);
    }

    public String getResponse() {
        return params.get(OAuth2Constants.RESPONSE);
    }

    public String getIssuer() {
        return params.get(OAuth2Constants.ISSUER);
    }

    public String getKcActionStatus() {
        return params.get("kc_action_status");
    }

}
