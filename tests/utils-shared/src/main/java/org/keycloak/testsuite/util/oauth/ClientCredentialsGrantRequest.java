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

public class ClientCredentialsGrantRequest extends AbstractHttpPostRequest<ClientCredentialsGrantRequest, AccessTokenResponse> {

    ClientCredentialsGrantRequest(AbstractOAuthClient<?> client) {
        super(client);
    }

    public ClientCredentialsGrantRequest dpopProof(String dpopProof) {
        header(TokenUtil.TOKEN_TYPE_DPOP, dpopProof);
        return this;
    }

    @Override
    protected String getEndpoint() {
        return client.getEndpoints().getToken();
    }

    protected void initRequest() {
        parameter(OAuth2Constants.GRANT_TYPE, OAuth2Constants.CLIENT_CREDENTIALS);

        scope();
    }

    @Override
    protected AccessTokenResponse toResponse(CloseableHttpResponse response) throws IOException {
        return new AccessTokenResponse(response);
    }

}
