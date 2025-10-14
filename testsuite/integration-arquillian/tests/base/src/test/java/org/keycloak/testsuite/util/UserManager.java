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
package org.keycloak.testsuite.util;

import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.ArrayList;
import java.util.Arrays;

import static org.keycloak.testsuite.admin.ApiUtil.findUserByUsernameId;

/**
 * @author <a href="mailto:bruno@abstractj.org">Bruno Oliveira</a>.
 */
public class UserManager {

    private static RealmResource realm;

    private UserManager() {
    }

    public static UserManager realm(RealmResource realm) {
        UserManager.realm = realm;
        return new UserManager();
    }

    public UserManagerBuilder username(String username) {
        return new UserManagerBuilder(findUserByUsernameId(realm, username));
    }

    public UserManagerBuilder user(UserResource user) {
        return new UserManagerBuilder(user);
    }

    public class UserManagerBuilder {

        private final UserResource userResource;

        public UserManagerBuilder(UserResource userResource) {
            this.userResource = userResource;
        }

        public void removeRequiredAction(String action) {
            UserRepresentation user = initializeRequiredActions();
            user.getRequiredActions().remove(action);
            userResource.update(user);
        }

        public void addRequiredAction(String... actions) {
            UserRepresentation user = initializeRequiredActions();
            user.setRequiredActions(Arrays.asList(actions));
            userResource.update(user);
        }

        public void assignRoles(String... roles) {
            UserRepresentation user = userResource.toRepresentation();
            if (user != null && user.getRealmRoles() == null) {
                user.setRealmRoles(new ArrayList<String>());
            }
            user.setRealmRoles(Arrays.asList(roles));
            userResource.update(user);
        }

        public void enabled(Boolean enabled) {
            UserRepresentation user = userResource.toRepresentation();
            user.setEnabled(enabled);
            userResource.update(user);
        }


        private UserRepresentation initializeRequiredActions() {
            UserRepresentation user = userResource.toRepresentation();
            if (user != null && user.getRequiredActions() == null) {
                user.setRequiredActions(new ArrayList<String>());
            }
            return user;
        }

    }
}