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
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

class MSSQLServerTestDatabase extends AbstractContainerTestDatabase {

    private static final Logger LOGGER = Logger.getLogger(MSSQLServerTestDatabase.class);

    public static final String NAME = "mssql";

    @SuppressWarnings("resource")
    @Override
    public JdbcDatabaseContainer<?> createContainer() {
        return new MSSQLServerContainer<>(DockerImageName.parse(ContainerImages.getContainerImageName(NAME))).withPassword(getPassword()).withEnv("MSSQL_PID", "Express").acceptLicense();
    }

    @Override
    public void withDatabaseAndUser(String database, String username, String password) {
        // MSSQLServerContainer does not support withUsername and withDatabase
    }

    @Override
    public String getDatabaseVendor() {
        return NAME;
    }

    @Override
    public String getUsername() {
        return "sa";
    }

    @Override
    public String getPassword() {
        return "vEry$tron9Pwd";
    }

    @Override
    public String getJdbcUrl(boolean internal) {
        return super.getJdbcUrl(internal) + ";integratedSecurity=false;encrypt=false;trustServerCertificate=true;sendStringParametersAsUnicode=false;";
    }

    @Override
    public List<String> getPostStartCommand() {
        return List.of("/opt/mssql-tools18/bin/sqlcmd", "-U", "sa", "-P", getPassword(), "-No", "-Q", "CREATE DATABASE " + getDatabase());
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }
}
