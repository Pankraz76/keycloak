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
package org.keycloak.testframework.realm;

import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;

class Representations {

    private Representations() {
    }

    static RoleRepresentation toRole(String roleName, boolean asClientRole) {
        RoleRepresentation role = new RoleRepresentation();
        role.setName(roleName);
        role.setClientRole(asClientRole);
        return role;
    }

    static GroupRepresentation toGroup(String groupName) {
        GroupRepresentation group = new GroupRepresentation();
        group.setName(groupName);
        return group;
    }

    static CredentialRepresentation toCredential(String type, String value) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(type);
        credential.setValue(value);
        return credential;
    }

}
