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
package org.keycloak.tests.admin;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpOptions;
import org.junit.jupiter.api.Test;
import org.keycloak.services.cors.Cors;
import org.keycloak.testframework.annotations.InjectHttpClient;
import org.keycloak.testframework.annotations.InjectKeycloakUrls;
import org.keycloak.testframework.annotations.KeycloakIntegrationTest;
import org.keycloak.testframework.server.KeycloakUrls;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@KeycloakIntegrationTest
public class AdminPreflightTest {

    @InjectHttpClient
    HttpClient client;

    @InjectKeycloakUrls
    KeycloakUrls keycloakUrls;

    @Test
    public void testPreflight() throws IOException {
        testPreflightForAdminPath("/realms/master/users");
    }

    @Test
    public void testPreflightServerInfo() throws IOException {
        testPreflightForAdminPath("/serverinfo");
    }

    private void testPreflightForAdminPath(String path) throws IOException {
        HttpOptions options = new HttpOptions(keycloakUrls.getAdminBuilder().path(path).build());
        options.setHeader("Origin", "http://test");

        HttpResponse response = client.execute(options);
        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals("true", response.getFirstHeader(Cors.ACCESS_CONTROL_ALLOW_CREDENTIALS).getValue());
        assertEquals("DELETE, POST, GET, PUT", response.getFirstHeader(Cors.ACCESS_CONTROL_ALLOW_METHODS).getValue());
        assertEquals("http://test", response.getFirstHeader(Cors.ACCESS_CONTROL_ALLOW_ORIGIN).getValue());
        assertEquals("3600", response.getFirstHeader(Cors.ACCESS_CONTROL_MAX_AGE).getValue());
        assertTrue(response.getFirstHeader(Cors.ACCESS_CONTROL_ALLOW_HEADERS).getValue().contains("Authorization"));
        assertTrue(response.getFirstHeader(Cors.ACCESS_CONTROL_ALLOW_HEADERS).getValue().contains("Content-Type"));
    }

}
