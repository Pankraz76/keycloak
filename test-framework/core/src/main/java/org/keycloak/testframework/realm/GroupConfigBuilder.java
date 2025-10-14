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
package org.keycloak.testframework.realm;

import org.keycloak.representations.idm.GroupRepresentation;

import java.util.List;
import java.util.Map;

public class GroupConfigBuilder {
    private final GroupRepresentation rep;

    private GroupConfigBuilder(GroupRepresentation rep) {
        this.rep = rep;
    }

    public static GroupConfigBuilder create() {
        GroupRepresentation rep = new GroupRepresentation();
        return new GroupConfigBuilder(rep);
    }

    public static GroupConfigBuilder update(GroupRepresentation rep) {
        return new GroupConfigBuilder(rep);
    }

    public GroupConfigBuilder name(String name) {
        rep.setName(name);
        return this;
    }

    public GroupConfigBuilder path(String path) {
        rep.setPath(path);
        return this;
    }

    public GroupConfigBuilder realmRoles(String... realmRoles) {
        rep.setRealmRoles(Collections.combine(rep.getRealmRoles(), realmRoles));
        return this;
    }

    public GroupConfigBuilder clientRoles(String client, String... clientRoles) {
        rep.setClientRoles(Collections.combine(rep.getClientRoles(), client, clientRoles));
        return this;
    }

    public GroupConfigBuilder attribute(String key, String... value) {
        rep.setAttributes(Collections.combine(rep.getAttributes(), key, value));
        return this;
    }

    public GroupConfigBuilder setAttributes(Map<String, List<String>> attributes) {
        rep.setAttributes(attributes);
        return this;
    }

    public GroupConfigBuilder subGroups(GroupRepresentation... subGroups) {
        rep.setSubGroups(Collections.combine(rep.getSubGroups(), subGroups));
        return this;
    }

    public GroupRepresentation build() {
        return rep;
    }
}
