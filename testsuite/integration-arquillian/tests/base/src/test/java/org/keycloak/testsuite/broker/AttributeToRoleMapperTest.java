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
package org.keycloak.testsuite.broker;

import static org.keycloak.models.IdentityProviderMapperSyncMode.FORCE;
import static org.keycloak.models.IdentityProviderMapperSyncMode.LEGACY;

import org.junit.Test;
import org.keycloak.broker.provider.ConfigConstants;
import org.keycloak.broker.saml.mappers.AttributeToRoleMapper;
import org.keycloak.broker.saml.mappers.UserAttributeMapper;
import org.keycloak.models.IdentityProviderMapperModel;
import org.keycloak.models.IdentityProviderMapperSyncMode;
import org.keycloak.representations.idm.IdentityProviderMapperRepresentation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:external.martin.idel@bosch.io">Martin Idel</a>
 */
public class AttributeToRoleMapperTest extends AbstractRoleMapperTest {

    private static final String ROLE_ATTR_NAME = "Role";

    @Override
    protected BrokerConfiguration getBrokerConfiguration() {
        return new KcSamlBrokerConfiguration();
    }

    @Test
    public void mapperGrantsRoleOnFirstLogin() {
        createMapperThenLoginAsUserTwiceWithAttributeToRoleMapper();

        assertThatRoleHasBeenAssignedInConsumerRealm();
    }

    @Test
    public void updateBrokeredUserGrantsRoleInLegacyMode() {
        loginAsUserThenCreateMapperAndLoginAgainWithAttributeToRoleMapper(LEGACY);

        assertThatRoleHasBeenAssignedInConsumerRealm();
    }

    @Test
    public void updateBrokeredUserGrantsRoleInForceMode() {
        loginAsUserThenCreateMapperAndLoginAgainWithAttributeToRoleMapper(FORCE);

        assertThatRoleHasBeenAssignedInConsumerRealm();
    }

    private void createMapperThenLoginAsUserTwiceWithAttributeToRoleMapper() {
        loginAsUserTwiceWithMapper(IdentityProviderMapperSyncMode.FORCE, false,
                createUserConfigForRole(CLIENT_ROLE_MAPPER_REPRESENTATION));
    }

    private void loginAsUserThenCreateMapperAndLoginAgainWithAttributeToRoleMapper(
            IdentityProviderMapperSyncMode syncMode) {
        loginAsUserTwiceWithMapper(syncMode, true, createUserConfigForRole(CLIENT_ROLE_MAPPER_REPRESENTATION));
    }

    @Override
    protected void createMapperInIdp(IdentityProviderMapperSyncMode syncMode, String roleValue) {
        IdentityProviderMapperRepresentation samlAttributeToRoleMapper = new IdentityProviderMapperRepresentation();
        samlAttributeToRoleMapper.setName("user-role-mapper");
        samlAttributeToRoleMapper.setIdentityProviderMapper(AttributeToRoleMapper.PROVIDER_ID);
        samlAttributeToRoleMapper.setConfig(ImmutableMap.<String, String> builder()
                .put(IdentityProviderMapperModel.SYNC_MODE, syncMode.toString())
                .put(UserAttributeMapper.ATTRIBUTE_NAME, ROLE_ATTR_NAME)
                .put(ATTRIBUTE_VALUE, ROLE_USER)
                .put(ConfigConstants.ROLE, roleValue)
                .build());

        persistMapper(samlAttributeToRoleMapper);
    }

    protected Map<String, List<String>> createUserConfigForRole(String roleValue) {
        return ImmutableMap.<String, List<String>> builder()
                .put(ROLE_ATTR_NAME, ImmutableList.<String> builder().add(roleValue).build())
                .build();
    }
}
