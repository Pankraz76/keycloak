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
package org.keycloak.testsuite.util;

import org.apache.http.client.RedirectStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpClientUtils {

    private static final boolean SSL_REQUIRED = Boolean.parseBoolean(System.getProperty("auth.server.ssl.required"));

    public static CloseableHttpClient createDefault() {
        return createDefault(DefaultRedirectStrategy.INSTANCE);
    }

    public static CloseableHttpClient createDefault(RedirectStrategy redirectStrategy) {
        if (SSL_REQUIRED) {
            String keyStorePath = System.getProperty("client.certificate.keystore");
            String keyStorePassword = System.getProperty("client.certificate.keystore.passphrase");
            String trustStorePath = System.getProperty("client.truststore");
            String trustStorePassword = System.getProperty("client.truststore.passphrase");
            return MutualTLSUtils.newCloseableHttpClient(keyStorePath, keyStorePassword, trustStorePath, trustStorePassword, redirectStrategy);
        }
        return HttpClientBuilder.create().build();
    }

}
