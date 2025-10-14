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
import org.keycloak.testframework.injection.ManagedTestResource;

public class ManagedClient extends ManagedTestResource {

    private final ClientRepresentation createdRepresentation;
    private final ClientResource clientResource;

    private ManagedClientCleanup cleanup;

    public ManagedClient(ClientRepresentation createdRepresentation, ClientResource clientResource) {
        this.createdRepresentation = createdRepresentation;
        this.clientResource = clientResource;
    }

    public String getId() {
        return createdRepresentation.getId();
    }

    public String getClientId() {
        return createdRepresentation.getClientId();
    }

    public String getSecret() {
        return createdRepresentation.getSecret();
    }

    public ClientResource admin() {
        return clientResource;
    }

    public void updateWithCleanup(ManagedClient.ClientUpdate... updates) {
        ClientRepresentation rep = admin().toRepresentation();

        // TODO Admin v2 - Setting a field to `null` is ignored when updating the client (for example `adminUrl`), which
        // makes it impossible to reset to the original. For now we are just re-creating the client by marking it as dirty
        // cleanup().resetToOriginalRepresentation(rep);
        dirty();

        ClientConfigBuilder configBuilder = ClientConfigBuilder.update(rep);
        for (ManagedClient.ClientUpdate update : updates) {
            configBuilder = update.update(configBuilder);
        }

        ClientRepresentation updated = configBuilder.build();
        admin().update(updated);

        // TODO It's possible to delete attributes by setting their value to an empty string, but due to the above this
        // is not a complete solution to resetting to the original
        // ClientRepresentation original = cleanup().getOriginalRepresentation();
        // updated.getAttributes().keySet().stream().filter(k -> !original.getAttributes().containsKey(k)).forEach(k -> original.getAttributes().put(k, ""));
    }

    public ManagedClientCleanup cleanup() {
        if (cleanup == null) {
            cleanup = new ManagedClientCleanup();
        }
        return cleanup;
    }

    @Override
    public void runCleanup() {
        if (cleanup != null) {
            cleanup.runCleanupTasks(clientResource);
            cleanup = null;
        }
    }

    public interface ClientUpdate {

        ClientConfigBuilder update(ClientConfigBuilder client);

    }

}
