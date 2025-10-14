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
import org.apache.http.client.methods.HttpGet;
import org.keycloak.utils.MediaType;

import java.io.IOException;

public abstract class AbstractHttpGetRequest<R> {

    protected final AbstractOAuthClient<?> client;

    private HttpGet get;

    public AbstractHttpGetRequest(AbstractOAuthClient<?> client) {
        this.client = client;
    }

    protected abstract String getEndpoint();

    protected abstract void initRequest();

    public R send() {
        get = new HttpGet(getEndpoint());
        get.addHeader("Accept", MediaType.APPLICATION_JSON);
        initRequest();
        try {
            return toResponse(client.httpClient().get().execute(get));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void header(String name, String value) {
        if (value != null) {
            get.addHeader(name, value);
        }
    }

    protected abstract R toResponse(CloseableHttpResponse response) throws IOException;

}
