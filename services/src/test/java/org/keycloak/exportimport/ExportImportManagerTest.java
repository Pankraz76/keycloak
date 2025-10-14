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
package org.keycloak.exportimport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.After;
import org.junit.Test;
import org.keycloak.models.KeycloakSession;
import org.keycloak.provider.Provider;
import org.keycloak.services.DefaultKeycloakContext;
import org.keycloak.services.DefaultKeycloakSession;
import org.keycloak.services.DefaultKeycloakSessionFactory;

public class ExportImportManagerTest {

    @After
    public void reset() {
        ExportImportConfig.reset();
    }

    @Test
    public void testImportOnStartup() {
        ExportImportConfig.setDir("/some/dir");
        new ExportImportManager(new DefaultKeycloakSession(new DefaultKeycloakSessionFactory() {

            @Override
            public KeycloakSession create() {
                return null;
            }
        }) {

            @Override
            protected DefaultKeycloakContext createKeycloakContext(KeycloakSession session) {
                return null;
            }

        });
        assertEquals(ExportImportConfig.ACTION_IMPORT, ExportImportConfig.getAction());
        assertEquals(Strategy.IGNORE_EXISTING.toString(), ExportImportConfig.getStrategy());
        assertTrue(ExportImportConfig.isReplacePlaceholders());
    }

    @Test
    public void testImport() {
        ExportImportConfig.setAction(ExportImportConfig.ACTION_IMPORT);
        new ExportImportManager(new DefaultKeycloakSession(null) {

            @Override
            protected DefaultKeycloakContext createKeycloakContext(KeycloakSession session) {
                return null;
            }

            @Override
            public <T extends Provider> T getProvider(Class<T> clazz, String id) {
                return (T) new ImportProvider() {

                    @Override
                    public void close() {

                    }

                    @Override
                    public boolean isMasterRealmExported() throws IOException {
                        return false;
                    }

                    @Override
                    public void importModel() throws IOException {

                    }
                };
            }

        });
        assertEquals(ExportImportConfig.ACTION_IMPORT, ExportImportConfig.getAction());
        assertNull(ExportImportConfig.getStrategy());
        // we're now setting this in the Quarkus logic, it's left as false in the ExportImportManager
        // for arquillian, or other legacy usage
        assertFalse(ExportImportConfig.isReplacePlaceholders());
    }

}
