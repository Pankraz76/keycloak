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
package org.keycloak.testsuite.broker;

import org.junit.Test;

/**
 * Test of various scenarios related to the use of default IdP option
 * in the Identity Provider Redirector authenticator
 */
public class KcSamlDefaultIdpTest extends AbstractDefaultIdpTest {

    // KEYCLOAK-17368
    @Test
    public void testDefaultIdpSetTriedAndReturnedError() {
        testDefaultIdpSetTriedAndReturnedError("Unexpected error when authenticating with identity provider");
    }

    @Override
    protected BrokerConfiguration getBrokerConfiguration() {
        return new KcSamlBrokerConfiguration();
    }
}
