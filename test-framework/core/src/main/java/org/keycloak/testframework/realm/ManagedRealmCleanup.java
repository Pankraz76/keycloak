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

import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.RealmRepresentation;

import java.util.LinkedList;
import java.util.List;

public class ManagedRealmCleanup {

    private final List<RealmCleanup> cleanupTasks = new LinkedList<>();

    public ManagedRealmCleanup add(RealmCleanup realmCleanup) {
        this.cleanupTasks.add(realmCleanup);
        return this;
    }

    public ManagedRealmCleanup deleteUsers() {
        return add(r -> r.users().list().forEach(u -> r.users().delete(u.getId()).close()));
    }

    void resetToOriginalRepresentation(RealmRepresentation rep) {
        if (cleanupTasks.stream().noneMatch(c -> c instanceof ResetRealm)) {
            RealmRepresentation clone = RepresentationUtils.clone(rep);
            cleanupTasks.add(new ResetRealm(clone));
        }
    }

    void runCleanupTasks(RealmResource realm) {
        cleanupTasks.forEach(t -> t.cleanup(realm));
        cleanupTasks.clear();
    }

    public interface RealmCleanup {

        void cleanup(RealmResource realm);

    }

    private record ResetRealm(RealmRepresentation rep) implements RealmCleanup {

        @Override
        public void cleanup(RealmResource realm) {
            realm.update(rep);
        }

    }

}
