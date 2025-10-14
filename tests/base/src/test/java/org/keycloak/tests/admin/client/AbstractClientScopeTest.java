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
package org.keycloak.tests.admin.client;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.keycloak.admin.client.resource.ClientScopesResource;
import org.keycloak.events.admin.OperationType;
import org.keycloak.events.admin.ResourceType;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.ClientScopeRepresentation;
import org.keycloak.testframework.annotations.InjectAdminEvents;
import org.keycloak.testframework.annotations.InjectRealm;
import org.keycloak.testframework.annotations.KeycloakIntegrationTest;
import org.keycloak.testframework.events.AdminEventAssertion;
import org.keycloak.testframework.events.AdminEvents;
import org.keycloak.testframework.realm.ManagedRealm;
import org.keycloak.tests.utils.admin.AdminEventPaths;
import org.keycloak.tests.utils.admin.ApiUtil;
import org.keycloak.util.JsonSerialization;

import java.io.IOException;
import java.util.Map;

@KeycloakIntegrationTest
public class AbstractClientScopeTest {

    @InjectRealm
    ManagedRealm managedRealm;

    @InjectAdminEvents
    AdminEvents adminEvents;

    void handleExpectedCreateFailure(ClientScopeRepresentation scopeRep, int expectedErrorCode, String expectedErrorMessage) {
        try (Response resp = clientScopes().create(scopeRep)) {
            Assertions.assertEquals(expectedErrorCode, resp.getStatus());
            String respBody = resp.readEntity(String.class);
            Map<String, String> responseJson;
            try {
                responseJson = JsonSerialization.readValue(respBody, Map.class);
                Assertions.assertEquals(expectedErrorMessage, responseJson.get("errorMessage"));
            } catch (IOException e) {
                Assertions.fail("Failed to extract the errorMessage from a CreateScope Response");
            }
        }
    }

    ClientScopesResource clientScopes() {
        return managedRealm.admin().clientScopes();
    }

    String createClientScope(ClientScopeRepresentation clientScopeRep) {
        Response resp = clientScopes().create(clientScopeRep);
        final String clientScopeId = ApiUtil.getCreatedId(resp);

        AdminEventAssertion.assertEvent(adminEvents.poll(), OperationType.CREATE, AdminEventPaths.clientScopeResourcePath(clientScopeId), clientScopeRep, ResourceType.CLIENT_SCOPE);

        return clientScopeId;
    }

    String createClientScopeWithCleanup(ClientScopeRepresentation clientScopeRep) {
        String clientScopeId = createClientScope(clientScopeRep);
        managedRealm.cleanup().add(r -> r.clientScopes().get(clientScopeId).remove());
        return clientScopeId;
    }

    String createClientWithCleanup(ClientRepresentation clientRep) {
        Response resp = managedRealm.admin().clients().create(clientRep);
        final String clientUuid = ApiUtil.getCreatedId(resp);
        managedRealm.cleanup().add(r -> r.clients().get(clientUuid).remove());

        AdminEventAssertion.assertEvent(adminEvents.poll(), OperationType.CREATE, AdminEventPaths.clientResourcePath(clientUuid), clientRep, ResourceType.CLIENT);
        return clientUuid;
    }

}
