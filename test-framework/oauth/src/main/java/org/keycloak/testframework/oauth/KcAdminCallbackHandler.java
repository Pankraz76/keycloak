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
package org.keycloak.testframework.oauth;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.keycloak.constants.AdapterConstants;
import org.keycloak.jose.jws.JWSInput;
import org.keycloak.jose.jws.JWSInputException;
import org.keycloak.representations.adapters.action.LogoutAction;
import org.keycloak.representations.adapters.action.PushNotBeforeAction;
import org.keycloak.representations.adapters.action.TestAvailabilityAction;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class KcAdminCallbackHandler implements HttpHandler {

    private final KcAdminInvocations invocations;

    KcAdminCallbackHandler(KcAdminInvocations kcAdminInvocations) {
        this.invocations = kcAdminInvocations;
    }

    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        try {
            JWSInput adminToken = new JWSInput(new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8));
            if (path.endsWith(AdapterConstants.K_LOGOUT)) {
                invocations.add(adminToken.readJsonContent(LogoutAction.class));
            } else if (path.endsWith(AdapterConstants.K_PUSH_NOT_BEFORE)) {
                invocations.add(adminToken.readJsonContent(PushNotBeforeAction.class));
            } else if (path.endsWith(AdapterConstants.K_TEST_AVAILABLE)) {
                invocations.add(adminToken.readJsonContent(TestAvailabilityAction.class));
            }
            exchange.sendResponseHeaders(204, 0);
            exchange.close();
        } catch (JWSInputException e) {
            throw new IOException(e);
        }
    }

}
