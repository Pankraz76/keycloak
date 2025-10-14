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

public class UserInfoRequest extends AbstractHttpGetRequest<UserInfoResponse> {

    private final String token;

    private boolean dpop = false;
    private String dpopProof;

    public UserInfoRequest(String token, AbstractOAuthClient<?> client) {
        super(client);
        this.token = token;
    }

    @Override
    protected String getEndpoint() {
        return client.getEndpoints().getUserInfo();
    }

    public UserInfoRequest dpop(String dpopProof) {
        this.dpop = true;
        this.dpopProof = dpopProof;
        return this;
    }

    @Override
    protected void initRequest() {
        String authorization = (dpop ? "DPoP" : "Bearer") + " " + token;
        header("Authorization", authorization);
        header(TokenUtil.TOKEN_TYPE_DPOP, dpopProof);
    }

    @Override
    protected UserInfoResponse toResponse(CloseableHttpResponse response) throws IOException {
        return new UserInfoResponse(response);
    }

}
