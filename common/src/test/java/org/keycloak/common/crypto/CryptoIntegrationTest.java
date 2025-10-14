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
package org.keycloak.common.crypto;

import static org.junit.Assert.assertNull;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class CryptoIntegrationTest {
    private static CryptoProvider originalProvider;

    @BeforeClass
    public static void keepOriginalProvider() {
        CryptoIntegrationTest.originalProvider = getSelectedProvider();
    }

    // doing our best to avoid any side effects on other tests by restoring the initial state of CryptoIntegration
    @AfterClass
    public static void restoreOriginalProvider() {
        CryptoIntegration.setProvider(originalProvider);
    }

    @Test
    public void canSetNullProvider() {
        CryptoIntegration.setProvider(null);
        assertNull(getSelectedProvider());
    }

    private static CryptoProvider getSelectedProvider() {
        try {
            return CryptoIntegration.getProvider();
        } catch (IllegalStateException e) {
            return null;
        }
    }
}
