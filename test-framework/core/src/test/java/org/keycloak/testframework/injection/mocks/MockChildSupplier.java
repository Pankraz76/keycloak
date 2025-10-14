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
package org.keycloak.testframework.injection.mocks;

import org.keycloak.testframework.injection.InstanceContext;
import org.keycloak.testframework.injection.LifeCycle;
import org.keycloak.testframework.injection.RequestedInstance;
import org.keycloak.testframework.injection.Supplier;

public class MockChildSupplier implements Supplier<MockChildValue, MockChildAnnotation> {

    public static LifeCycle DEFAULT_LIFECYCLE = LifeCycle.CLASS;

    public static void reset() {
        DEFAULT_LIFECYCLE = LifeCycle.CLASS;
    }

    @Override
    public MockChildValue getValue(InstanceContext<MockChildValue, MockChildAnnotation> instanceContext) {
        MockParentValue mockParentValue = instanceContext.getDependency(MockParentValue.class, instanceContext.getAnnotation().parentRef());
        return new MockChildValue(mockParentValue);
    }

    @Override
    public boolean compatible(InstanceContext<MockChildValue, MockChildAnnotation> a, RequestedInstance<MockChildValue, MockChildAnnotation> b) {
        return true;
    }

    @Override
    public void close(InstanceContext<MockChildValue, MockChildAnnotation> instanceContext) {
        instanceContext.getValue().close();
    }

    @Override
    public LifeCycle getDefaultLifecycle() {
        return DEFAULT_LIFECYCLE;
    }
}
