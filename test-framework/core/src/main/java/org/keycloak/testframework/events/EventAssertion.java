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

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.keycloak.events.EventType;
import org.keycloak.representations.idm.EventRepresentation;

public class EventAssertion {

    private final EventRepresentation event;

    protected EventAssertion(EventRepresentation event) {
        Assertions.assertNotNull(event, "Event was null");
        Assertions.assertNotNull(event.getId(), "Event id was null");
        this.event = event;
    }

    public static EventAssertion assertSuccess(EventRepresentation event) {
        Assertions.assertFalse(event.getType().endsWith("_ERROR"), "Expected successful event");
        return new EventAssertion(event);
    }

    public static EventAssertion assertError(EventRepresentation event) {
        Assertions.assertTrue(event.getType().endsWith("_ERROR"), "Expected error event");
        return new EventAssertion(event);
    }

    public EventAssertion error(String error) {
        Assertions.assertEquals(error, event.getError());
        return this;
    }

    public EventAssertion type(EventType type) {
        Assertions.assertEquals(type, EventType.valueOf(event.getType()));
        return this;
    }

    public EventAssertion clientId(String clientId) {
        Assertions.assertEquals(clientId, event.getClientId());
        return this;
    }

    public EventAssertion details(String key, String value) {
        if (value != null) {
            MatcherAssert.assertThat(event.getDetails(), Matchers.hasEntry(key, value));
        } else {
            withoutDetails(key);
        }
        return this;
    }

    public EventAssertion withoutDetails(String... keys) {
        for (String key : keys) {
            MatcherAssert.assertThat(event.getDetails(), Matchers.not(Matchers.hasKey(key)));
        }
        return this;
    }

}
