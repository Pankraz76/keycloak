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
package org.keycloak.testframework.remote;

import org.keycloak.testframework.annotations.InjectTestDatabase;
import org.keycloak.testframework.database.TestDatabase;
import org.keycloak.testframework.injection.InstanceContext;
import org.keycloak.testframework.injection.LifeCycle;
import org.keycloak.testframework.injection.RequestedInstance;
import org.keycloak.testframework.injection.Supplier;
import org.keycloak.testframework.injection.SupplierOrder;
import org.keycloak.testframework.server.KeycloakServerConfigBuilder;
import org.keycloak.testframework.server.KeycloakServerConfigInterceptor;

public class RemoteProvidersSupplier implements Supplier<RemoteProviders, InjectRemoteProviders>, KeycloakServerConfigInterceptor<TestDatabase, InjectTestDatabase> {

    @Override
    public RemoteProviders getValue(InstanceContext<RemoteProviders, InjectRemoteProviders> instanceContext) {
        return new RemoteProviders();
    }

    @Override
    public LifeCycle getDefaultLifecycle() {
        return LifeCycle.GLOBAL;
    }

    @Override
    public boolean compatible(InstanceContext<RemoteProviders, InjectRemoteProviders> a, RequestedInstance<RemoteProviders, InjectRemoteProviders> b) {
        return true;
    }

    @Override
    public int order() {
        return SupplierOrder.BEFORE_KEYCLOAK_SERVER;
    }

    @Override
    public KeycloakServerConfigBuilder intercept(KeycloakServerConfigBuilder serverConfig, InstanceContext<TestDatabase, InjectTestDatabase> instanceContext) {
        return serverConfig.dependency("org.keycloak.testframework", "keycloak-test-framework-remote-providers");
    }
}
