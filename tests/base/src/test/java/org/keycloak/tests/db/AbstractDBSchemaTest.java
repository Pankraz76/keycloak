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
package org.keycloak.tests.db;

import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.testframework.annotations.InjectClient;
import org.keycloak.testframework.annotations.KeycloakIntegrationTest;
import org.keycloak.testframework.database.TestDatabase;
import org.keycloak.testframework.injection.Extensions;
import org.keycloak.testframework.realm.ManagedClient;
import org.keycloak.testframework.realm.RoleConfigBuilder;

@KeycloakIntegrationTest
public abstract class AbstractDBSchemaTest {

    @InjectClient
    ManagedClient managedClient;

    protected static String dbType() {
        return Extensions.getInstance().findSupplierByType(TestDatabase.class).getAlias();
    }

    @Test
    public void testCaseSensitiveSchema() {
        RoleRepresentation role1 = RoleConfigBuilder.create()
                .name("role1")
                .description("role1-description")
                .singleAttribute("role1-attr-key", "role1-attr-val")
                .build();
        RolesResource roles = managedClient.admin().roles();
        roles.create(role1);
        roles.deleteRole(role1.getName());
    }
}
