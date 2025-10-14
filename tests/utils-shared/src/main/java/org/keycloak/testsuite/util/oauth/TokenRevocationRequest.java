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

import org.apache.http.client.methods.CloseableHttpResponse;
import org.keycloak.util.TokenUtil;

import java.io.IOException;

public class TokenRevocationRequest extends AbstractHttpPostRequest<TokenRevocationRequest, TokenRevocationResponse> {

    private final String token;
    private String tokenTypeHint;

    TokenRevocationRequest(String token, AbstractOAuthClient<?> client) {
        super(client);
        this.token = token;
    }

    public TokenRevocationRequest tokenTypeHint(String tokenTypeHint) {
        this.tokenTypeHint = tokenTypeHint;
        return this;
    }

    public TokenRevocationRequest accessToken() {
        this.tokenTypeHint = "access_token";
        return this;
    }

    public TokenRevocationRequest refreshToken() {
        this.tokenTypeHint = "refresh_token";
        return this;
    }

    public TokenRevocationRequest dpopProof(String dpopProof) {
        header(TokenUtil.TOKEN_TYPE_DPOP, dpopProof);
        return this;
    }

    @Override
    protected String getEndpoint() {
        return client.getEndpoints().getRevocation();
    }

    protected void initRequest() {
        parameter("token", token);
        parameter("token_type_hint", tokenTypeHint);
    }

    @Override
    protected TokenRevocationResponse toResponse(CloseableHttpResponse response) throws IOException {
        return new TokenRevocationResponse(response);
    }

}
