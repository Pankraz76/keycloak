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


import java.util.UUID;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.keycloak.common.util.BouncyIntegration;
import org.keycloak.common.util.Environment;
import org.keycloak.jose.HmacTest;


/**
 * Another variation to this test using SecretKeyFactory
 *
 */
public class FIPS1402HmacTest extends HmacTest {

    @Before
    public void before() {
        // Run this test just if java is in FIPS mode
        Assume.assumeTrue("Java is not in FIPS mode. Skipping the test.", Environment.isJavaInFipsMode());
    }

    @Test
    public void testHmacSignaturesWithRandomSecretKeyCreatedByFactory() throws Exception {
        SecretKeyFactory skFact = SecretKeyFactory.getInstance("HmacSHA256", BouncyIntegration.PROVIDER );
        SecretKey secretKey = skFact.generateSecret(new SecretKeySpec(UUID.randomUUID().toString().getBytes(), "HmacSHA256"));
        testHMACSignAndVerify(secretKey, "testHmacSignaturesWithRandomSecretKeyCreatedByFactory");
    }

    @Override
    public void testHmacSignaturesWithShortSecretKey() throws Exception {
        // With BCFIPS approved mode, secret key used for HmacSHA256 must be at least 112 bits long (14 characters). Short key won't work
        Assume.assumeFalse(CryptoServicesRegistrar.isInApprovedOnlyMode());
        super.testHmacSignaturesWithShortSecretKey();
    }
}
