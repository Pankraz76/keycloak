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
package org.keycloak.testframework.events;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.keycloak.representations.idm.AdminEventRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.util.JsonSerialization;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class AdminEventAssertionTest {

    @Test
    public void testSuccess() {
        AdminEventAssertion.assertSuccess(createEvent("CREATE"));
        Assertions.assertThrows(AssertionError.class, () -> AdminEventAssertion.assertSuccess(createEvent("CREATE_ERROR")));
        Assertions.assertThrows(AssertionError.class, () -> AdminEventAssertion.assertSuccess(createEvent("INVALID")));
    }

    @Test
    public void testError() {
        AdminEventAssertion.assertError(createEvent("CREATE_ERROR"));
        Assertions.assertThrows(AssertionError.class, () -> AdminEventAssertion.assertError(createEvent("CREATE")));
        Assertions.assertThrows(AssertionError.class, () -> AdminEventAssertion.assertError(createEvent("INVALID_ERROR")));
    }

    @Test
    public void assertRepresentation() throws IOException {
        UserRepresentation user = createUser("username", List.of("group-1", "group-2"));

        AdminEventRepresentation event = createEvent("CREATE");
        event.setRepresentation(JsonSerialization.writeValueAsString(user));

        AdminEventAssertion.assertSuccess(event).representation(user);

        Assertions.assertThrows(AssertionError.class, () ->
                AdminEventAssertion.assertSuccess(event).representation(createUser("username2", List.of("group-1", "group-2")))
        );
        Assertions.assertThrows(AssertionError.class, () ->
                AdminEventAssertion.assertSuccess(event).representation(createUser("username", List.of("group-1", "group-3")))
        );
    }

    private AdminEventRepresentation createEvent(String operation) {
        AdminEventRepresentation rep = new AdminEventRepresentation();
        rep.setId(UUID.randomUUID().toString());
        rep.setOperationType(operation);
        return rep;
    }

    private UserRepresentation createUser(String username, List<String> groups) {
        UserRepresentation user = new UserRepresentation();
        user.setEnabled(true);
        user.setUsername(username);
        user.setGroups(groups);
        return user;
    }

}
