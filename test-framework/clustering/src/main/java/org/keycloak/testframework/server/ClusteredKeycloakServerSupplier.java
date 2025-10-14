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

public class ClusteredKeycloakServerSupplier extends AbstractKeycloakServerSupplier {

    private static final Logger LOGGER = Logger.getLogger(ClusteredKeycloakServerSupplier.class);

    @ConfigProperty(name = "numContainer", defaultValue = "2")
    int numContainers = 2;

    @ConfigProperty(name = "images", defaultValue = ClusteredKeycloakServer.SNAPSHOT_IMAGE)
    String images = ClusteredKeycloakServer.SNAPSHOT_IMAGE;

    @Override
    public KeycloakServer getServer() {
        return new ClusteredKeycloakServer(numContainers, images);
    }

    @Override
    public boolean requiresDatabase() {
        return true;
    }

    @Override
    public String getAlias() {
        return "cluster";
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }
}
