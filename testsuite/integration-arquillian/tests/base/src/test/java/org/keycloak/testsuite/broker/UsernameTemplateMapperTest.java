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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.keycloak.admin.client.resource.IdentityProviderResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.broker.oidc.mappers.UserAttributeMapper;
import org.keycloak.broker.oidc.mappers.UsernameTemplateMapper;
import org.keycloak.representations.idm.IdentityProviderMapperRepresentation;
import org.keycloak.representations.idm.IdentityProviderRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import static org.junit.Assert.assertEquals;
import static org.keycloak.testsuite.broker.BrokerTestTools.getConsumerRoot;

public class UsernameTemplateMapperTest extends AbstractBaseBrokerTest {

    private String idpUserId;

    @Override
    protected BrokerConfiguration getBrokerConfiguration() {
        return KcOidcBrokerConfiguration.INSTANCE;
    }

    @Before
    public void addClients() {
        addClientsToProviderAndConsumer();
    }

    @Before
    public void addIdentityProviderToConsumerRealm() {

        log.debug("adding identity provider to realm " + bc.consumerRealmName());

        RealmResource realm = adminClient.realm(bc.consumerRealmName());
        IdentityProviderRepresentation idp = bc.setUpIdentityProvider();
        realm.identityProviders().create(idp).close();

        IdentityProviderResource idpResource = realm.identityProviders().get(idp.getAlias());
        for (IdentityProviderMapperRepresentation mapper : createIdentityProviderMappers()) {
            mapper.setIdentityProviderAlias(bc.getIDPAlias());
            idpResource.addMapper(mapper).close();
        }
    }

    @Before
    public void createUserInIdp() {
        idpUserId = createUser(bc.providerRealmName(), bc.getUserLogin(), bc.getUserPassword(), "First", "Last", bc.getUserEmail());
    }

    protected Iterable<IdentityProviderMapperRepresentation> createIdentityProviderMappers() {

        IdentityProviderMapperRepresentation userTemplateImporterMapper = new IdentityProviderMapperRepresentation();
        userTemplateImporterMapper.setName("custom-username-import-mapper");
        userTemplateImporterMapper.setIdentityProviderMapper(UsernameTemplateMapper.PROVIDER_ID);
        userTemplateImporterMapper.setConfig(ImmutableMap.<String, String>builder()
                .put(UsernameTemplateMapper.TEMPLATE, "${ALIAS}_${CLAIM.sub}")
                .build());

        IdentityProviderMapperRepresentation jwtClaimsAttrMapper = new IdentityProviderMapperRepresentation();
        jwtClaimsAttrMapper.setName("jwt-claims-mapper");
        jwtClaimsAttrMapper.setIdentityProviderMapper(UserAttributeMapper.PROVIDER_ID);
        jwtClaimsAttrMapper.setConfig(ImmutableMap.<String, String>builder()
                .put(UserAttributeMapper.CLAIM, "sub")
                .put(UserAttributeMapper.USER_ATTRIBUTE, "mappedSub")
                .put(UserAttributeMapper.CLAIM_VALUE, "${CLAIM.sub};test")
                .build());

        return Lists.newArrayList(userTemplateImporterMapper, jwtClaimsAttrMapper);
    }

    /**
     * See: KEYCLOAK-8100
     */
    @Test
    public void userAttributeShouldBeDerivedFromIdpSubClaim() {

        logInAsUserInIDP();
        logoutFromRealm(getConsumerRoot(), bc.consumerRealmName());

        UserRepresentation user = adminClient.realm(bc.consumerRealmName()).users().search(bc.getUserEmail(), 0, 1).get(0);

        assertEquals("Should render idpSub as mappedSub attribute", idpUserId, user.getAttributes().get("mappedSub").get(0));

        String username = user.getUsername();
        assertEquals("Should render alias:sub as Username", bc.getIDPAlias() + "_" + idpUserId, username);
    }
}
