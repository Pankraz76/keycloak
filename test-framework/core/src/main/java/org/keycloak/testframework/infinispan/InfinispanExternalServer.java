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
package org.keycloak.testframework.infinispan;

import java.util.Map;

import org.infinispan.server.test.core.InfinispanContainer;
import org.jboss.logging.Logger;
import org.keycloak.testframework.logging.JBossLogConsumer;
import org.keycloak.testframework.util.ContainerImages;

public class InfinispanExternalServer extends InfinispanContainer implements InfinispanServer {

    private static final String USER = "keycloak";
    private static final String PASSWORD = "Password1!";
    private static final String HOST = "127.0.0.1";

    public static InfinispanExternalServer create() {
        return new InfinispanExternalServer(ContainerImages.getContainerImageName("infinispan"));
    }

    @SuppressWarnings("resource")
    private InfinispanExternalServer(String dockerImageName) {
        super(dockerImageName);
        withUser(USER);
        withPassword(PASSWORD);
        withLogConsumer(new JBossLogConsumer(Logger.getLogger("managed.infinispan")));
        addFixedExposedPort(11222, 11222);
    }

    @Override
    public Map<String, String> serverConfig() {
        return Map.of(
                "cache-remote-host", HOST,
                "cache-remote-username", USER,
                "cache-remote-password", PASSWORD,
                "cache-remote-tls-enabled", "false",
                "spi-cache-embedded-default-site-name", "ispn",
                "spi-load-balancer-check-remote-poll-interval", "500",
                "spi-cache-remote-default-client-intelligence", "BASIC"
        );
    }
}
