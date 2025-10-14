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

import jakarta.ws.rs.core.UriBuilder;
import org.keycloak.util.JsonSerialization;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractUrlBuilder {

    protected final AbstractOAuthClient<?> client;
    protected Map<String, String> params = new HashMap<>();

    public AbstractUrlBuilder(AbstractOAuthClient<?> client) {
        this.client = client;
        initRequest();
    }

    public abstract String getEndpoint();

    protected abstract void initRequest();

    public void open() {
        client.driver.navigate().to(build());
    }

    protected void parameter(String name, String value) {
        params.put(name, value);
    }

    protected void parameter(String name, Object value) {
        try {
            String encoded = URLEncoder.encode(JsonSerialization.writeValueAsString(value), StandardCharsets.UTF_8);
            parameter(name, encoded);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String build() {
        UriBuilder uriBuilder = UriBuilder.fromUri(getEndpoint());
        params.entrySet().stream().filter(e -> e.getValue() != null).forEach(e -> uriBuilder.queryParam(e.getKey(), e.getValue()));
        return uriBuilder.build().toString();
    }

}
