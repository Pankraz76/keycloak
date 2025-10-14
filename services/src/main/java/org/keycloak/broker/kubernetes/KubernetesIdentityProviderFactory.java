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
package org.keycloak.broker.kubernetes;

import org.keycloak.Config;
import org.keycloak.broker.provider.AbstractIdentityProviderFactory;
import org.keycloak.common.Profile;
import org.keycloak.models.IdentityProviderModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.provider.EnvironmentDependentProviderFactory;

import java.util.Map;

import static org.keycloak.broker.kubernetes.KubernetesConstants.KUBERNETES_SERVICE_HOST_KEY;
import static org.keycloak.broker.kubernetes.KubernetesConstants.KUBERNETES_SERVICE_PORT_HTTPS_KEY;

public class KubernetesIdentityProviderFactory extends AbstractIdentityProviderFactory<KubernetesIdentityProvider> implements EnvironmentDependentProviderFactory {

    public static final String PROVIDER_ID = "kubernetes";

    private String globalJwksUrl;

    @Override
    public String getName() {
        return "Kubernetes";
    }

    @Override
    public KubernetesIdentityProvider create(KeycloakSession session, IdentityProviderModel model) {
        return new KubernetesIdentityProvider(session, new KubernetesIdentityProviderConfig(model), globalJwksUrl);
    }

    @Override
    public void init(Config.Scope config) {
        String kubernetesServiceHost = System.getenv(KUBERNETES_SERVICE_HOST_KEY);
        String kubernetesServicePortHttps = System.getenv(KUBERNETES_SERVICE_PORT_HTTPS_KEY);
        if (kubernetesServiceHost != null && kubernetesServicePortHttps != null) {
            globalJwksUrl = "https://" + kubernetesServiceHost + ":" + kubernetesServicePortHttps + "/openid/v1/jwks";
        }
    }

    @Override
    public Map<String, String> parseConfig(KeycloakSession session, String configString) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IdentityProviderModel createConfig() {
        return new KubernetesIdentityProviderConfig();
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public boolean isSupported(Config.Scope config) {
        return Profile.isFeatureEnabled(Profile.Feature.KUBERNETES_SERVICE_ACCOUNTS);
    }

}
