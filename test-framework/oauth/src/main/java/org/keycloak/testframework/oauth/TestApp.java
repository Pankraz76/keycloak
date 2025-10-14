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
package org.keycloak.testframework.oauth;

import com.sun.net.httpserver.HttpServer;

public class TestApp {

    public static final String OAUTH_CALLBACK_PATH = "/callback/oauth";
    public static final String K_ADMIN_PATH = "/k_admin";

    private final HttpServer httpServer;

    private final KcAdminInvocations kcAdminInvocations;

    private final String redirectionUri;
    private final String adminUri;

    public TestApp(HttpServer httpServer) {
        this.httpServer = httpServer;
        this.kcAdminInvocations = new KcAdminInvocations();

        try {
            httpServer.createContext(OAUTH_CALLBACK_PATH, new OAuthCallbackHandler());
            httpServer.createContext(K_ADMIN_PATH, new KcAdminCallbackHandler(kcAdminInvocations));

            redirectionUri = "http://127.0.0.1:" + httpServer.getAddress().getPort() + "/callback/oauth";
            adminUri = "http://127.0.0.1:" + httpServer.getAddress().getPort() + "/k_admin";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public String getRedirectionUri() {
        return redirectionUri;
    }

    public String getAdminUri() {
        return adminUri;
    }

    public KcAdminInvocations kcAdmin() {
        return kcAdminInvocations;
    }

    public void close() {
        httpServer.removeContext(OAUTH_CALLBACK_PATH);
        httpServer.removeContext(K_ADMIN_PATH);
    }

}
