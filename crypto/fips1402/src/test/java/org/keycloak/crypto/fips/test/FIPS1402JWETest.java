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

import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;
import org.keycloak.common.util.Environment;
import org.keycloak.jose.JWETest;

/**
 * Test with fips1402 security provider and bouncycastle-fips
 *
 * @author <a href="mailto:mposolda@redhat.com">Marek Posolda</a>
 */
public class FIPS1402JWETest extends JWETest {

    @Before
    public void before() {
        // Run this test just if java is in FIPS mode
        Assume.assumeTrue("Java is not in FIPS mode. Skipping the test.", Environment.isJavaInFipsMode());
    }

    @Test
    @Override
    public void testRSA1_5_A128GCM() throws Exception {
        // https://www.bouncycastle.org/download/bouncy-castle-java-fips/#release-notes
        // The provider blocks RSA with PKCS1.5 encryption
        Assume.assumeFalse("approved_only is set", Boolean.getBoolean("org.bouncycastle.fips.approved_only"));
        super.testRSA1_5_A128GCM();
    }

    @Test
    @Override
    public void testRSA1_5_A128CBCHS256() throws Exception {
        Assume.assumeFalse("approved_only is set", Boolean.getBoolean("org.bouncycastle.fips.approved_only"));
        super.testRSA1_5_A128CBCHS256();
    }
}
