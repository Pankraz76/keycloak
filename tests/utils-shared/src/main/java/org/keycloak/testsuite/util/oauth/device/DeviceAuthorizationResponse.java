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

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.keycloak.testsuite.util.oauth.AbstractHttpResponse;

import java.io.IOException;

public class DeviceAuthorizationResponse extends AbstractHttpResponse {

    private String deviceCode;
    private String userCode;
    private String verificationUri;
    private String verificationUriComplete;
    private int expiresIn;
    private int interval;

    public DeviceAuthorizationResponse(CloseableHttpResponse response) throws IOException {
        super(response);
    }

    @Override
    protected void parseContent() throws IOException {
        ObjectNode responseJson = asJson();
        deviceCode = responseJson.get("device_code").asText();
        userCode = responseJson.get("user_code").asText();
        verificationUri = responseJson.get("verification_uri").asText();
        verificationUriComplete = responseJson.get("verification_uri_complete").asText();
        expiresIn = responseJson.get("expires_in").asInt();
        interval = responseJson.get("interval").asInt();
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public String getVerificationUri() {
        return verificationUri;
    }

    public String getVerificationUriComplete() {
        return verificationUriComplete;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public int getInterval() {
        return interval;
    }

}
