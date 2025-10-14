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
package org.keycloak.testsuite.util.oauth.ciba;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.keycloak.OAuth2Constants;
import org.keycloak.protocol.oidc.OIDCLoginProtocol;
import org.keycloak.protocol.oidc.grants.ciba.CibaGrantType;
import org.keycloak.testsuite.util.oauth.AbstractHttpPostRequest;
import org.keycloak.testsuite.util.oauth.AbstractOAuthClient;

import java.io.IOException;
import java.util.Map;

public class BackchannelAuthenticationRequest extends AbstractHttpPostRequest<BackchannelAuthenticationRequest, AuthenticationRequestAcknowledgement> {

    private final String userid;
    private String bindingMessage;
    private String acrValues;
    private String clientNotificationToken;
    private Map<String, String> additionalParams;

    BackchannelAuthenticationRequest(String userid, AbstractOAuthClient<?> client) {
        super(client);
        this.userid = userid;
    }

    public BackchannelAuthenticationRequest bindingMessage(String bindingMessage) {
        this.bindingMessage = bindingMessage;
        return this;
    }

    public BackchannelAuthenticationRequest acrValues(String acrValues) {
        this.acrValues = acrValues;
        return this;
    }

    public BackchannelAuthenticationRequest clientNotificationToken(String clientNotificationToken) {
        this.clientNotificationToken = clientNotificationToken;
        return this;
    }

    public BackchannelAuthenticationRequest additionalParams(Map<String, String> additionalParams) {
        this.additionalParams = additionalParams;
        return this;
    }

    public BackchannelAuthenticationRequest request(String request) {
        parameter(OIDCLoginProtocol.REQUEST_PARAM, request);
        return this;
    }

    public BackchannelAuthenticationRequest requestUri(String requestUri) {
        parameter(OIDCLoginProtocol.REQUEST_URI_PARAM, requestUri);
        return this;
    }

    @Override
    protected String getEndpoint() {
        return client.getEndpoints().getBackchannelAuthentication();
    }

    protected void initRequest() {
        parameter(OIDCLoginProtocol.LOGIN_HINT_PARAM, userid);
        parameter(CibaGrantType.BINDING_MESSAGE, bindingMessage);
        parameter(OAuth2Constants.ACR_VALUES, acrValues);
        parameter(CibaGrantType.CLIENT_NOTIFICATION_TOKEN, clientNotificationToken);

        if (additionalParams != null) {
            additionalParams.forEach(this::parameter);
        }

        scope();
    }

    @Override
    protected AuthenticationRequestAcknowledgement toResponse(CloseableHttpResponse response) throws IOException {
        return new AuthenticationRequestAcknowledgement(response);
    }

}
