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
package org.keycloak.testsuite.arquillian;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.UndertowMessages;
import io.undertow.server.handlers.resource.Resource;
import io.undertow.server.handlers.resource.ResourceChangeListener;
import io.undertow.server.handlers.resource.ResourceManager;
import io.undertow.server.handlers.resource.URLResource;
import org.jboss.logging.Logger;

import java.net.URL;

public class TestClassProvider {

    private static final Logger LOGGER = Logger.getLogger(TestClassProvider.class);

    public static final String[] PERMITTED_PACKAGES = new String[] {
            "/org/keycloak/testsuite",
            "/org/junit",
            "/org/hamcrest",
            "/org/keycloak/admin/client",
            "/org/jboss/resteasy/client",
            "/org/jboss/arquillian",
            "/org/jboss/shrinkwrap",
            "/org/jboss/jandex",
            "/org/openqa/selenium",
            "/com/webauthn4j",
            "/com/fasterxml/jackson/dataformat/cbor",
            "/org/slf4j",
            "/org/apache"
    };

    private Undertow server;

    public void start() {
        server = Undertow.builder()
                .addHttpListener(8500, "localhost")
                .setHandler(Handlers.resource(new ClassPathResourceManager()))
                .build();
        server.start();

        LOGGER.infov("Started test class provider on http://localhost:8500");
    }

    public void stop() {
        server.stop();
    }

    public static class ClassPathResourceManager implements ResourceManager {

        @Override
        public Resource getResource(String className) {
            LOGGER.infov("Request: {0}", className);

            URL resource = isPermittedPackage(className) ? TestClassProvider.class.getResource(className) : null;
            return resource != null ? new URLResource(resource, className) : null;
        }

        private boolean isPermittedPackage(String className) {
            for (String p : PERMITTED_PACKAGES) {
                if (className.startsWith(p)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean isResourceChangeListenerSupported() {
            return false;
        }

        @Override
        public void registerResourceChangeListener(ResourceChangeListener listener) {
            throw UndertowMessages.MESSAGES.resourceChangeListenerNotSupported();
        }

        @Override
        public void removeResourceChangeListener(ResourceChangeListener listener) {
            throw UndertowMessages.MESSAGES.resourceChangeListenerNotSupported();
        }

        @Override
        public void close() {
        }
    }

}
