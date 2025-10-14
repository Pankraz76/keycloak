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
package org.keycloak.testframework.https;

import org.keycloak.testframework.injection.InstanceContext;
import org.keycloak.testframework.injection.LifeCycle;
import org.keycloak.testframework.injection.RequestedInstance;
import org.keycloak.testframework.injection.Supplier;
import org.keycloak.testframework.injection.SupplierOrder;

public class CertificatesSupplier implements Supplier<ManagedCertificates, InjectCertificates> {

    @Override
    public ManagedCertificates getValue(InstanceContext<ManagedCertificates, InjectCertificates> instanceContext) {
        return new ManagedCertificates();
    }

    @Override
    public LifeCycle getDefaultLifecycle() {
        return LifeCycle.GLOBAL;
    }

    @Override
    public boolean compatible(InstanceContext<ManagedCertificates, InjectCertificates> a, RequestedInstance<ManagedCertificates, InjectCertificates> b) {
        return true;
    }

    @Override
    public int order() {
        return SupplierOrder.BEFORE_KEYCLOAK_SERVER;
    }
}
