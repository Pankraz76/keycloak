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
package org.keycloak.testframework.http;

import com.sun.net.httpserver.HttpServer;
import org.keycloak.testframework.annotations.InjectHttpServer;
import org.keycloak.testframework.injection.InstanceContext;
import org.keycloak.testframework.injection.LifeCycle;
import org.keycloak.testframework.injection.RequestedInstance;
import org.keycloak.testframework.injection.Supplier;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpServerSupplier implements Supplier<HttpServer, InjectHttpServer> {

    @Override
    public HttpServer getValue(InstanceContext<HttpServer, InjectHttpServer> instanceContext) {
        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress("127.0.0.1", 8500), 10);
            httpServer.start();
            return httpServer;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close(InstanceContext<HttpServer, InjectHttpServer> instanceContext) {
        instanceContext.getValue().stop(0);
    }

    @Override
    public boolean compatible(InstanceContext<HttpServer, InjectHttpServer> a, RequestedInstance<HttpServer, InjectHttpServer> b) {
        return true;
    }

    @Override
    public LifeCycle getDefaultLifecycle() {
        return LifeCycle.GLOBAL;
    }

}
