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
package org.keycloak.testsuite.authentication;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SetUserAttributeAuthenticator implements Authenticator {
    @Override
    public void authenticate(AuthenticationFlowContext context) {
        // Retrieve configuration
        Map<String, String> config = context.getAuthenticatorConfig().getConfig();
        String attrName = config.get(SetUserAttributeAuthenticatorFactory.CONF_ATTR_NAME);
        String attrValue = config.get(SetUserAttributeAuthenticatorFactory.CONF_ATTR_VALUE);

        UserModel user = context.getUser();
        List<String> attrValues = user.getAttributeStream(attrName).collect(Collectors.toList());
        if (attrValues.isEmpty()) {
            user.setSingleAttribute(attrName, attrValue);
        }
        else {
            if (!attrValues.contains(attrValue)) {
                attrValues.add(attrValue);
            }
            user.setAttribute(attrName, attrValues);
        }

        context.success();
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        context.failure(AuthenticationFlowError.INTERNAL_ERROR);
    }

    @Override
    public boolean requiresUser() {
        return true;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {

    }

    @Override
    public void close() {

    }
}
