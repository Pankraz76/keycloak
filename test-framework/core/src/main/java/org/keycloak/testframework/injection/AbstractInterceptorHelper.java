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
package org.keycloak.testframework.injection;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractInterceptorHelper<I, V> {

    private final Registry registry;
    private final Class<?> interceptorClass;
    private final List<Interception> interceptions = new LinkedList<>();

    public AbstractInterceptorHelper(Registry registry, Class<I> interceptorClass) {
        this.registry = registry;
        this.interceptorClass = interceptorClass;

        registry.getDeployedInstances().stream().filter(i -> isInterceptor(i.getSupplier())).forEach(i -> interceptions.add(new Interception(i)));
        registry.getRequestedInstances().stream().filter(r -> isInterceptor(r.getSupplier())).forEach(r -> interceptions.add(new Interception(r)));
    }

    public V intercept(V value, InstanceContext<?, ?> instanceContext) {
        for (Interception interception : interceptions) {
            value = intercept(value, interception.supplier, interception.existingInstance);
            registry.getLogger().logIntercepted(value, interception.supplier);
            if (interception.existingInstance != null) {
                interception.existingInstance.registerDependency(instanceContext);
            } else {
                interception.requestedInstance.registerDependency(instanceContext);
            }
        }
        return value;
    }

    public abstract V intercept(V value, Supplier<?, ?> supplier, InstanceContext<?, ?> existingInstance);

    private boolean isInterceptor(Supplier<?, ?> supplier) {
        return interceptorClass.isAssignableFrom(supplier.getClass());
    }

    private static class Interception {

        private final Supplier<?, ?> supplier;
        private final RequestedInstance<?, ?> requestedInstance;
        private final InstanceContext<?, ?> existingInstance;

        public Interception(InstanceContext<?, ?> existingInstance) {
            this.supplier = existingInstance.getSupplier();
            this.requestedInstance = null;
            this.existingInstance = existingInstance;
        }

        public Interception(RequestedInstance<?, ?> requestedInstance) {
            this.supplier = requestedInstance.getSupplier();
            this.requestedInstance = requestedInstance;
            this.existingInstance = null;
        }
    }

}
