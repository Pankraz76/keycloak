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
package org.keycloak.testsuite.model;

import org.junit.Test;
import org.keycloak.models.KeycloakSession;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.services.managers.RealmManager;
import org.keycloak.testsuite.AbstractKeycloakTest;
import org.keycloak.testsuite.arquillian.annotation.ModelTest;
import org.keycloak.utils.ReservedCharValidator;

import java.util.List;

import static org.junit.Assert.fail;

public class BadRealmTest extends AbstractKeycloakTest {
    private String name = "MyRealm";
    private String id = "MyId";
    private String script = "<script>alert(4)</script>";

    public void addTestRealms(List<RealmRepresentation> testRealms) {
    }

    @Test
    @ModelTest
    public void testBadRealmName(KeycloakSession session) {
        RealmManager manager = new RealmManager(session);
        try {
            manager.createRealm(id, name + script);
            fail();
        } catch (ReservedCharValidator.ReservedCharException ex) {}
    }

    @Test
    @ModelTest
    public void testBadRealmId(KeycloakSession session) {
        RealmManager manager = new RealmManager(session);
        try {
            manager.createRealm(id + script, name);
            fail();
        } catch (ReservedCharValidator.ReservedCharException ex) {}
    }
}
