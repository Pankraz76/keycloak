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
package org.keycloak.quarkus.runtime.configuration.compatibility;

import static org.keycloak.quarkus.runtime.configuration.Configuration.getConfigValue;
import static org.keycloak.quarkus.runtime.configuration.Configuration.getOptionalKcValue;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.keycloak.compatibility.CompatibilityMetadataProvider;
import org.keycloak.config.DatabaseOptions;
import org.keycloak.config.Option;

import io.smallrye.config.ConfigValue;

public class DatabaseCompatibilityMetadataProvider implements CompatibilityMetadataProvider {

    public static final String ID = "database";

    @Override
    public Map<String, String> metadata() {
        Map<String, String> metadata = new HashMap<>();
        metadata.put(DatabaseOptions.DB.getKey(), getConfigValue(DatabaseOptions.DB).getValue());
        addOptional(DatabaseOptions.DB_SCHEMA, metadata);

        // Only track DB_URL_* properties if the user has not explicitly configured a DB_URL
        ConfigValue dbUrl = getConfigValue(DatabaseOptions.DB_URL);
        if (!dbUrl.getValue().equals(dbUrl.getRawValue())) {
            addOptional(DatabaseOptions.DB_URL_HOST, metadata);
            addOptional(DatabaseOptions.DB_URL_PORT, metadata);
            addOptional(DatabaseOptions.DB_URL_DATABASE, metadata);
        }
        return metadata;
    }

    void addOptional(Option<?> option, Map<String, String> metadata) {
        Optional<String> optional = getOptionalKcValue(option.getKey());
        optional.ifPresent(opt -> metadata.put(option.getKey(), opt));
    }

    @Override
    public String getId() {
        return ID;
    }
}
