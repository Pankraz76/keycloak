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
import org.keycloak.testframework.oauth.annotations.InjectTestApp;

public class TestAppSupplier implements Supplier<TestApp, InjectTestApp> {

    @Override
    public TestApp getValue(InstanceContext<TestApp, InjectTestApp> instanceContext) {
        HttpServer httpServer = instanceContext.getDependency(HttpServer.class);
        return new TestApp(httpServer);
    }

    @Override
    public boolean compatible(InstanceContext<TestApp, InjectTestApp> a, RequestedInstance<TestApp, InjectTestApp> b) {
        return true;
    }

    @Override
    public void close(InstanceContext<TestApp, InjectTestApp> instanceContext) {
        instanceContext.getValue().close();
    }

}
