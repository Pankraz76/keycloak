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

import org.keycloak.admin.client.resource.IdentityProviderResource;
import org.keycloak.broker.oidc.mappers.UsernameTemplateMapper;
import org.keycloak.models.IdentityProviderMapperModel;
import org.keycloak.models.IdentityProviderMapperSyncMode;
import org.keycloak.representations.idm.IdentityProviderMapperRepresentation;
import org.keycloak.representations.idm.IdentityProviderRepresentation;

import com.google.common.collect.ImmutableMap;

/**
 * @author <a href="mailto:external.martin.idel@bosch.io">Martin Idel</a>
 */
public class KcOidcUsernameTemplateMapperTest extends AbstractUsernameTemplateMapperTest {
    @Override
    protected void createMapperInIdp(IdentityProviderRepresentation idp, IdentityProviderMapperSyncMode syncMode) {
        IdentityProviderMapperRepresentation usernameTemplateMapper = new IdentityProviderMapperRepresentation();
        usernameTemplateMapper.setName("oidc-username-template-mapper");
        usernameTemplateMapper.setIdentityProviderMapper(UsernameTemplateMapper.PROVIDER_ID);
        usernameTemplateMapper.setConfig(ImmutableMap.<String, String>builder()
                .put(IdentityProviderMapperModel.SYNC_MODE, syncMode.toString())
                .put("template", "${ALIAS}-${CLAIM.user-attribute}")
                .build());

        IdentityProviderResource idpResource = realm.identityProviders().get(idp.getAlias());
        usernameTemplateMapper.setIdentityProviderAlias(bc.getIDPAlias());
        idpResource.addMapper(usernameTemplateMapper).close();
    }

    @Override
    protected String getMapperTemplate() {
        return "kc-oidc-idp-%s";
    }

    @Override
    protected BrokerConfiguration getBrokerConfiguration() {
        return new KcOidcBrokerConfiguration();
    }
}
