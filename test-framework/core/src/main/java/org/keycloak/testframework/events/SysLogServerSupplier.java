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
package org.keycloak.testframework.events;

import org.keycloak.testframework.annotations.InjectSysLogServer;
import org.keycloak.testframework.injection.InstanceContext;
import org.keycloak.testframework.injection.LifeCycle;
import org.keycloak.testframework.injection.RequestedInstance;
import org.keycloak.testframework.injection.Supplier;
import org.keycloak.testframework.injection.SupplierOrder;
import org.keycloak.testframework.server.KeycloakServerConfigBuilder;
import org.keycloak.testframework.server.KeycloakServerConfigInterceptor;

import java.io.IOException;

public class SysLogServerSupplier implements Supplier<SysLogServer, InjectSysLogServer>, KeycloakServerConfigInterceptor<SysLogServer, InjectSysLogServer> {

    @Override
    public SysLogServer getValue(InstanceContext<SysLogServer, InjectSysLogServer> instanceContext) {
        try {
            return new SysLogServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LifeCycle getDefaultLifecycle() {
        return LifeCycle.GLOBAL;
    }

    @Override
    public void close(InstanceContext<SysLogServer, InjectSysLogServer> instanceContext) {
        SysLogServer server = instanceContext.getValue();
        try {
            server.stop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean compatible(InstanceContext<SysLogServer, InjectSysLogServer> a, RequestedInstance<SysLogServer, InjectSysLogServer> b) {
        return true;
    }

    @Override
    public KeycloakServerConfigBuilder intercept(KeycloakServerConfigBuilder serverConfig, InstanceContext<SysLogServer, InjectSysLogServer> instanceContext) {
        serverConfig.log()
                .handlers(KeycloakServerConfigBuilder.LogHandlers.SYSLOG)
                .syslogEndpoint(instanceContext.getValue().getEndpoint())
                .handlerLevel(KeycloakServerConfigBuilder.LogHandlers.SYSLOG, "INFO");

        serverConfig.option("spi-events-listener-jboss-logging-success-level", "INFO")
                .log().categoryLevel("org.keycloak.events", "INFO");

        return serverConfig;
    }

    @Override
    public int order() {
        return SupplierOrder.BEFORE_KEYCLOAK_SERVER;
    }
}
