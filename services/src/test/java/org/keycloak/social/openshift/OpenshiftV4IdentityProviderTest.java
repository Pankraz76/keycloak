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
package org.keycloak.social.openshift;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.keycloak.broker.provider.IdentityBrokerException;
import org.keycloak.models.IdentityProviderModel;
import org.keycloak.models.KeycloakSession;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class OpenshiftV4IdentityProviderTest {

    private final String TEST_OAUTH_METADATA_FILE = "/org/keycloak/test/social/openshift/OpenshiftV4-oauth-metadata.json";

    private URL oauthMetadataFile;
    private String authMetadata;
    private Map<String, String> oauthMetadataMap;

    @Before
    public void before() throws Exception {
        oauthMetadataFile = OpenshiftV4IdentityProviderTest.class.getResource(TEST_OAUTH_METADATA_FILE);
        authMetadata = IOUtils.toString(oauthMetadataFile, StandardCharsets.UTF_8);

        ObjectMapper objectMapper = new ObjectMapper();
        oauthMetadataMap = objectMapper.readValue(authMetadata, HashMap.class);
    }

    @Test
    public void testExtractingConfigProperties() {
        //given
        OpenshiftV4IdentityProviderConfig config = new OpenshiftV4IdentityProviderConfig(new IdentityProviderModel());

        //when
        new OpenshiftV4IdentityProvider(null, config) {
            @Override
            InputStream getOauthMetadataInputStream(KeycloakSession session, String baseUrl) {
                return new ByteArrayInputStream(authMetadata.getBytes());
            }
        };

        //then
        Assert.assertEquals(OpenshiftV4IdentityProvider.BASE_URL + OpenshiftV4IdentityProvider.PROFILE_RESOURCE, config.getUserInfoUrl());
        Assert.assertEquals(oauthMetadataMap.get("token_endpoint"), config.getTokenUrl());
        Assert.assertEquals(oauthMetadataMap.get("authorization_endpoint"), config.getAuthorizationUrl());
    }

    @Test
    public void testHttpClientErrors() {
        //given
        OpenshiftV4IdentityProviderConfig config = new OpenshiftV4IdentityProviderConfig(new IdentityProviderModel());

        //when
        try {
            new OpenshiftV4IdentityProvider(null, config) {
                @Override
                InputStream getOauthMetadataInputStream(KeycloakSession session, String baseUrl) {
                    throw new RuntimeException("Failed : HTTP error code : 500");
                }
            };
            Assert.fail();
        } catch (IdentityBrokerException e) {
            //then
            //OK
        }
    }

}
