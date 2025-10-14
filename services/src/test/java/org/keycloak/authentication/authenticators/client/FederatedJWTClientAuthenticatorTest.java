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
package org.keycloak.authentication.authenticators.client;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class FederatedJWTClientAuthenticatorTest {

    @Test
    public void testToIssuer() {
        Assertions.assertEquals("https://something", FederatedJWTClientAuthenticator.toIssuer("https://something/client"));
        Assertions.assertEquals("spiffe://trust-domain", FederatedJWTClientAuthenticator.toIssuer("spiffe://trust-domain/the"));
        Assertions.assertEquals("spiffe://trust-domain", FederatedJWTClientAuthenticator.toIssuer("spiffe://trust-domain/the/client"));
        Assertions.assertNull(FederatedJWTClientAuthenticator.toIssuer("client"));
        Assertions.assertNull(FederatedJWTClientAuthenticator.toIssuer("the/client"));
        Assertions.assertNull(FederatedJWTClientAuthenticator.toIssuer("spiffe:/the/client"));
    }

}
