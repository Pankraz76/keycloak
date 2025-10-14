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
import org.keycloak.partialimport.PartialImportResults;
import org.keycloak.testframework.annotations.KeycloakIntegrationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@KeycloakIntegrationTest(config = AbstractPartialImportTest.PartialImportServerConfig.class)
public class PartialImportRealmTest extends AbstractPartialImportTest {

    private static final int NUM_RESOURCE_TYPES = 6;

    @Test
    public void testEverythingFail() {
        setFail();
        importEverything(false);
        PartialImportResults results = doImport(); // second import will fail because not allowed to skip or overwrite
        assertNotNull(results.getErrorMessage());
    }

    @Test
    public void testEverythingSkip() {
        setSkip();
        importEverything(false);
        PartialImportResults results = doImport();
        assertEquals(NUM_ENTITIES * NUM_RESOURCE_TYPES, results.getSkipped());
    }

    @Test
    public void testEverythingSkipWithServiceAccounts() {
        setSkip();
        importEverything(true);
        PartialImportResults results = doImport();
        assertEquals(NUM_ENTITIES * (NUM_RESOURCE_TYPES + 1), results.getSkipped());
    }

    @Test
    public void testEverythingOverwrite() {
        setOverwrite();
        importEverything(false);
        PartialImportResults results = doImport();
        assertEquals(NUM_ENTITIES * NUM_RESOURCE_TYPES, results.getOverwritten());
    }

    @Test
    public void testEverythingOverwriteWithServiceAccounts() {
        setOverwrite();
        importEverything(true);
        PartialImportResults results = doImport();
        assertEquals(NUM_ENTITIES * (NUM_RESOURCE_TYPES + 1), results.getOverwritten());
    }

    private void importEverything(boolean withServiceAccounts) {
        addUsers();
        addGroups();
        addClients(withServiceAccounts);
        addProviders();
        addRealmRoles();
        addClientRoles();

        PartialImportResults results = doImport();
        assertNull(results.getErrorMessage());
        if (withServiceAccounts) {
            assertEquals(NUM_ENTITIES * (NUM_RESOURCE_TYPES + 1), results.getAdded());
        } else {
            assertEquals(NUM_ENTITIES * NUM_RESOURCE_TYPES, results.getAdded());
        }
    }
}
