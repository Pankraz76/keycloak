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

import org.keycloak.testsuite.util.oauth.AbstractOAuthClient;
import org.keycloak.testsuite.util.oauth.AccessTokenResponse;

public class DeviceClient {

    private final AbstractOAuthClient<?> client;

    public DeviceClient(AbstractOAuthClient<?> client) {
        this.client = client;
    }

    public DeviceAuthorizationRequest deviceAuthorizationRequest() {
        return new DeviceAuthorizationRequest(client);
    }

    public DeviceAuthorizationResponse doDeviceAuthorizationRequest() {
        return deviceAuthorizationRequest().send();
    }

    public DeviceTokenRequest deviceTokenRequest(String deviceCode) {
        return new DeviceTokenRequest(deviceCode, client);
    }

    public AccessTokenResponse doDeviceTokenRequest(String deviceCode) {
        return deviceTokenRequest(deviceCode).send();
    }

}
