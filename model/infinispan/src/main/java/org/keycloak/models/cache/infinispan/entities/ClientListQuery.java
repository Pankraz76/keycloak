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
public class ClientListQuery extends AbstractRevisioned implements ClientQuery {
    private final Set<String> clients;
    private final String realm;

    public ClientListQuery(Long revisioned, String id, RealmModel realm, Set<String> clients) {
        super(revisioned, id);
        this.realm = realm.getId();
        this.clients = clients;
    }

    public ClientListQuery(Long revisioned, String id, RealmModel realm, String client) {
        super(revisioned, id);
        this.realm = realm.getId();
        this.clients = new HashSet<>();
        this.clients.add(client);
    }

    @Override
    public Set<String> getClients() {
        return clients;
    }

    @Override
    public String getRealm() {
        return realm;
    }

    @Override
    public String toString() {
        return "ClientListQuery{" +
                "id='" + getId() + "'" +
                "realm='" + realm + '\'' +
                '}';
    }
}
