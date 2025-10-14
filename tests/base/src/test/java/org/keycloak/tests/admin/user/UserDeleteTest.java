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
package org.keycloak.tests.admin.user;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.keycloak.testframework.annotations.KeycloakIntegrationTest;
import org.keycloak.testframework.events.AdminEventAssertion;
import org.keycloak.testframework.realm.UserConfigBuilder;
import org.keycloak.tests.utils.admin.ApiUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;

@KeycloakIntegrationTest
public class UserDeleteTest extends AbstractUserTest {

    @Test
    public void delete() {
        String userId = ApiUtil.getCreatedId(managedRealm.admin().users().create(UserConfigBuilder.create().username("user1").email("user1@localhost.com").build()));
        AdminEventAssertion.assertSuccess(adminEvents.poll());
        deleteUser(userId);
    }

    @Test
    public void deleteNonExistent() {
        try (Response response = managedRealm.admin().users().delete("does-not-exist")) {
            assertEquals(404, response.getStatus());
        }
        Assertions.assertNull(adminEvents.poll());
    }
}
