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

import org.keycloak.broker.oidc.mappers.UserAttributeMapper;
import org.keycloak.models.IdentityProviderMapperModel;
import org.keycloak.models.IdentityProviderMapperSyncMode;
import org.keycloak.representations.idm.IdentityProviderMapperRepresentation;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

public class OidcUserAttributeMapperTest extends AbstractUserAttributeMapperTest {

    @Override
    protected BrokerConfiguration getBrokerConfiguration() {
        return KcOidcBrokerConfiguration.INSTANCE;
    }

    @Override
    protected Iterable<IdentityProviderMapperRepresentation> createIdentityProviderMappers(IdentityProviderMapperSyncMode syncMode) {
        IdentityProviderMapperRepresentation attrMapper1 = new IdentityProviderMapperRepresentation();
        attrMapper1.setName("attribute-mapper");
        attrMapper1.setIdentityProviderMapper(UserAttributeMapper.PROVIDER_ID);
        attrMapper1.setConfig(ImmutableMap.<String,String>builder()
          .put(IdentityProviderMapperModel.SYNC_MODE, syncMode.toString())
          .put(UserAttributeMapper.CLAIM, KcOidcBrokerConfiguration.ATTRIBUTE_TO_MAP_NAME)
          .put(UserAttributeMapper.USER_ATTRIBUTE, MAPPED_ATTRIBUTE_NAME)
          .build());

        IdentityProviderMapperRepresentation emailAttrMapper = new IdentityProviderMapperRepresentation();
        emailAttrMapper.setName("attribute-mapper-email");
        emailAttrMapper.setIdentityProviderMapper(UserAttributeMapper.PROVIDER_ID);
        emailAttrMapper.setConfig(ImmutableMap.<String,String>builder()
          .put(IdentityProviderMapperModel.SYNC_MODE, syncMode.toString())
          .put(UserAttributeMapper.CLAIM, "email")
          .put(UserAttributeMapper.USER_ATTRIBUTE, "email")
          .build());

        IdentityProviderMapperRepresentation nestedEmailAttrMapper = new IdentityProviderMapperRepresentation();
        nestedEmailAttrMapper.setName("nested-attribute-mapper-email");
        nestedEmailAttrMapper.setIdentityProviderMapper(UserAttributeMapper.PROVIDER_ID);
        nestedEmailAttrMapper.setConfig(ImmutableMap.<String,String>builder()
          .put(IdentityProviderMapperModel.SYNC_MODE, syncMode.toString())
          .put(UserAttributeMapper.CLAIM, "nested.email")
          .put(UserAttributeMapper.USER_ATTRIBUTE, "nested.email")
          .build());

        IdentityProviderMapperRepresentation dottedEmailAttrMapper = new IdentityProviderMapperRepresentation();
        dottedEmailAttrMapper.setName("dotted-attribute-mapper-email");
        dottedEmailAttrMapper.setIdentityProviderMapper(UserAttributeMapper.PROVIDER_ID);
        dottedEmailAttrMapper.setConfig(ImmutableMap.<String,String>builder()
          .put(IdentityProviderMapperModel.SYNC_MODE, syncMode.toString())
          .put(UserAttributeMapper.CLAIM, "dotted\\.email")
          .put(UserAttributeMapper.USER_ATTRIBUTE, "dotted.email")
          .build());

        return Lists.newArrayList(attrMapper1, emailAttrMapper, nestedEmailAttrMapper, dottedEmailAttrMapper);
    }

}
