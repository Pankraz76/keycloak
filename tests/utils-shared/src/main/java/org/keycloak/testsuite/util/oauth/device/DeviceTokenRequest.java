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
import org.keycloak.testsuite.util.oauth.AccessTokenResponse;
import org.keycloak.testsuite.util.oauth.PkceGenerator;

import java.io.IOException;

public class DeviceTokenRequest extends AbstractHttpPostRequest<DeviceTokenRequest, AccessTokenResponse> {

    private final String deviceCode;

    DeviceTokenRequest(String deviceCode, AbstractOAuthClient<?> client) {
        super(client);
        this.deviceCode = deviceCode;
    }

    public DeviceTokenRequest codeVerifier(PkceGenerator pkceGenerator) {
        if (pkceGenerator != null) {
            codeVerifier(pkceGenerator.getCodeVerifier());
        }
        return this;
    }

    public DeviceTokenRequest codeVerifier(String codeVerifier) {
        parameter(OAuth2Constants.CODE_VERIFIER, codeVerifier);
        return this;
    }

    @Override
    protected String getEndpoint() {
        return client.getEndpoints().getToken();
    }

    @Override
    protected void initRequest() {
        parameter(OAuth2Constants.GRANT_TYPE, OAuth2Constants.DEVICE_CODE_GRANT_TYPE);
        parameter("device_code", deviceCode);
    }

    @Override
    protected AccessTokenResponse toResponse(CloseableHttpResponse response) throws IOException {
        return new AccessTokenResponse(response);
    }

}
