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
package org.keycloak.tests.admin.partialimport;

import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.resource.IdentityProviderResource;
import org.keycloak.partialimport.PartialImportResult;
import org.keycloak.partialimport.PartialImportResults;
import org.keycloak.partialimport.ResourceType;
import org.keycloak.representations.idm.IdentityProviderMapperRepresentation;
import org.keycloak.representations.idm.IdentityProviderRepresentation;
import org.keycloak.testframework.annotations.KeycloakIntegrationTest;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@KeycloakIntegrationTest(config = AbstractPartialImportTest.PartialImportServerConfig.class)
public class PartialImportProvidersTest extends AbstractPartialImportTest {

    @Test
    public void testAddProviders() {
        setFail();
        addProviders();

        PartialImportResults results = doImport();
        assertEquals(IDP_ALIASES.length, results.getAdded());

        for (PartialImportResult result : results.getResults()) {
            String id = result.getId();
            IdentityProviderResource idpRsc = managedRealm.admin().identityProviders().get(id);
            IdentityProviderRepresentation idp = idpRsc.toRepresentation();
            Map<String, String> config = idp.getConfig();
            assertTrue(Arrays.asList(IDP_ALIASES).contains(config.get("clientId")));
        }
    }

    @Test
    public void testAddProviderMappers() {
        setFail();
        addProviderMappers();

        PartialImportResults results = doImport();
        assertEquals(IDP_ALIASES.length*2, results.getAdded());

        for (PartialImportResult result : results.getResults()) {
            if (ResourceType.IDP.equals(result.getResourceType())) {
                String id = result.getId();
                IdentityProviderResource idpRsc = managedRealm.admin().identityProviders().get(id);
                IdentityProviderMapperRepresentation idpMap = idpRsc.getMappers().get(0);
                String alias = idpMap.getIdentityProviderAlias();
                assertTrue(Arrays.asList(IDP_ALIASES).contains(alias));
                assertEquals(alias + "_mapper", idpMap.getName());
                assertEquals("keycloak-oidc-role-to-role-idp-mapper", idpMap.getIdentityProviderMapper());
                assertEquals("IDP.TEST_ROLE", idpMap.getConfig().get("external.role"));
                assertEquals("FORCE", idpMap.getConfig().get("syncMode"));
                assertEquals("TEST_ROLE", idpMap.getConfig().get("role"));
            }
        }
    }

    @Test
    public void testAddProvidersFail() {
        addProviders();
        testFail();
    }

    @Test
    public void testAddProviderMappersFail() {
        addProviderMappers();
        testFail();
    }

    @Test
    public void testAddProvidersSkip() {
        addProviders();
        testSkip();
    }

    @Test
    public void testAddProviderMappersSkip() {
        addProviderMappers();
        testSkip(NUM_ENTITIES*2);
    }

    @Test
    public void testAddProvidersOverwrite() {
        addProviders();
        testOverwrite();
    }

    @Test
    public void testAddProviderMappersOverwrite() {
        addProviderMappers();
        testOverwrite(NUM_ENTITIES*2);
    }
}
