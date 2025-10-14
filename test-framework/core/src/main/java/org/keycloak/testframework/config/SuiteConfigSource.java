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
package org.keycloak.testframework.config;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SuiteConfigSource implements ConfigSource {

    private static final Map<String, String> SUITE_CONFIG = new HashMap<>();

    public static void set(String key, String value) {
        SUITE_CONFIG.put(key, value);
    }

    public static void clear() {
        SUITE_CONFIG.clear();
    }

    @Override
    public Set<String> getPropertyNames() {
        return SUITE_CONFIG.keySet();
    }

    @Override
    public String getValue(String s) {
        return SUITE_CONFIG.get(s);
    }

    @Override
    public String getName() {
        return "SuiteConfigSource";
    }

    @Override
    public int getOrdinal() {
        return 270;
    }
}
