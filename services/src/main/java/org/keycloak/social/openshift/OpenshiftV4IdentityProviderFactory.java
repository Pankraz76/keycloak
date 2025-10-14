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

import org.keycloak.broker.provider.AbstractIdentityProviderFactory;
import org.keycloak.broker.social.SocialIdentityProviderFactory;
import org.keycloak.models.IdentityProviderModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.List;

/**
 * OpenShift 4 Identity Provider factory class.
 *
 * @author David Festal and Sebastian Łaskawiec
 */
public class OpenshiftV4IdentityProviderFactory extends AbstractIdentityProviderFactory<OpenshiftV4IdentityProvider> implements SocialIdentityProviderFactory<OpenshiftV4IdentityProvider> {

    public static final String PROVIDER_ID = "openshift-v4";
    public static final String NAME = "Openshift v4";

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public OpenshiftV4IdentityProvider create(KeycloakSession keycloakSession, IdentityProviderModel identityProviderModel) {
        return new OpenshiftV4IdentityProvider(keycloakSession, new OpenshiftV4IdentityProviderConfig(identityProviderModel));
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public OpenshiftV4IdentityProviderConfig createConfig() {
        return new OpenshiftV4IdentityProviderConfig();
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return OpenshiftV4IdentityProviderConfig.getConfigProperties();
    }
}
