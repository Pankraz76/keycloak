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
package org.keycloak.crypto.fips.test;

import java.util.Set;
import java.util.stream.Collectors;

import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.hamcrest.Matchers;
import org.junit.Assume;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.keycloak.common.crypto.CryptoIntegration;
import org.keycloak.common.util.Environment;
import org.keycloak.common.util.KeystoreUtil;
import org.keycloak.rule.CryptoInitRule;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author <a href="mailto:mposolda@redhat.com">Marek Posolda</a>
 */
public class FIPS1402KeystoreTypesTest {

    @ClassRule
    public static CryptoInitRule cryptoInitRule = new CryptoInitRule();

    @Before
    public void before() {
        // Run this test just if java is in FIPS mode
        Assume.assumeTrue("Java is not in FIPS mode. Skipping the test.", Environment.isJavaInFipsMode());
    }

    @Test
    public void testKeystoreFormatsInNonApprovedMode() {
        Assume.assumeFalse(CryptoServicesRegistrar.isInApprovedOnlyMode());
        Set<KeystoreUtil.KeystoreFormat> supportedKeystoreFormats = CryptoIntegration.getProvider().getSupportedKeyStoreTypes().collect(Collectors.toSet());
        assertThat(supportedKeystoreFormats, Matchers.containsInAnyOrder(
                KeystoreUtil.KeystoreFormat.PKCS12,
                KeystoreUtil.KeystoreFormat.BCFKS));
    }

    // BCFIPS approved mode supports only BCFKS. No JKS nor PKCS12 support for keystores
    @Test
    public void testKeystoreFormatsInApprovedMode() {
        Assume.assumeTrue(CryptoServicesRegistrar.isInApprovedOnlyMode());
        Set<KeystoreUtil.KeystoreFormat> supportedKeystoreFormats = CryptoIntegration.getProvider().getSupportedKeyStoreTypes().collect(Collectors.toSet());
        assertThat(supportedKeystoreFormats, Matchers.containsInAnyOrder(
                KeystoreUtil.KeystoreFormat.BCFKS));
    }
}
