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
import org.keycloak.util.TokenUtil;

import java.io.IOException;

public class PasswordGrantRequest extends AbstractHttpPostRequest<PasswordGrantRequest, AccessTokenResponse> {

    private final String username;
    private final String password;
    private String otp;

    PasswordGrantRequest(String username, String password, AbstractOAuthClient<?> client) {
        super(client);
        this.username = username;
        this.password = password;
    }

    @Override
    protected String getEndpoint() {
        return client.getEndpoints().getToken();
    }

    public PasswordGrantRequest otp(String otp) {
        this.otp = otp;
        return this;
    }

    public PasswordGrantRequest dpopProof(String dpopProof) {
        header(TokenUtil.TOKEN_TYPE_DPOP, dpopProof);
        return this;
    }

    public PasswordGrantRequest param(String name, String value) {
        parameter(name, value);
        return this;
    }

    protected void initRequest() {
        parameter(OAuth2Constants.GRANT_TYPE, OAuth2Constants.PASSWORD);
        parameter("username", username);
        parameter("password", password);
        parameter("otp", otp);

        scope();
    }

    @Override
    protected AccessTokenResponse toResponse(CloseableHttpResponse response) throws IOException {
        return new AccessTokenResponse(response);
    }

}
