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

public class DatabaseConfigBuilder {

    DatabaseConfiguration rep;

    private DatabaseConfigBuilder(DatabaseConfiguration rep) {
        this.rep = rep;
    }

    public static DatabaseConfigBuilder create() {
        DatabaseConfiguration rep = new DatabaseConfiguration();
        return new DatabaseConfigBuilder(rep);
    }

    public DatabaseConfigBuilder initScript(String initScript) {
        rep.setInitScript(initScript);
        return this;
    }

    public DatabaseConfigBuilder database(String database) {
        rep.setDatabase(database);
        return this;
    }

    public DatabaseConfigBuilder preventReuse(boolean preventReuse) {
        rep.setPreventReuse(preventReuse);
        return this;
    }

    public DatabaseConfiguration build() {
        return rep;
    }
}
