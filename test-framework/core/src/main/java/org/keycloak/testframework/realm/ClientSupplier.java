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

import jakarta.ws.rs.NotFoundException;
import java.util.List;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.testframework.annotations.InjectClient;
import org.keycloak.testframework.injection.InstanceContext;
import org.keycloak.testframework.injection.RequestedInstance;
import org.keycloak.testframework.injection.Supplier;
import org.keycloak.testframework.injection.SupplierHelpers;
import org.keycloak.testframework.util.ApiUtil;

public class ClientSupplier implements Supplier<ManagedClient, InjectClient> {

    @Override
    public ManagedClient getValue(InstanceContext<ManagedClient, InjectClient> instanceContext) {
        ManagedRealm realm = instanceContext.getDependency(ManagedRealm.class, instanceContext.getAnnotation().realmRef());

        String attachTo = instanceContext.getAnnotation().attachTo();
        boolean managed = attachTo.isEmpty();

        ClientRepresentation clientRepresentation;

        if (managed) {
            ClientConfig config = SupplierHelpers.getInstance(instanceContext.getAnnotation().config());
            clientRepresentation = config.configure(ClientConfigBuilder.create()).build();

            if (clientRepresentation.getClientId() == null) {
                clientRepresentation.setClientId(SupplierHelpers.createName(instanceContext));
            }

            Response response = realm.admin().clients().create(clientRepresentation);
            if (Status.CONFLICT.equals(Status.fromStatusCode(response.getStatus()))) {
                throw new IllegalStateException("Client already exist with client id: " + clientRepresentation.getClientId());
            }
            clientRepresentation.setId(ApiUtil.handleCreatedResponse(response));
        } else {
            List<ClientRepresentation> clients = realm.admin().clients().findByClientId(attachTo);
            if (clients.isEmpty()) {
                throw new IllegalStateException("No client found with client id: " + attachTo);
            }
            clientRepresentation = clients.get(0);
        }

        instanceContext.addNote("managed", managed);

        ClientResource clientResource = realm.admin().clients().get(clientRepresentation.getId());
        return new ManagedClient(clientRepresentation, clientResource);
    }

    @Override
    public boolean compatible(InstanceContext<ManagedClient, InjectClient> a, RequestedInstance<ManagedClient, InjectClient> b) {
        return a.getAnnotation().config().equals(b.getAnnotation().config());
    }

    @Override
    public void close(InstanceContext<ManagedClient, InjectClient> instanceContext) {
        if (instanceContext.getNote("managed", Boolean.class)) {
            try {
                instanceContext.getValue().admin().remove();
            } catch (NotFoundException ex) {}
        }
    }

}
