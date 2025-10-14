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

import org.keycloak.broker.oidc.mappers.AdvancedClaimToRoleMapper;
import org.keycloak.broker.provider.ConfigConstants;
import org.keycloak.models.IdentityProviderMapperModel;
import org.keycloak.models.IdentityProviderMapperSyncMode;
import org.keycloak.representations.idm.IdentityProviderMapperRepresentation;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="mailto:external.benjamin.weimer@bosch-si.com">Benjamin Weimer</a>,
 * <a href="mailto:external.martin.idel@bosch.io">Martin Idel</a>,
 * <a href="mailto:daniel.fesenmeyer@bosch.io">Daniel Fesenmeyer</a>
 */
public class OidcAdvancedClaimToRoleMapperTest extends AbstractAdvancedRoleMapperTest {
    @Override
    protected BrokerConfiguration getBrokerConfiguration() {
        return new KcOidcBrokerConfiguration();
    }

    @Override
    protected void createMapperInIdp(String claimsOrAttributeRepresentation,
            boolean areClaimsOrAttributeValuesRegexes, IdentityProviderMapperSyncMode syncMode, String roleValue) {
        IdentityProviderMapperRepresentation advancedClaimToRoleMapper = new IdentityProviderMapperRepresentation();
        advancedClaimToRoleMapper.setName("advanced-claim-to-role-mapper");
        advancedClaimToRoleMapper.setIdentityProviderMapper(AdvancedClaimToRoleMapper.PROVIDER_ID);

        final Map<String, String> config = new HashMap<>();
        config.put(IdentityProviderMapperModel.SYNC_MODE, syncMode.toString());
        config.put(AdvancedClaimToRoleMapper.CLAIM_PROPERTY_NAME, claimsOrAttributeRepresentation);
        config.put(AdvancedClaimToRoleMapper.ARE_CLAIM_VALUES_REGEX_PROPERTY_NAME,
                Boolean.valueOf(areClaimsOrAttributeValuesRegexes).toString());
        config.put(ConfigConstants.ROLE, roleValue);
        advancedClaimToRoleMapper.setConfig(config);

        persistMapper(advancedClaimToRoleMapper);
    }
}
