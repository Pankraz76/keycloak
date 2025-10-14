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

import java.util.Map;

public class DevFileDatabaseSupplier extends AbstractDatabaseSupplier {

    @Override
    public String getAlias() {
        return "dev-file";
    }

    @Override
    TestDatabase getTestDatabase() {
        return new DevFileTestDatabase();
    }

    private static class DevFileTestDatabase implements TestDatabase {

        @Override
        public void start(DatabaseConfiguration config) {
            if (config.getInitScript() != null)
                throw new IllegalArgumentException("init script not supported, configure h2 properties via --db-url-properties");
        }

        @Override
        public void stop() {
            // TODO Should we clean-up H2 database here?
        }

        @Override
        public Map<String, String> serverConfig() {
            return Map.of("db", "dev-file");
        }
    }

}
