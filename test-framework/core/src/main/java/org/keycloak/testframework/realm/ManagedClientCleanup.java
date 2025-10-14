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

import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.representations.idm.ClientRepresentation;

import java.util.LinkedList;
import java.util.List;

public class ManagedClientCleanup {

    private final List<ClientCleanup> cleanupTasks = new LinkedList<>();

    public ManagedClientCleanup add(ClientCleanup clientCleanup) {
        this.cleanupTasks.add(clientCleanup);
        return this;
    }

    void resetToOriginalRepresentation(ClientRepresentation rep) {
        if (cleanupTasks.stream().noneMatch(c -> c instanceof ResetClient)) {
            ClientRepresentation clone = RepresentationUtils.clone(rep);
            cleanupTasks.add(new ResetClient(clone));
        }
    }

    ClientRepresentation getOriginalRepresentation() {
        ResetClient clientCleanup = (ResetClient) cleanupTasks.stream().filter(c -> c instanceof ResetClient).findFirst().orElse(null);
        return clientCleanup != null ? clientCleanup.rep() : null;
    }

    void runCleanupTasks(ClientResource client) {
        cleanupTasks.forEach(t -> t.cleanup(client));
        cleanupTasks.clear();
    }

    public interface ClientCleanup {

        void cleanup(ClientResource client);

    }

    private record ResetClient(ClientRepresentation rep) implements ClientCleanup {

        @Override
        public void cleanup(ClientResource client) {
            client.update(rep);
        }

    }

}
