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
package org.keycloak.protocol.oid4vc.issuance.credentialbuilder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import org.keycloak.common.Profile;
import org.keycloak.common.Profile.Feature;
import org.keycloak.common.crypto.CryptoIntegration;
import org.keycloak.common.crypto.CryptoProvider;
import org.keycloak.common.profile.CommaSeparatedListProfileConfigResolver;
import org.keycloak.models.KeycloakSession;
import org.keycloak.services.resteasy.ResteasyKeycloakSession;
import org.keycloak.services.resteasy.ResteasyKeycloakSessionFactory;

public class CredentialBuilderFactoryTest {

    private static KeycloakSession session;

    @BeforeClass
    public static void beforeClass() {
        Profile.configure(new CommaSeparatedListProfileConfigResolver(Feature.OID4VC_VCI.getVersionedKey(), ""));
        CryptoIntegration.init(CryptoProvider.class.getClassLoader());
        ResteasyKeycloakSessionFactory factory = new ResteasyKeycloakSessionFactory();
        factory.init();
        session = new ResteasyKeycloakSession(factory);
    }

    @Test
    public void testVerifyNonNullConfigProperties() {
        List<CredentialBuilderFactory> credentialBuilderFactories = session
            .getKeycloakSessionFactory()
            .getProviderFactoriesStream(CredentialBuilder.class)
            .filter(CredentialBuilderFactory.class::isInstance)
            .map(CredentialBuilderFactory.class::cast)
            .toList();

        assertThat(credentialBuilderFactories, is(not(empty())));

        for (CredentialBuilderFactory credentialBuilderFactory : credentialBuilderFactories) {
            assertThat(credentialBuilderFactory.getConfigProperties(), notNullValue());
        }
    }
}
