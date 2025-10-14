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

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

public class RequestedInstance<T, A extends Annotation> {

    private final int instanceId;
    private final Supplier<T, A> supplier;
    private final A annotation;
    private final Set<InstanceContext<?, ?>> dependencies = new HashSet<>();
    private final Class<? extends T> valueType;
    private final LifeCycle lifeCycle;
    private final String ref;

    public RequestedInstance(Supplier<T, A> supplier, A annotation, Class<? extends T> valueType) {
        this.instanceId = this.hashCode();
        this.supplier = supplier;
        this.annotation = annotation;
        this.valueType = valueType;
        this.lifeCycle = supplier.getLifeCycle(annotation);
        this.ref = StringUtil.convertEmptyToNull(supplier.getRef(annotation));
    }

    public int getInstanceId() {
        return instanceId;
    }

    public Supplier<T, A> getSupplier() {
        return supplier;
    }

    public A getAnnotation() {
        return annotation;
    }

    public Class<? extends T> getValueType() {
        return valueType;
    }

    public LifeCycle getLifeCycle() {
        return lifeCycle;
    }

    public String getRef() {
        return ref;
    }

    public void registerDependency(InstanceContext<?, ?> instanceContext) {
        dependencies.add(instanceContext);
    }

    public Set<InstanceContext<?, ?>> getDependencies() {
        return dependencies;
    }
}
