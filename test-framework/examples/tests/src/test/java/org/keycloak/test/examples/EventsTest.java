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
package org.keycloak.test.examples;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.keycloak.events.EventType;
import org.keycloak.representations.idm.EventRepresentation;
import org.keycloak.testframework.annotations.InjectEvents;
import org.keycloak.testframework.annotations.InjectRealm;
import org.keycloak.testframework.annotations.KeycloakIntegrationTest;
import org.keycloak.testframework.events.Events;
import org.keycloak.testframework.oauth.OAuthClient;
import org.keycloak.testframework.oauth.annotations.InjectOAuthClient;
import org.keycloak.testframework.realm.ManagedRealm;
import org.keycloak.testframework.remote.timeoffset.InjectTimeOffSet;
import org.keycloak.testframework.remote.timeoffset.TimeOffSet;

@KeycloakIntegrationTest
public class EventsTest {

    @InjectRealm
    private ManagedRealm realm;

    @InjectEvents
    private Events events;

    @InjectOAuthClient
    private OAuthClient oAuthClient;

    @InjectTimeOffSet
    TimeOffSet timeOffSet;

    @Test
    public void testFailedLogin() {
        oAuthClient.doPasswordGrantRequest("invalid", "invalid");

        EventRepresentation event = events.poll();
        Assertions.assertEquals(EventType.LOGIN_ERROR.name(), event.getType());
        Assertions.assertEquals("invalid", event.getDetails().get("username"));

        oAuthClient.doPasswordGrantRequest("invalid2", "invalid");

        event = events.poll();
        Assertions.assertEquals(EventType.LOGIN_ERROR.name(), event.getType());
        Assertions.assertEquals("invalid2", event.getDetails().get("username"));
    }

    @Test
    public void testTimeOffset() {
        timeOffSet.set(60);

        oAuthClient.doClientCredentialsGrantAccessTokenRequest();

        Assertions.assertEquals(EventType.CLIENT_LOGIN.name(), events.poll().getType());
    }

    @Test
    public void testClientLogin() {
        oAuthClient.doClientCredentialsGrantAccessTokenRequest();

        Assertions.assertEquals(EventType.CLIENT_LOGIN.name(), events.poll().getType());
    }

}
