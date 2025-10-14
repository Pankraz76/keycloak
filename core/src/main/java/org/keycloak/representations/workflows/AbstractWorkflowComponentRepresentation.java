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
package org.keycloak.representations.workflows;

import static org.keycloak.common.util.reflections.Reflections.isArrayType;
import static org.keycloak.representations.workflows.WorkflowConstants.CONFIG_WITH;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.keycloak.common.util.MultivaluedHashMap;
import org.keycloak.common.util.reflections.Reflections;

public abstract class AbstractWorkflowComponentRepresentation {

    private String id;
    private String uses;

    @JsonProperty(CONFIG_WITH)
    private MultivaluedHashMap<String, String> config;

    public AbstractWorkflowComponentRepresentation(String id, String uses, MultivaluedHashMap<String, String> config) {
        this.id = id;
        this.uses = uses;
        this.config = config;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUses() {
        return this.uses;
    }

    public void setUses(String uses) {
        this.uses = uses;
    }

    public MultivaluedHashMap<String, String> getConfig() {
        return config;
    }

    public void setConfig(MultivaluedHashMap<String, String> config) {
        if (this.config == null) {
            this.config = config;
        }
        this.config.putAll(config);
    }

    public void setConfig(String key, String value) {
        setConfig(key, Collections.singletonList(value));
    }

    @JsonAnySetter
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    public void setConfig(String key, List<String> values) {
        if (this.config == null) {
            this.config = new MultivaluedHashMap<>();
        }
        this.config.put(key, values);
    }

    protected <T> T getConfigValue(String key, Class<T> type) {
        if (config == null) {
            return null;
        }

        return Reflections.convertValueToType(config.getFirst(key), type);
    }

    protected List<String> getConfigValues(String key) {
        if (config == null) {
            return null;
        }

        try {
            return config.get(key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected <T> T getConfigValuesOrSingle(String key) {
        if (config == null) {
            return null;
        }

        List<String> values = config.get(key);

        if (values == null || values.isEmpty()) {
            return null;
        }

        if (values.size() == 1) {
            return (T) values.get(0);
        }

        return (T) values;
    }

    protected void setConfigValue(String key, Object... values) {
        if (values == null || values.length == 0) {
            return;
        }

        if (this.config == null) {
            this.config = new MultivaluedHashMap<>();
        }

        if (isArrayType(values.getClass())) {
            this.config.put(key, Arrays.stream(values).filter(Objects::nonNull).map(Object::toString).collect(Collectors.toList()));
        } else {
            this.config.putSingle(key, values[0].toString());
        }
    }

    protected void setConfigValue(String key, List<String> values) {
        if (this.config == null) {
            this.config = new MultivaluedHashMap<>();
        }
        this.config.put(key, values);
    }

    protected void addConfigValue(String key, String value) {
        if (this.config == null) {
            this.config = new MultivaluedHashMap<>();
        }

        this.config.add(key, value);
    }
}
