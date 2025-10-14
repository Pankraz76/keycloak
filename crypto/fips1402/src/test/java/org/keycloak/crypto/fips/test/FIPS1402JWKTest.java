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
import org.junit.Ignore;
import org.junit.Test;
import org.keycloak.common.util.Environment;
import org.keycloak.jose.jwk.JWKTest;

/**
 * Test with fips1402 security provider and bouncycastle-fips
 *
 * @author <a href="mailto:mposolda@redhat.com">Marek Posolda</a>
 */
public class FIPS1402JWKTest extends JWKTest {

    @Before
    public void before() {
        // Run this test just if java is in FIPS mode
        Assume.assumeTrue("Java is not in FIPS mode. Skipping the test.", Environment.isJavaInFipsMode());
    }

    @Ignore("Test not supported by BC FIPS")
    @Test
    public void publicEs256() throws Exception {
        // Do nothing
    }
}
