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
package org.keycloak.tests.admin;

import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.keycloak.models.BrowserSecurityHeaders;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.testframework.annotations.InjectRealm;
import org.keycloak.testframework.annotations.KeycloakIntegrationTest;
import org.keycloak.testframework.realm.ManagedRealm;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@KeycloakIntegrationTest
public class AdminHeadersTest {

    @InjectRealm
    private ManagedRealm realm;

    @Test
    public void testHeaders() {
        UserRepresentation userRep = new UserRepresentation();
        userRep.setUsername("headers-user");
        Response response = realm.admin().users().create(userRep);
        MultivaluedMap<String, Object> h = response.getHeaders();

        assertDefaultValue(BrowserSecurityHeaders.STRICT_TRANSPORT_SECURITY, h);
        assertDefaultValue(BrowserSecurityHeaders.X_FRAME_OPTIONS, h);
        assertDefaultValue(BrowserSecurityHeaders.X_CONTENT_TYPE_OPTIONS, h);
        assertDefaultValue(BrowserSecurityHeaders.REFERRER_POLICY, h);

        response.close();
    }

    private void assertDefaultValue(BrowserSecurityHeaders header, MultivaluedMap<String, Object> h) {
        assertThat(h.getFirst(header.getHeaderName()), is(equalTo(header.getDefaultValue())));
    }
}
