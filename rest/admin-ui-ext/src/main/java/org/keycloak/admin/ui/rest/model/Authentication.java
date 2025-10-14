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

import java.util.Objects;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

public class Authentication {

    @Schema(required = true)
    private String id;

    @Schema(required = true)
    private String alias;

    @Schema(required = true)
    private boolean builtIn;

    private UsedBy usedBy;

    private String description;

    public  UsedBy getUsedBy() {
        return usedBy;
    }

    public void setUsedBy( UsedBy usedBy) {
        this.usedBy = usedBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isBuiltIn() {
        return builtIn;
    }

    public void setBuiltIn(boolean builtIn) {
        this.builtIn = builtIn;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Authentication that = (Authentication) o;
        return builtIn == that.builtIn && Objects.equals(usedBy, that.usedBy) && Objects.equals(id, that.id) && Objects.equals(alias,
                that.alias) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usedBy, id, builtIn, alias, description);
    }

    @Override public String toString() {
        return "Authentication{" + "usedBy=" + usedBy + ", id='" + id + '\'' + ", buildIn=" + builtIn + ", alias='" + alias + '\'' + ", description='" + description + '\'' + '}';
    }
}
