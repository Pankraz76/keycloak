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
package org.keycloak.authorization.store.syncronization;

import org.keycloak.authorization.fgap.AdminPermissionsSchema;
import org.keycloak.authorization.AuthorizationProvider;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.models.RoleContainerModel.RoleRemovedEvent;
import org.keycloak.provider.ProviderFactory;

public class RoleSynchronizer implements Synchronizer<RoleRemovedEvent> {

    @Override
    public void synchronize(RoleRemovedEvent event, KeycloakSessionFactory factory) {
        ProviderFactory<AuthorizationProvider> providerFactory = factory.getProviderFactory(AuthorizationProvider.class);
        AuthorizationProvider authorizationProvider = providerFactory.create(event.getKeycloakSession());

        AdminPermissionsSchema.SCHEMA.removeResourceObject(authorizationProvider, event);
    }
}
