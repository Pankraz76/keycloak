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
package org.keycloak.testframework.clustering;

import org.keycloak.testframework.annotations.InjectLoadBalancer;
import org.keycloak.testframework.injection.InstanceContext;
import org.keycloak.testframework.injection.RequestedInstance;
import org.keycloak.testframework.injection.Supplier;
import org.keycloak.testframework.injection.SupplierOrder;
import org.keycloak.testframework.server.ClusteredKeycloakServer;
import org.keycloak.testframework.server.KeycloakServer;
import org.keycloak.testframework.server.KeycloakServerConfigBuilder;
import org.keycloak.testframework.server.KeycloakServerConfigInterceptor;

public class LoadBalancerSupplier implements Supplier<LoadBalancer, InjectLoadBalancer>, KeycloakServerConfigInterceptor<LoadBalancer, InjectLoadBalancer> {

    @Override
    public LoadBalancer getValue(InstanceContext<LoadBalancer, InjectLoadBalancer> instanceContext) {
        KeycloakServer server = instanceContext.getDependency(KeycloakServer.class);

        if (server instanceof ClusteredKeycloakServer clusteredKeycloakServer) {
            return new LoadBalancer(clusteredKeycloakServer);
        }

        throw new IllegalStateException("Load balancer can only be used with ClusteredKeycloakServer");
    }

    @Override
    public void close(InstanceContext<LoadBalancer, InjectLoadBalancer> instanceContext) {
        instanceContext.getValue().close();
    }

    @Override
    public boolean compatible(InstanceContext<LoadBalancer, InjectLoadBalancer> a, RequestedInstance<LoadBalancer, InjectLoadBalancer> b) {
        return true;
    }

    @Override
    public int order() {
        return SupplierOrder.BEFORE_REALM;
    }

    @Override
    public KeycloakServerConfigBuilder intercept(KeycloakServerConfigBuilder serverConfig, InstanceContext<LoadBalancer, InjectLoadBalancer> instanceContext) {
        return serverConfig.option("hostname", LoadBalancer.HOSTNAME);
    }
}
