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
package org.keycloak.authentication.authenticators.conditional;

import org.keycloak.Config.Scope;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.AuthenticationExecutionModel.Requirement;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.Arrays;
import java.util.List;

public class ConditionalRoleAuthenticatorFactory implements ConditionalAuthenticatorFactory {
    public static final String PROVIDER_ID = "conditional-user-role";

    public static final String CONDITIONAL_USER_ROLE = "condUserRole";
    public static final String CONF_NEGATE = "negate";

    @Override
    public void init(Scope config) {
        // no-op
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
        // no-op
    }

    @Override
    public void close() {
        // no-op
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public String getDisplayType() {
        return "Condition - user role";
    }

    @Override
    public boolean isConfigurable() {
        return true;
    }

    private static final Requirement[] REQUIREMENT_CHOICES = {
            AuthenticationExecutionModel.Requirement.REQUIRED,
            AuthenticationExecutionModel.Requirement.DISABLED
    };

    @Override
    public Requirement[] getRequirementChoices() {
        return REQUIREMENT_CHOICES;
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public String getHelpText() {
        return "Flow is executed only if user has the given role.";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        ProviderConfigProperty role = new ProviderConfigProperty();
        role.setType(ProviderConfigProperty.ROLE_TYPE);
        role.setName(CONDITIONAL_USER_ROLE);
        role.setLabel("User role");
        role.setHelpText("Role the user should have to execute this flow. Click 'Select Role' button to browse roles, or just type it in the textbox. To reference a client role the syntax is clientname.clientrole, i.e. myclient.myrole");

        ProviderConfigProperty negateOutput = new ProviderConfigProperty();
        negateOutput.setType(ProviderConfigProperty.BOOLEAN_TYPE);
        negateOutput.setName(CONF_NEGATE);
        negateOutput.setLabel("Negate output");
        negateOutput.setHelpText("Apply a NOT to the check result. When this is true, then the condition will evaluate to true just if user does NOT have the specified role. When this is false, the condition will evaluate to true just if user has the specified role");

        return Arrays.asList(role, negateOutput);
    }

    @Override
    public ConditionalAuthenticator getSingleton() {
        return ConditionalRoleAuthenticator.SINGLETON;
    }
}
