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
package org.keycloak.testframework.server;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

public class DistributionKeycloakServerSupplier extends AbstractKeycloakServerSupplier {

    private static final Logger LOGGER = Logger.getLogger(DistributionKeycloakServerSupplier.class);

    @ConfigProperty(name = "debug", defaultValue = "false")
    boolean debug = false;

    @Override
    public KeycloakServer getServer() {
        return new DistributionKeycloakServer(debug);
    }

    @Override
    public boolean requiresDatabase() {
        return true;
    }

    @Override
    public String getAlias() {
        return "distribution";
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }
}
