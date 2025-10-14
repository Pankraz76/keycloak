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

public final class DatabaseConfiguration {
    private String initScript;
    private String database;
    private boolean preventReuse;

    public String getInitScript() {
        return initScript;
    }

    public void setInitScript(String initScript) {
        this.initScript = initScript;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public boolean isPreventReuse() {
        return preventReuse;
    }

    public void setPreventReuse(boolean preventReuse) {
        this.preventReuse = preventReuse;
    }
}
