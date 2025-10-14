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
package org.keycloak.tests.admin.realm;

import jakarta.ws.rs.BadRequestException;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.models.Constants;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.testframework.annotations.KeycloakIntegrationTest;
import org.keycloak.tests.utils.Assert;

import static org.junit.jupiter.api.Assertions.fail;

@KeycloakIntegrationTest
public class RealmRemoveTest extends AbstractRealmTest {

    @Test
    public void removeRealm() {
        RealmRepresentation realmRep = managedRealm.admin().toRepresentation();
        adminClient.realm(managedRealm.getName()).remove();

        Assert.assertNames(adminClient.realms().findAll(), "master");

        adminClient.realms().create(realmRep);
    }

    @Test
    public void removeMasterRealm() {
        // any attempt to remove the master realm should fail.
        try {
            adminClient.realm("master").remove();
            fail("It should not be possible to remove the master realm");
        } catch(BadRequestException ignored) {
        }
    }

    @Test
    public void loginAfterRemoveRealm() {
        RealmRepresentation realmRep = managedRealm.admin().toRepresentation();
        adminClient.realm(managedRealm.getName()).remove();

        try (Keycloak client = adminClientFactory.create().realm("master")
                .username("admin").password("admin").clientId(Constants.ADMIN_CLI_CLIENT_ID).build()) {
            client.serverInfo().getInfo();
        }

        adminClient.realms().create(realmRep);
    }
}
