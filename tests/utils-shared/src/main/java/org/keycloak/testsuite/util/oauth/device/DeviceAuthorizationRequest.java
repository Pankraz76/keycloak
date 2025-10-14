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
package org.keycloak.testsuite.util.oauth.device;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.keycloak.OAuth2Constants;
import org.keycloak.testsuite.util.oauth.AbstractHttpPostRequest;
import org.keycloak.testsuite.util.oauth.AbstractOAuthClient;
import org.keycloak.testsuite.util.oauth.PkceGenerator;

import java.io.IOException;

public class DeviceAuthorizationRequest extends AbstractHttpPostRequest<DeviceAuthorizationRequest, DeviceAuthorizationResponse> {

    DeviceAuthorizationRequest(AbstractOAuthClient<?> client) {
        super(client);
    }

    public DeviceAuthorizationRequest codeChallenge(PkceGenerator pkceGenerator) {
        if (pkceGenerator != null) {
            codeChallenge(pkceGenerator.getCodeChallenge(), pkceGenerator.getCodeChallengeMethod());
        }
        return this;
    }

    public DeviceAuthorizationRequest codeChallenge(String codeChallenge, String codeChallengeMethod) {
        parameter(OAuth2Constants.CODE_CHALLENGE, codeChallenge);
        parameter(OAuth2Constants.CODE_CHALLENGE_METHOD, codeChallengeMethod);
        return this;
    }

    @Override
    protected String getEndpoint() {
        return client.getEndpoints().getDeviceAuthorization();
    }

    @Override
    protected void initRequest() {
        parameter(OAuth2Constants.SCOPE, client.config().getScope());
    }

    @Override
    protected DeviceAuthorizationResponse toResponse(CloseableHttpResponse response) throws IOException {
        return new DeviceAuthorizationResponse(response);
    }

}
