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

import org.apache.http.impl.client.CloseableHttpClient;

public class HttpClientManager {

    private final CloseableHttpClient defaultClient;
    private CloseableHttpClient customClient;

    public HttpClientManager(CloseableHttpClient defaultClient) {
        this.defaultClient = defaultClient;
    }

    public CloseableHttpClient get() {
        if (customClient != null) {
            return customClient;
        } else {
            return defaultClient;
        }
    }

    public void set(CloseableHttpClient httpClient) {
        this.customClient = httpClient;
    }

    public void reset() {
        this.customClient = null;
    }

}
