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

public class MockChildValue {

    private final MockParentValue parent;

    public MockChildValue(MockParentValue parent) {
        MockInstances.INSTANCES.add(this);
        this.parent = parent;
    }

    public MockParentValue getParent() {
        return parent;
    }

    public void close() {
        if (parent.isClosed()) {
            throw new RuntimeException("Parent is closed!");
        }

        boolean removed = MockInstances.INSTANCES.remove(this);
        if (!removed) {
            throw new RuntimeException("Instance already removed");
        } else {
            MockInstances.CLOSED_INSTANCES.add(this);
        }
    }

}
