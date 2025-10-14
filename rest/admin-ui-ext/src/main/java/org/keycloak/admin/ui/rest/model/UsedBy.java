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
package org.keycloak.admin.ui.rest.model;

import java.util.List;
import java.util.Objects;

public class UsedBy {
    public UsedBy(UsedByType type, List<String> values) {
        this.type = type;
        this.values = values;
    }

    public enum UsedByType {
        SPECIFIC_CLIENTS, SPECIFIC_PROVIDERS, DEFAULT
    }

    private UsedByType type;
    private List<String> values;

    public UsedByType getType() {
        return type;
    }

    public void setType(UsedByType type) {
        this.type = type;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UsedBy usedBy = (UsedBy) o;
        return type == usedBy.type && Objects.equals(values, usedBy.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, values);
    }
}
