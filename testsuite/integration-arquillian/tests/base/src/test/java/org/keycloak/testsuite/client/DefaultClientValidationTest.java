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
package org.keycloak.testsuite.client;

import org.junit.Test;
import org.keycloak.validation.DefaultClientValidationProvider;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DefaultClientValidationTest {
    @Test
    public void that_checkCurlyBracketsBalanced_worksCorrectly() {
        String urlWithCurlyBrackets1="http://{test}/prova123";
        String urlWithCurlyBrackets2="http://{test}/{prova123}";
        String urlWithCurlyBrackets3="http://{{test}/{prova123}}";
        assertTrue(DefaultClientValidationProvider.checkCurlyBracketsBalanced(urlWithCurlyBrackets1));
        assertTrue(DefaultClientValidationProvider.checkCurlyBracketsBalanced(urlWithCurlyBrackets2));
        assertTrue(DefaultClientValidationProvider.checkCurlyBracketsBalanced(urlWithCurlyBrackets3));
    }
    @Test
    public void that_checkCurlyBracketsBalanced_notWorksCorrectly() {
        String urlWithImproperlyCurlyBrackets="http://}test}/prova123";
        String urlWithImproperlyCurlyBrackets1="http://{test}/prova123}";
        assertFalse(DefaultClientValidationProvider.checkCurlyBracketsBalanced(urlWithImproperlyCurlyBrackets));
        assertFalse(DefaultClientValidationProvider.checkCurlyBracketsBalanced(urlWithImproperlyCurlyBrackets1));
    }
}
