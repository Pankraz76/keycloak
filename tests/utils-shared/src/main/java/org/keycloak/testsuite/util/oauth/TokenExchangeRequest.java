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
import org.keycloak.OAuth2Constants;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TokenExchangeRequest extends AbstractHttpPostRequest<TokenExchangeRequest, AccessTokenResponse> {

    private final String subjectToken;
    private final String subjectTokenType;
    private String requestedTokenType;
    private String requestedSubject;
    private List<String> audience;

    TokenExchangeRequest(String subjectToken, String subjectTokenType, AbstractOAuthClient<?> client) {
        super(client);
        this.subjectToken = subjectToken;
        this.subjectTokenType = subjectTokenType;
    }

    @Override
    protected String getEndpoint() {
        return client.getEndpoints().getToken();
    }

    public TokenExchangeRequest requestedTokenType(String requestedTokenType) {
        this.requestedTokenType = requestedTokenType;
        return this;
    }

    public TokenExchangeRequest requestedSubject(String requestedSubject) {
        this.requestedSubject = requestedSubject;
        return this;
    }

    public TokenExchangeRequest audience(List<String> audience) {
        this.audience = audience;
        return this;
    }

    public TokenExchangeRequest audience(String... audience) {
        this.audience = Arrays.stream(audience).toList();
        return this;
    }

    protected void initRequest() {
        parameter(OAuth2Constants.GRANT_TYPE, OAuth2Constants.TOKEN_EXCHANGE_GRANT_TYPE);

        parameter(OAuth2Constants.SUBJECT_TOKEN, subjectToken);
        parameter(OAuth2Constants.SUBJECT_TOKEN_TYPE, subjectTokenType != null ? subjectTokenType : OAuth2Constants.ACCESS_TOKEN_TYPE);

        if (requestedTokenType != null) {
            parameter(OAuth2Constants.REQUESTED_TOKEN_TYPE, requestedTokenType);
        }

        if (requestedSubject != null) {
            parameter(OAuth2Constants.REQUESTED_SUBJECT, requestedSubject);
        }

        if (audience != null) {
            audience.forEach(a -> parameter(OAuth2Constants.AUDIENCE, a));
        }

        parameter(OAuth2Constants.SCOPE, client.config().getScope(false));
    }

    @Override
    protected AccessTokenResponse toResponse(CloseableHttpResponse response) throws IOException {
        return new AccessTokenResponse(response);
    }

}
