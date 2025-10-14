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
package org.keycloak.testsuite.federation.ldap;

import org.junit.ClassRule;
import org.keycloak.testsuite.arquillian.annotation.EnableVault;
import org.keycloak.testsuite.util.LDAPRule;
import org.keycloak.testsuite.util.LDAPTestConfiguration;

import java.util.Map;

import static org.keycloak.models.LDAPConstants.BIND_CREDENTIAL;

/**
 * @author mhajas
 */
@EnableVault
public class LDAPVaultCredentialsTest extends LDAPSyncTest {

    private static final String VAULT_EXPRESSION = "${vault.ldap_bindCredential}";

    @ClassRule
    public static LDAPRule ldapRule = new LDAPRule() {
        @Override
        public Map<String, String> getConfig() {

            Map<String, String> config = super.getConfig();
            // Replace secret with vault expression
            config.put(BIND_CREDENTIAL, VAULT_EXPRESSION);
            return config;
        }
    }.assumeTrue(LDAPTestConfiguration::isStartEmbeddedLdapServer);

    @Override
    protected LDAPRule getLDAPRule() {
        return ldapRule;
    }
}
