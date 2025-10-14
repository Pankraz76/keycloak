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

import static org.keycloak.testsuite.broker.KcSamlBrokerConfiguration.ATTRIBUTE_TO_MAP_FRIENDLY_NAME;

import org.junit.Test;
import org.keycloak.broker.provider.ConfigConstants;
import org.keycloak.broker.saml.mappers.AdvancedAttributeToRoleMapper;
import org.keycloak.models.IdentityProviderMapperModel;
import org.keycloak.models.IdentityProviderMapperSyncMode;
import org.keycloak.representations.idm.IdentityProviderMapperRepresentation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:external.martin.idel@bosch.io">Martin Idel</a>,
 * <a href="mailto:daniel.fesenmeyer@bosch.io">Daniel Fesenmeyer</a>
 */
public class KcSamlAdvancedAttributeToRoleMapperTest extends AbstractAdvancedRoleMapperTest {

    private static final String ATTRIBUTES = "[\n" +
            "  {\n" +
            "    \"key\": \"" + ATTRIBUTE_TO_MAP_FRIENDLY_NAME + "\",\n" +
            "    \"value\": \"value 1\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"key\": \"" + ATTRIBUTE_TO_MAP_FRIENDLY_NAME + "\",\n" +
            "    \"value\": \"value 2\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"key\": \"" + KcOidcBrokerConfiguration.ATTRIBUTE_TO_MAP_NAME_2 + "\",\n" +
            "    \"value\": \"value 2\"\n" +
            "  }\n" +
            "]";


    @Override
    protected BrokerConfiguration getBrokerConfiguration() {
        return new KcSamlBrokerConfiguration();
    }

    @Override
    protected void createMapperInIdp(String claimsOrAttributeRepresentation,
                                     boolean areClaimsOrAttributeValuesRegexes, IdentityProviderMapperSyncMode syncMode, String roleValue) {
        IdentityProviderMapperRepresentation advancedAttributeToRoleMapper = new IdentityProviderMapperRepresentation();
        advancedAttributeToRoleMapper.setName("advanced-attribute-to-role-mapper");
        advancedAttributeToRoleMapper.setIdentityProviderMapper(AdvancedAttributeToRoleMapper.PROVIDER_ID);

        final Map<String, String> config = new HashMap<>();
        config.put(IdentityProviderMapperModel.SYNC_MODE, syncMode.toString());
        config.put(AdvancedAttributeToRoleMapper.ATTRIBUTE_PROPERTY_NAME, claimsOrAttributeRepresentation);
        config.put(AdvancedAttributeToRoleMapper.ARE_ATTRIBUTE_VALUES_REGEX_PROPERTY_NAME,
                Boolean.valueOf(areClaimsOrAttributeValuesRegexes).toString());
        config.put(ConfigConstants.ROLE, roleValue);
        advancedAttributeToRoleMapper.setConfig(config);

        persistMapper(advancedAttributeToRoleMapper);
    }

    @Test
    public void attributeFriendlyNameGetsConsideredAndMatchedToRole() {
        createAdvancedRoleMapper(ATTRIBUTES, false);
        createUserInProviderRealm(ImmutableMap.<String, List<String>>builder()
                .put(ATTRIBUTE_TO_MAP_FRIENDLY_NAME, ImmutableList.<String>builder().add("value 1").add("value 2").build())
                .put(KcOidcBrokerConfiguration.ATTRIBUTE_TO_MAP_NAME_2,
                        ImmutableList.<String>builder().add("value 2").build())
                .build());

        logInAsUserInIDPForFirstTime();

        assertThatRoleHasBeenAssignedInConsumerRealm();
    }

}
