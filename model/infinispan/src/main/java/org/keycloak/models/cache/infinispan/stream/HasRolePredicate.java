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
package org.keycloak.models.cache.infinispan.stream;

import org.infinispan.protostream.annotations.ProtoField;
import org.infinispan.protostream.annotations.ProtoTypeId;
import org.keycloak.models.cache.infinispan.entities.CachedClient;
import org.keycloak.models.cache.infinispan.entities.CachedClientScope;
import org.keycloak.models.cache.infinispan.entities.CachedGroup;
import org.keycloak.models.cache.infinispan.entities.CachedRole;
import org.keycloak.models.cache.infinispan.entities.Revisioned;
import org.keycloak.models.cache.infinispan.entities.RoleQuery;
import org.keycloak.marshalling.Marshalling;

import java.util.Map;
import java.util.function.Predicate;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
@ProtoTypeId(Marshalling.HAS_ROLE_PREDICATE)
public class HasRolePredicate implements Predicate<Map.Entry<String, Revisioned>> {
    private String role;

    public static HasRolePredicate create() {
        return new HasRolePredicate();
    }

    public HasRolePredicate role(String role) {
        this.role = role;
        return this;
    }

    @ProtoField(1)
    String getRole() {
        return role;
    }

    void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean test(Map.Entry<String, Revisioned> entry) {
        Object value = entry.getValue();
        return (value instanceof CachedRole cachedRole && cachedRole.getComposites().contains(role)) ||
                (value instanceof CachedGroup cachedGroup && cachedGroup.getRoleMappings(null, null).contains(role)) ||
                (value instanceof RoleQuery roleQuery && roleQuery.getRoles().contains(role)) ||
                (value instanceof CachedClient cachedClient && cachedClient.getScope().contains(role)) ||
                (value instanceof CachedClientScope cachedClientScope && cachedClientScope.getScope().contains(role));
    }

}
