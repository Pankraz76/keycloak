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

import org.keycloak.testframework.injection.InstanceContext;
import org.keycloak.testframework.injection.LifeCycle;
import org.keycloak.testframework.injection.RequestedInstance;
import org.keycloak.testframework.injection.Supplier;
import org.keycloak.testframework.injection.SupplierHelpers;
import org.keycloak.testframework.realm.ManagedRealm;
import org.keycloak.testframework.realm.RealmConfigInterceptor;

import java.lang.annotation.Annotation;

@SuppressWarnings("rawtypes")
public abstract class AbstractEventsSupplier<E extends AbstractEvents, A extends Annotation> implements Supplier<E, A>, RealmConfigInterceptor<E, A> {

    @Override
    public E getValue(InstanceContext<E, A> instanceContext) {
        String realmRef = SupplierHelpers.getAnnotationField(instanceContext.getAnnotation(), "realmRef");
        ManagedRealm realm = instanceContext.getDependency(ManagedRealm.class, realmRef);
        return createValue(realm);
    }

    @Override
    public LifeCycle getDefaultLifecycle() {
        return LifeCycle.GLOBAL;
    }

    @Override
    public boolean compatible(InstanceContext<E, A> a, RequestedInstance<E, A> b) {
        return true;
    }

    @Override
    public void onBeforeEach(InstanceContext<E, A> instanceContext) {
        instanceContext.getValue().testStarted();
    }

    @Override
    public void close(InstanceContext<E, A> instanceContext) {
        instanceContext.getValue().clear();
    }

    protected abstract E createValue(ManagedRealm realm);

}
