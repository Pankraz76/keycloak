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
package org.keycloak.cache;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

public class ComputedKeyTest {

    @Test
    public void testComputedKeyWithStrings() {
        String k1 = ComputedKey.computeKey("realm", "type", "key1");
        Assertions.assertEquals(k1, ComputedKey.computeKey("realm", "type", "key1"));
        Assertions.assertNotEquals(k1, ComputedKey.computeKey("realm2", "type", "key"));
        Assertions.assertNotEquals(k1, ComputedKey.computeKey("realm", "type2", "key"));
        Assertions.assertNotEquals(k1, ComputedKey.computeKey("realm", "type", "key2"));
    }

    @Test
    public void testComputedKeyWithAttributes() {
        String k1 = ComputedKey.computeKey("realm", "type", Map.of("one", "one", "two", "two"));
        Assertions.assertEquals(k1, ComputedKey.computeKey("realm", "type", Map.of("one", "one", "two", "two")));
        Assertions.assertEquals(k1, ComputedKey.computeKey("realm", "type", Map.of("two", "two", "one", "one")));
        Assertions.assertNotEquals(k1, ComputedKey.computeKey("realm2", "type", Map.of("one", "one", "two", "two")));
        Assertions.assertNotEquals(k1, ComputedKey.computeKey("realm", "type2", Map.of("one", "one", "two", "two")));
        Assertions.assertNotEquals(k1, ComputedKey.computeKey("realm", "type", Map.of("one2", "one", "two", "two")));
        Assertions.assertNotEquals(k1, ComputedKey.computeKey("realm", "type", Map.of("one", "one2", "two", "two")));
        Assertions.assertNotEquals(k1, ComputedKey.computeKey("realm", "type", Map.of("one", "one", "two2", "two")));
        Assertions.assertNotEquals(k1, ComputedKey.computeKey("realm", "type", Map.of("one", "one", "two", "two2")));
    }

}
