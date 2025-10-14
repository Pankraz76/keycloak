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

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.keycloak.testframework.util.ContainerImages;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.tidb.TiDBContainer;
import org.testcontainers.utility.DockerImageName;

class TiDBTestDatabase extends AbstractContainerTestDatabase {

    private static final Logger LOGGER = Logger.getLogger(TiDBTestDatabase.class);

    public static final String NAME = "tidb";

    @Override
    public JdbcDatabaseContainer<?> createContainer() {
        return new TiDBContainer(DockerImageName.parse(ContainerImages.getContainerImageName(NAME)).asCompatibleSubstituteFor("pingcap/tidb")){
            @Override
            public TiDBContainer withDatabaseName(String databaseName) {
                if(StringUtils.equals(this.getDatabaseName(), databaseName)) {
                    return this;
                }
                throw new UnsupportedOperationException("The TiDB docker image does not currently support this");
            }

            @Override
            public TiDBContainer withUsername(String username) {
                if(StringUtils.equals(this.getUsername(), username)) {
                    return this;
                }
                throw new UnsupportedOperationException("The TiDB docker image does not currently support this");
            }

            @Override
            public TiDBContainer withPassword(String password) {
                if(StringUtils.equals(this.getPassword(), password)) {
                    return this;
                }
                throw new UnsupportedOperationException("The TiDB docker image does not currently support this");
            }
        }.withExposedPorts(4000);
    }

    @Override
    public String getDatabaseVendor() {
        return "tidb";
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }


    @Override
    public String getDatabase() {
        return "test";
    }

    @Override
    public String getUsername() {
        return "root";
    }

    @Override
    public String getPassword() {
        return "";
    }
}
