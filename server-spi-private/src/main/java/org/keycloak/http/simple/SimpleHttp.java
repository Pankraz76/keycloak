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
package org.keycloak.http.simple;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.keycloak.connections.httpclient.HttpClientProvider;
import org.keycloak.models.KeycloakSession;

public class SimpleHttp {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final HttpClient client;
    private long maxConsumedResponseSize;
    private RequestConfig requestConfig;

    private SimpleHttp(HttpClient client, long maxConsumedResponseSize) {
        this.client = client;
        this.maxConsumedResponseSize = maxConsumedResponseSize;
    }

    public static SimpleHttp create(KeycloakSession session) {
        HttpClientProvider provider = session.getProvider(HttpClientProvider.class);
        return new SimpleHttp(provider.getHttpClient(), provider.getMaxConsumedResponseSize());
    }

    public static SimpleHttp create(HttpClient httpClient) {
        return new SimpleHttp(httpClient, HttpClientProvider.DEFAULT_MAX_CONSUMED_RESPONSE_SIZE);
    }

    public SimpleHttp withRequestConfig(RequestConfig requestConfig) {
        this.requestConfig = requestConfig;
        return this;
    }

    public SimpleHttp withMaxConsumedResponseSize(long maxConsumedResponseSize) {
        this.maxConsumedResponseSize = maxConsumedResponseSize;
        return this;
    }

    private SimpleHttpRequest doRequest(String url, SimpleHttpMethod method) {
        return new SimpleHttpRequest(url, method, client, requestConfig, maxConsumedResponseSize, objectMapper);
    }

    public SimpleHttpRequest doGet(String url) {
        return doRequest(url, SimpleHttpMethod.GET);
    }

    public SimpleHttpRequest doPost(String url) {
        return doRequest(url, SimpleHttpMethod.POST);
    }

    public SimpleHttpRequest doPut(String url) {
        return doRequest(url, SimpleHttpMethod.PUT);
    }

    public SimpleHttpRequest doDelete(String url) {
        return doRequest(url, SimpleHttpMethod.DELETE);
    }

    public SimpleHttpRequest doHead(String url) {
        return doRequest(url, SimpleHttpMethod.HEAD);
    }

    public SimpleHttpRequest doPatch(String url) {
        return doRequest(url, SimpleHttpMethod.PATCH);
    }

}
