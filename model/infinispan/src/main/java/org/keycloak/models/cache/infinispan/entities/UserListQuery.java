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

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class UserListQuery extends AbstractRevisioned implements UserQuery {
    private final Set<String> users;
    private final String realm;

    public UserListQuery(Long revisioned, String id, RealmModel realm, Set<String> users) {
        super(revisioned, id);
        this.realm = realm.getId();
        this.users = users;
    }

    public UserListQuery(Long revisioned, String id, RealmModel realm, String user) {
        super(revisioned, id);
        this.realm = realm.getId();
        this.users = new HashSet<>();
        this.users.add(user);
    }

    @Override
    public Set<String> getUsers() {
        return users;
    }

    @Override
    public String getRealm() {
        return realm;
    }

    @Override
    public String toString() {
        return "UserListQuery{" +
                "id='" + getId() + "'" +
                "realm='" + realm + '\'' +
                '}';
    }
}
