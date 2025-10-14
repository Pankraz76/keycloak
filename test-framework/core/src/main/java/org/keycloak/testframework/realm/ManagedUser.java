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

import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.Optional;

public class ManagedUser {

    private final UserRepresentation createdRepresentation;

    private final UserResource userResource;

    public ManagedUser(UserRepresentation createdRepresentation, UserResource userResource) {
        this.createdRepresentation = createdRepresentation;
        this.userResource = userResource;
    }

    public String getId() {
        return createdRepresentation.getId();
    }

    public String getUsername() {
        return createdRepresentation.getUsername();
    }

    public String getPassword() {
        return getPassword(createdRepresentation);
    }

    public UserResource admin() {
        return userResource;
    }

    public static String getPassword(UserRepresentation userRepresentation) {
        Optional<CredentialRepresentation> password = userRepresentation.getCredentials().stream().filter(c -> c.getType().equals(CredentialRepresentation.PASSWORD)).findFirst();
        return password.map(CredentialRepresentation::getValue).orElse(null);
    }

}
