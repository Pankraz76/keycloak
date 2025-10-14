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
package org.keycloak.connections.httpclient;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

public class HttpClientBuilderTest {

    @Test
    public void testDefaultBuilder() throws NoSuchFieldException, IllegalAccessException {
        CloseableHttpClient httpClient = new HttpClientBuilder().build();

        RequestConfig requestConfig = getRequestConfig(httpClient);

        Assert.assertEquals("Default socket timeout is -1 and can be converted by TimeUnit", -1, requestConfig.getSocketTimeout());
        Assert.assertEquals("Default connect timeout is -1 and can be converted by TimeUnit", -1, requestConfig.getConnectTimeout());
    }

    @Test
    public void testTimeUnitSeconds() throws NoSuchFieldException, IllegalAccessException {
        HttpClientBuilder httpClientBuilder = new HttpClientBuilder();
        httpClientBuilder
                .socketTimeout(2, TimeUnit.SECONDS)
                .establishConnectionTimeout(1, TimeUnit.SECONDS);
        CloseableHttpClient httpClient = httpClientBuilder.build();

        RequestConfig requestConfig = getRequestConfig(httpClient);

        Assert.assertEquals("Socket timeout is converted to milliseconds", 2000, requestConfig.getSocketTimeout());
        Assert.assertEquals("Connect timeout is converted to milliseconds", 1000, requestConfig.getConnectTimeout());
    }

    @Test
    public void testTimeUnitMilliSeconds() throws NoSuchFieldException, IllegalAccessException {
        HttpClientBuilder httpClientBuilder = new HttpClientBuilder();
        httpClientBuilder
                .socketTimeout(2000, TimeUnit.MILLISECONDS)
                .establishConnectionTimeout(1000, TimeUnit.MILLISECONDS);
        CloseableHttpClient httpClient = httpClientBuilder.build();

        RequestConfig requestConfig = getRequestConfig(httpClient);

        Assert.assertEquals("Socket timeout is still in milliseconds", 2000, requestConfig.getSocketTimeout());
        Assert.assertEquals("Connect timeout is still in milliseconds", 1000, requestConfig.getConnectTimeout());
    }

    private static RequestConfig getRequestConfig(CloseableHttpClient httpClient) throws NoSuchFieldException, IllegalAccessException {
        Field defaultConfig = httpClient.getClass().getDeclaredField("defaultConfig");
        defaultConfig.setAccessible(true);
        return (RequestConfig) defaultConfig.get(httpClient);
    }
}
