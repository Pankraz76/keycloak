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
package org.keycloak.models.cache.infinispan.entities;

import org.keycloak.models.RealmModel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class GroupListQuery extends AbstractRevisioned implements GroupQuery {
    private final String realm;
    private final Map<String, Set<String>> searchKeys;

    public GroupListQuery(Long revisioned, String id, RealmModel realm, String searchKey, Set<String> result) {
        super(revisioned, id);
        this.realm = realm.getId();
        this.searchKeys = new HashMap<>();
        this.searchKeys.put(searchKey, result);
    }

    public GroupListQuery(Long revisioned, String id, RealmModel realm, String searchKey, Set<String> result, GroupListQuery previous) {
        super(revisioned, id);
        this.realm = realm.getId();
        this.searchKeys = new HashMap<>();
        this.searchKeys.putAll(previous.searchKeys);
        this.searchKeys.put(searchKey, result);
    }

    public GroupListQuery(Long revisioned, String id, RealmModel realm, Set<String> ids) {
        super(revisioned, id);
        this.realm = realm.getId();
        this.searchKeys = new HashMap<>();
        this.searchKeys.put(id, ids);
    }

    @Override
    public Set<String> getGroups() {
        Collection<Set<String>> values = searchKeys.values();

        if (values.isEmpty()) {
            return Set.of();
        }

        return values.stream().flatMap(Set::stream).collect(Collectors.toSet());
    }

    public Set<String> getGroups(String searchKey) {
        return searchKeys.get(searchKey);
    }

    @Override
    public String getRealm() {
        return realm;
    }

    @Override
    public String toString() {
        return "GroupListQuery{" +
                "id='" + getId() + "'" +
                "realm='" + realm + '\'' +
                '}';
    }
}
