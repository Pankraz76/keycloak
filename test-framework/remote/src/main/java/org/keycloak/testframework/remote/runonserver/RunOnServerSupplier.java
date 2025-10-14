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
package org.keycloak.testframework.remote.runonserver;

import org.apache.http.client.HttpClient;
import org.keycloak.testframework.injection.InstanceContext;
import org.keycloak.testframework.injection.LifeCycle;
import org.keycloak.testframework.injection.RequestedInstance;
import org.keycloak.testframework.injection.Supplier;
import org.keycloak.testframework.injection.SupplierOrder;
import org.keycloak.testframework.realm.ManagedRealm;
import org.keycloak.testframework.remote.RemoteProviders;

import java.util.Arrays;
import java.util.HashSet;

public class RunOnServerSupplier implements Supplier<RunOnServerClient, InjectRunOnServer> {

    @Override
    public RunOnServerClient getValue(InstanceContext<RunOnServerClient, InjectRunOnServer> instanceContext) {
        HttpClient httpClient = instanceContext.getDependency(HttpClient.class);
        ManagedRealm realm = instanceContext.getDependency(ManagedRealm.class, instanceContext.getAnnotation().realmRef());
        instanceContext.getDependency(RemoteProviders.class);

        TestClassServer testClassServer = instanceContext.getDependency(TestClassServer.class);
        String[] permittedPackages = instanceContext.getAnnotation().permittedPackages();
        testClassServer.addPermittedPackages(new HashSet<>(Arrays.asList(permittedPackages)));

        return new RunOnServerClient(httpClient, realm.getBaseUrl());
    }

    @Override
    public boolean compatible(InstanceContext<RunOnServerClient, InjectRunOnServer> a, RequestedInstance<RunOnServerClient, InjectRunOnServer> b) {
        return a.getAnnotation().realmRef().equals(b.getAnnotation().realmRef());
    }

    @Override
    public LifeCycle getDefaultLifecycle() {
        return LifeCycle.METHOD;
    }

    @Override
    public int order() {
        return SupplierOrder.BEFORE_KEYCLOAK_SERVER;
    }

}
