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
package org.keycloak.testframework.database;

import org.jboss.logging.Logger;
import org.keycloak.testframework.util.ContainerImages;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

class MySQLTestDatabase extends AbstractContainerTestDatabase {

    private static final Logger LOGGER = Logger.getLogger(MySQLTestDatabase.class);

    public static final String NAME = "mysql";

    @Override
    public JdbcDatabaseContainer<?> createContainer() {
        return new MySQLContainer<>(DockerImageName.parse(ContainerImages.getContainerImageName(NAME)).asCompatibleSubstituteFor(NAME));
    }

    @Override
    public String getDatabaseVendor() {
        return NAME;
    }

    @Override
    public String getJdbcUrl(boolean internal) {
        return super.getJdbcUrl(internal) + "?allowPublicKeyRetrieval=true";
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }
}
