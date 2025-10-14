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
package org.keycloak.testsuite.arquillian.containers;

import java.util.List;
import org.jboss.arquillian.container.spi.client.container.LifecycleException;
import org.jboss.logging.Logger;
import org.keycloak.Keycloak;
import org.keycloak.common.Version;

/**
 * @author mhajas
 */
public class KeycloakQuarkusEmbeddedDeployableContainer extends AbstractQuarkusDeployableContainer {

    private static final Logger log = Logger.getLogger(KeycloakQuarkusEmbeddedDeployableContainer.class);
    
    private static final String KEYCLOAK_VERSION = Version.VERSION;

    private Keycloak keycloak;

    @Override
    public void start() throws LifecycleException {
        try {
            List<String> args = getArgs();
            log.debugf("Quarkus process arguments: %s", args);
            keycloak = configure().start(args);
            waitForReadiness();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() throws LifecycleException {
        if (keycloak != null) {
            try {
                keycloak.stop();
            } catch (Exception e) {
                throw new RuntimeException("Failed to stop the server", e);
            } finally {
                keycloak = null;
            }
        }
    }

    private Keycloak.Builder configure() {
        return Keycloak.builder()
                .setHomeDir(configuration.getProvidersPath())
                .setVersion(KEYCLOAK_VERSION)
                .addDependency("org.keycloak.testsuite", "integration-arquillian-testsuite-providers", KEYCLOAK_VERSION)
                .addDependency("org.keycloak.testsuite", "integration-arquillian-testsuite-providers-deployment", KEYCLOAK_VERSION)
                .addDependency("org.keycloak.testsuite", "integration-arquillian-tests-base", KEYCLOAK_VERSION)
                .addDependency("org.keycloak.testsuite", "integration-arquillian-tests-base", KEYCLOAK_VERSION, "tests");
    }

    @Override
    protected List<String> configureArgs(List<String> args) {
        System.setProperty("quarkus.http.test-port", String.valueOf(configuration.getBindHttpPort()));
        System.setProperty("quarkus.http.test-ssl-port", String.valueOf(configuration.getBindHttpsPort()));
        return args;
    }

    @Override
    protected void checkLiveness() {
        // no-op, Keycloak would throw an exception in the test JVM if something went wrong
    }
}
