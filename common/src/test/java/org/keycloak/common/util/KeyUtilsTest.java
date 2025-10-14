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
package org.keycloak.common.util;

import org.junit.Test;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class KeyUtilsTest {

    @Test
    public void loadSecretKey() {
        byte[] secretBytes = new byte[32];
        ThreadLocalRandom.current().nextBytes(secretBytes);
        SecretKeySpec expected = new SecretKeySpec(secretBytes, "HmacSHA256");
        SecretKey actual = KeyUtils.loadSecretKey(secretBytes, "HmacSHA256");
        assertEquals(expected.getAlgorithm(), actual.getAlgorithm());
        assertArrayEquals(expected.getEncoded(), actual.getEncoded());
    }

}