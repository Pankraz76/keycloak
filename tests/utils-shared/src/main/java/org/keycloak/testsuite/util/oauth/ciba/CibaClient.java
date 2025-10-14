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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.keycloak.protocol.oidc.grants.ciba.channel.AuthenticationChannelResponse;
import org.keycloak.testsuite.util.oauth.AbstractOAuthClient;
import org.keycloak.testsuite.util.oauth.AccessTokenResponse;
import org.keycloak.util.JsonSerialization;
import org.keycloak.util.TokenUtil;

public class CibaClient {

    private final AbstractOAuthClient<?> client;

    public CibaClient(AbstractOAuthClient<?> client) {
        this.client = client;
    }

    public BackchannelAuthenticationRequest backchannelAuthenticationRequest(String userid) {
        return new BackchannelAuthenticationRequest(userid, client);
    }

    public AuthenticationRequestAcknowledgement doBackchannelAuthenticationRequest(String userid) {
        return backchannelAuthenticationRequest(userid).send();
    }

    public BackchannelAuthenticationTokenRequest backchannelAuthenticationTokenRequest(String authReqId) {
        return new BackchannelAuthenticationTokenRequest(authReqId, client);
    }

    public AccessTokenResponse doBackchannelAuthenticationTokenRequest(String authReqId) {
        return backchannelAuthenticationTokenRequest(authReqId).send();
    }

    public int doAuthenticationChannelCallback(String requestToken, AuthenticationChannelResponse.Status authStatus) throws Exception {
        HttpPost post = new HttpPost(client.getEndpoints().getBackchannelAuthenticationCallback());
        post.setHeader("Authorization", TokenUtil.TOKEN_TYPE_BEARER + " " + requestToken);
        post.setEntity(new StringEntity(JsonSerialization.writeValueAsString(new AuthenticationChannelResponse(authStatus)), ContentType.APPLICATION_JSON));

        try (CloseableHttpResponse response = client.httpClient().get().execute(post)) {
            return response.getStatusLine().getStatusCode();
        }
    }

}
