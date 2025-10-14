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
package org.keycloak.storage.ldap.idm.model;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.Test;
import org.keycloak.representations.idm.LDAPCapabilityRepresentation;
import org.keycloak.representations.idm.LDAPCapabilityRepresentation.CapabilityType;
import org.keycloak.storage.ldap.idm.store.ldap.extended.PasswordModifyRequest;

public class LDAPCapabilityTest {

    @Test
    public void testEquals() {
        LDAPCapabilityRepresentation oid1 = new LDAPCapabilityRepresentation(PasswordModifyRequest.PASSWORD_MODIFY_OID, CapabilityType.CONTROL);
        LDAPCapabilityRepresentation oid2 = new LDAPCapabilityRepresentation(PasswordModifyRequest.PASSWORD_MODIFY_OID, CapabilityType.EXTENSION);
        LDAPCapabilityRepresentation oid3 = new LDAPCapabilityRepresentation(PasswordModifyRequest.PASSWORD_MODIFY_OID, CapabilityType.EXTENSION);
        assertFalse(oid1.equals(oid2));
        assertTrue(oid2.equals(oid3));
        System.out.println(oid1);
    }

    @Test
    public void testContains() {
        LDAPCapabilityRepresentation oid1 = new LDAPCapabilityRepresentation(PasswordModifyRequest.PASSWORD_MODIFY_OID, CapabilityType.EXTENSION);
        LDAPCapabilityRepresentation oidx = new LDAPCapabilityRepresentation(PasswordModifyRequest.PASSWORD_MODIFY_OID, CapabilityType.EXTENSION);
        LDAPCapabilityRepresentation oid2 = new LDAPCapabilityRepresentation("13.2.3.11.22", CapabilityType.CONTROL);
        LDAPCapabilityRepresentation oid3 = new LDAPCapabilityRepresentation("14.2.3.42.22", CapabilityType.FEATURE);
        Set<LDAPCapabilityRepresentation> ids = new LinkedHashSet<>();
        ids.add(oid1);
        ids.add(oidx);
        ids.add(oid2);
        ids.add(oid3);
        assertTrue(ids.contains(oid1));
        assertTrue(ids.contains(oidx));
        assertEquals(3, ids.size());
    }

    @Test
    public void testCapabilityTypeFromAttributeName() {
        CapabilityType extension = CapabilityType.fromRootDseAttributeName("supportedExtension");
        assertEquals(CapabilityType.EXTENSION, extension);

        CapabilityType control = CapabilityType.fromRootDseAttributeName("supportedControl");
        assertEquals(CapabilityType.CONTROL, control);

        CapabilityType feature = CapabilityType.fromRootDseAttributeName("supportedFeatures");
        assertEquals(CapabilityType.FEATURE, feature);

        CapabilityType unknown = CapabilityType.fromRootDseAttributeName("foo");
        assertEquals(CapabilityType.UNKNOWN, unknown);
    }
}
