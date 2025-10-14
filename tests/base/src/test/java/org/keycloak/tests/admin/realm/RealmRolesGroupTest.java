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
package org.keycloak.tests.admin.realm;

import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.testframework.annotations.KeycloakIntegrationTest;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@KeycloakIntegrationTest
public class RealmRolesGroupTest extends AbstractRealmRolesTest {

    /**
     * KEYCLOAK-4978 Verifies that Groups assigned to Role are being properly retrieved as members in API endpoint for role membership
     */
    @Test
    public void testGroupsInRole() {
        RoleResource role = managedRealm.admin().roles().get("role-with-users");

        List<GroupRepresentation> groups = managedRealm.admin().groups().groups();
        GroupRepresentation groupRep = groups.stream().filter(g -> g.getPath().equals("/test-role-group")).findFirst().get();

        RoleResource roleResource = managedRealm.admin().roles().get(role.toRepresentation().getName());
        List<RoleRepresentation> rolesToAdd = new LinkedList<>();
        rolesToAdd.add(roleResource.toRepresentation());
        managedRealm.admin().groups().group(groupRep.getId()).roles().realmLevel().add(rolesToAdd);

        roleResource = managedRealm.admin().roles().get(role.toRepresentation().getName());

        Set<GroupRepresentation> groupsInRole = roleResource.getRoleGroupMembers();
        assertTrue(groupsInRole.stream().anyMatch(g -> g.getPath().equals("/test-role-group")));
    }

    /**
     * KEYCLOAK-4978  Verifies that Role with no users assigned is being properly retrieved without groups in API endpoint for role membership
     */
    @Test
    public void testGroupsNotInRole() {
        RoleResource role = managedRealm.admin().roles().get("role-without-users");

        role = managedRealm.admin().roles().get(role.toRepresentation().getName());

        Set<GroupRepresentation> groupsInRole = role.getRoleGroupMembers();
        assertTrue(groupsInRole.isEmpty());
    }
}
