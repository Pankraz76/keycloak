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
package org.keycloak.testframework.oauth;

import com.sun.net.httpserver.HttpServer;
import org.keycloak.testframework.injection.InstanceContext;
import org.keycloak.testframework.injection.RequestedInstance;
import org.keycloak.testframework.injection.Supplier;
import org.keycloak.testframework.injection.SupplierHelpers;
import org.keycloak.testframework.oauth.annotations.InjectOAuthIdentityProvider;

public class OAuthIdentityProviderSupplier implements Supplier<OAuthIdentityProvider, InjectOAuthIdentityProvider> {

    @Override
    public OAuthIdentityProvider getValue(InstanceContext<OAuthIdentityProvider, InjectOAuthIdentityProvider> instanceContext) {
        HttpServer httpServer = instanceContext.getDependency(HttpServer.class);
        OAuthIdentityProviderConfig config = SupplierHelpers.getInstance(instanceContext.getAnnotation().config());
        OAuthIdentityProviderConfigBuilder configBuilder = new OAuthIdentityProviderConfigBuilder();
        OAuthIdentityProviderConfigBuilder.OAuthIdentityProviderConfiguration configuration = config.configure(configBuilder).build();

        return new OAuthIdentityProvider(httpServer, configuration);
    }

    @Override
    public void close(InstanceContext<OAuthIdentityProvider, InjectOAuthIdentityProvider> instanceContext) {
        instanceContext.getValue().close();
    }

    @Override
    public boolean compatible(InstanceContext<OAuthIdentityProvider, InjectOAuthIdentityProvider> a, RequestedInstance<OAuthIdentityProvider, InjectOAuthIdentityProvider> b) {
        return a.getAnnotation().equals(b.getAnnotation());
    }

}
