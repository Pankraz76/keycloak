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
package org.keycloak.vault;

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.keycloak.common.util.Environment;

import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;
import static org.keycloak.vault.SecretContains.secretContains;

/**
 * Tests for {@link FilesKeystoreVaultProvider}.
 *
 * @author Peter Zaoral
 */
public class KeystoreVaultProviderTest {

    @Before
    public void before() {
        // TODO: improve when the supported keystore types for FIPS will be unified across the codebase
        Assume.assumeFalse("Java is in FIPS mode. Skipping the test.", Environment.isJavaInFipsMode());
    }

    @Test
    public void shouldObtainSecret() {
        //given
        // keytool -importpass -storetype pkcs12 -alias test_alias -keystore myks -storepass keystorepassword
        VaultProvider provider = new FilesKeystoreVaultProvider(Paths.get(Scenario.EXISTING.getAbsolutePathAsString() + "/myks"), "keystorepassword", "PKCS12","test",
                Arrays.asList(AbstractVaultProviderFactory.AvailableResolvers.REALM_UNDERSCORE_KEY.getVaultKeyResolver()));

        //when
        VaultRawSecret secret1 = provider.obtainSecret("alias");

        //then
        assertNotNull(secret1);
        assertNotNull(secret1.get().get());
        assertThat(secret1, secretContains("topsecret"));
    }

    @Test
    public void shouldObtainSecretFromDifferentKeystoreType() {
        //given
        VaultProvider provider = new FilesKeystoreVaultProvider(Paths.get(Scenario.EXISTING.getAbsolutePathAsString() + "/myks.jceks"), "keystorepassword", "JCEKS", "test",
                Arrays.asList(AbstractVaultProviderFactory.AvailableResolvers.REALM_UNDERSCORE_KEY.getVaultKeyResolver()));

        //when
        VaultRawSecret secret1 = provider.obtainSecret("alias");

        //then
        assertNotNull(secret1);
        assertNotNull(secret1.get().get());
        assertThat(secret1, secretContains("topsecret"));
    }

    @Test
    public void shouldFailBecauseOfTypeMismatch() {
        //given
        VaultProvider provider = new FilesKeystoreVaultProvider(Paths.get(Scenario.EXISTING.getAbsolutePathAsString() + "/myks"), "keystorepassword", "JCEKS", "test",
                Arrays.asList(AbstractVaultProviderFactory.AvailableResolvers.REALM_UNDERSCORE_KEY.getVaultKeyResolver()));

        //when
        assertThrows("java.io.IOException: Invalid keystore format", RuntimeException.class, () -> provider.obtainSecret("alias"));
    }
}