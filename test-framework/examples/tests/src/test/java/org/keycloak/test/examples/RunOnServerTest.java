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
import org.keycloak.models.ModelException;
import org.keycloak.models.RealmModel;
import org.keycloak.models.utils.ModelToRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.testframework.annotations.InjectRealm;
import org.keycloak.testframework.annotations.KeycloakIntegrationTest;
import org.keycloak.testframework.realm.ManagedRealm;
import org.keycloak.testframework.remote.providers.runonserver.RunOnServerException;
import org.keycloak.testframework.remote.runonserver.InjectRunOnServer;
import org.keycloak.testframework.remote.runonserver.RunOnServerClient;
import org.opentest4j.AssertionFailedError;

@KeycloakIntegrationTest
public class RunOnServerTest {

    @InjectRealm
    ManagedRealm realm;

    @InjectRealm(attachTo = "master", ref = "master")
    ManagedRealm masterRealm;

    @InjectRunOnServer(permittedPackages = "org.keycloak.test.examples")
    RunOnServerClient runOnServer;

    @InjectRunOnServer(permittedPackages = "org.keycloak.test.examples", ref = "master", realmRef = "master")
    RunOnServerClient runOnServerMaster;

    @Test
    public void verifyRealm() {
        Assertions.assertEquals("default", runOnServer.fetch(session -> session.getContext().getRealm().getName(), String.class));
        Assertions.assertEquals("master", runOnServerMaster.fetch(session -> session.getContext().getRealm().getName(), String.class));
    }

    @Test
    public void runOnServerString() {
        String string = runOnServer.fetch(session -> "Hello world!", String.class);
        Assertions.assertEquals("Hello world!", string);
    }

    @Test
    public void runOnServerRep() {
        final String realmName = "master";

        RealmRepresentation realmRep = runOnServer.fetch(session -> {
            RealmModel master = session.realms().getRealmByName(realmName);
            return ModelToRepresentation.toRepresentation(session, master, true);
        }, RealmRepresentation.class);

        Assertions.assertEquals(realmName, realmRep.getRealm());
    }

    @Test
    public void runOnServerHelpers() {
        RealmRepresentation realmRep = runOnServer.fetch(RunOnServerHelpers.internalRealm());
        Assertions.assertEquals("default", realmRep.getRealm());
    }

    @Test
    public void runOnServerNoResponse() {
        runOnServer.run(session -> session.getContext().getRealm());
    }

    @Test
    public void runOnServerAssertOnServer() {
        try {
            runOnServer.run(session -> Assertions.assertEquals("foo", "bar"));
            Assertions.fail("Expected exception");
        } catch (AssertionFailedError e) {
            Assertions.assertEquals("expected: <foo> but was: <bar>", e.getMessage());
        }
    }

    @Test
    public void runOnServerExceptionOnServer() {
        try {
            runOnServer.run(session -> {
                throw new ModelException("Something went wrong");
            });
            Assertions.fail("Expected exception");
        } catch (RunOnServerException e) {
            Assertions.assertTrue(e.getCause() instanceof ModelException);
            Assertions.assertEquals("Something went wrong", e.getCause().getMessage());
        }
    }

}
