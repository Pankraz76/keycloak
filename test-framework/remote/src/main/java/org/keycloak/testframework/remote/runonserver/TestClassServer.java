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
package org.keycloak.testframework.remote.runonserver;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class TestClassServer {

    public static final String CONTEXT_PATH = "/test-classes/";

    private final HttpServer httpServer;

    public static final Set<String> DEFAULT_PERMITTED_PACKAGES = Set.of(
        "org.keycloak.testframework",
        "org.junit",
        "org.opentest4j"
    );

    private final Set<String> permittedPackages;

    TestClassServer(HttpServer httpServer) {
        this.httpServer = httpServer;
        permittedPackages = new HashSet<>(DEFAULT_PERMITTED_PACKAGES);

        httpServer.createContext(CONTEXT_PATH, new TestClassPathHandler());
    }

    public void addPermittedPackages(Set<String> permittedPackages) {
        this.permittedPackages.addAll(permittedPackages);
    }

    public void close() {
        httpServer.removeContext(CONTEXT_PATH);
    }

    private class TestClassPathHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String resource = httpExchange.getRequestURI().getPath().substring(CONTEXT_PATH.length() - 1);

            Headers respHeaders = httpExchange.getResponseHeaders();
            respHeaders.set("Content-Type", "application/x-java-applet;charset=utf-8");

            if (!isPermittedPackage(resource) || !resource.endsWith(".class")) {
                httpExchange.sendResponseHeaders(403, 0);
            } else {
                try (InputStream resourceStream = TestClassServer.class.getResourceAsStream(resource)) {
                    if (resourceStream != null) {
                        byte[] bytes = resourceStream.readAllBytes();
                        httpExchange.sendResponseHeaders(200, bytes.length);
                        httpExchange.getResponseBody().write(bytes);
                    } else {
                        httpExchange.sendResponseHeaders(404, 0);
                    }
                }
            }
            httpExchange.close();
        }

        private boolean isPermittedPackage(String className) {
            String c = className.substring(1).replace('/', '.');
            for (String p : permittedPackages) {
                if (c.startsWith(p)) {
                    return true;
                }
            }
            return false;
        }
    }

}
