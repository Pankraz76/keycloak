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
package org.keycloak.tests.admin.userstorage;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.keycloak.common.util.MultivaluedHashMap;
import org.keycloak.models.LDAPConstants;
import org.keycloak.representations.idm.ComponentRepresentation;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.testframework.annotations.InjectAdminEvents;
import org.keycloak.testframework.annotations.InjectRealm;
import org.keycloak.testframework.events.AdminEvents;
import org.keycloak.testframework.realm.ManagedRealm;
import org.keycloak.tests.utils.admin.ApiUtil;

public class AbstractUserStorageRestTest {

    @InjectRealm
    ManagedRealm managedRealm;

    @InjectAdminEvents
    AdminEvents adminEvents;

    protected String createComponent(ComponentRepresentation rep) {
        Response resp = managedRealm.admin().components().add(rep);
        Assertions.assertEquals(201, resp.getStatus());
        resp.close();
        String id = ApiUtil.getCreatedId(resp);

        adminEvents.clear();
        return id;
    }

    protected void removeComponent(String id) {
        managedRealm.admin().components().component(id).remove();
        adminEvents.clear();
    }

    protected ComponentRepresentation createBasicLDAPProviderRep() {
        ComponentRepresentation ldapRep = new ComponentRepresentation();
        ldapRep.setName("ldap2");
        ldapRep.setProviderId("ldap");
        ldapRep.setProviderType(UserStorageProvider.class.getName());
        ldapRep.setConfig(new MultivaluedHashMap<>());
        ldapRep.getConfig().putSingle("priority", Integer.toString(2));
        ldapRep.getConfig().putSingle(LDAPConstants.EDIT_MODE, UserStorageProvider.EditMode.WRITABLE.name());
        return ldapRep;
    }
}
