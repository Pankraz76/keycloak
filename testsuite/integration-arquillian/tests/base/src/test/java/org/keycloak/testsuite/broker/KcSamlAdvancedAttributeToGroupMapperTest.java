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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.IdentityProviderResource;
import org.keycloak.broker.provider.ConfigConstants;
import org.keycloak.broker.saml.mappers.AdvancedAttributeToGroupMapper;
import org.keycloak.models.IdentityProviderMapperModel;
import org.keycloak.models.IdentityProviderMapperSyncMode;
import org.keycloak.representations.idm.IdentityProviderMapperRepresentation;
import org.keycloak.representations.idm.IdentityProviderRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.keycloak.testsuite.broker.KcSamlBrokerConfiguration.ATTRIBUTE_TO_MAP_FRIENDLY_NAME;

/**
 * @author <a href="mailto:external.martin.idel@bosch.io">Martin Idel</a>,
 * <a href="mailto:daniel.fesenmeyer@bosch.io">Daniel Fesenmeyer</a>
 */
public class KcSamlAdvancedAttributeToGroupMapperTest extends AbstractGroupBrokerMapperTest {

    private static final String ATTRIBUTES = "[\n" +
            "  {\n" +
            "    \"key\": \"" + ATTRIBUTE_TO_MAP_FRIENDLY_NAME + "\",\n" +
            "    \"value\": \"value 1\"\n" +
            "  },\n" + "  {\n" +
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
    protected String createMapperInIdp(IdentityProviderRepresentation idp, String claimsOrAttributeRepresentation,
                                       boolean areClaimsOrAttributeValuesRegexes, IdentityProviderMapperSyncMode syncMode, String groupValue) {
        IdentityProviderMapperRepresentation advancedAttributeToGroupMapper = new IdentityProviderMapperRepresentation();
        advancedAttributeToGroupMapper.setName("advanced-attribute-to-group-mapper");
        advancedAttributeToGroupMapper.setIdentityProviderMapper(AdvancedAttributeToGroupMapper.PROVIDER_ID);

        final Map<String, String> config = new HashMap<>();
        config.put(IdentityProviderMapperModel.SYNC_MODE, syncMode.toString());
        config.put(AdvancedAttributeToGroupMapper.ATTRIBUTE_PROPERTY_NAME, claimsOrAttributeRepresentation);
        config.put(AdvancedAttributeToGroupMapper.ARE_ATTRIBUTE_VALUES_REGEX_PROPERTY_NAME,
                Boolean.valueOf(areClaimsOrAttributeValuesRegexes).toString());
        config.put(ConfigConstants.GROUP, MAPPER_TEST_GROUP_PATH);
        advancedAttributeToGroupMapper.setConfig(config);

        IdentityProviderResource idpResource = realm.identityProviders().get(idp.getAlias());
        advancedAttributeToGroupMapper.setIdentityProviderAlias(bc.getIDPAlias());
        Response response = idpResource.addMapper(advancedAttributeToGroupMapper);
        return CreatedResponseUtil.getCreatedId(response);
    }

    @Test
    public void attributeFriendlyNameGetsConsideredAndMatchedToGroup() {
        createAdvancedGroupMapper(ATTRIBUTES, false, KcOidcBrokerConfiguration.ATTRIBUTE_TO_MAP_NAME_2);
        createUserInProviderRealm(ImmutableMap.<String, List<String>>builder()
                .put(ATTRIBUTE_TO_MAP_FRIENDLY_NAME, ImmutableList.<String>builder().add("value 1").add("value 2").build())
                .put(KcOidcBrokerConfiguration.ATTRIBUTE_TO_MAP_NAME_2,
                        ImmutableList.<String>builder().add("value 2").build())
                .build());

        logInAsUserInIDPForFirstTime();
        UserRepresentation user = findUser(bc.consumerRealmName(), bc.getUserLogin(), bc.getUserEmail());
        assertThatUserHasBeenAssignedToGroup(user, MAPPER_TEST_GROUP_PATH);
    }

}
