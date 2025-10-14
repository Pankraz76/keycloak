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
package org.keycloak.compatibility;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.keycloak.Config;


public abstract class AbstractCompatibilityMetadataProvider implements CompatibilityMetadataProvider {

    final String spi;
    final Config.Scope config;

    public AbstractCompatibilityMetadataProvider(String spi, String providerId) {
        this.spi = spi;
        this.config = Config.scope(spi, providerId);
    }

    abstract protected boolean isEnabled(Config.Scope scope);

    @Override
    public Map<String, String> metadata() {
        if (!isEnabled(config))
            return Map.of();

        Map<String, String> metadata = new HashMap<>(customMeta());
        configKeys().forEach(key -> {
            String value = config.get(key);
            if (value != null)
                metadata.put(key, value);
        });
        return metadata;
    }

    @Override
    public String getId() {
        return spi;
    }

    protected Map<String, String> customMeta() {
        return Map.of();
    }

    protected Stream<String> configKeys() {
        return Stream.of();
    }
}
