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
package org.keycloak.testframework.remote;

import org.keycloak.testframework.TestFrameworkExtension;
import org.keycloak.testframework.injection.Supplier;
import org.keycloak.testframework.remote.timeoffset.TimeOffsetSupplier;
import org.keycloak.testframework.remote.runonserver.RunOnServerSupplier;
import org.keycloak.testframework.remote.runonserver.TestClassServerSupplier;

import java.util.List;

public class RemoteTestFrameworkExtension implements TestFrameworkExtension {
    @Override
    public List<Supplier<?, ?>> suppliers() {
        return List.of(
                new TimeOffsetSupplier(),
                new RunOnServerSupplier(),
                new RemoteProvidersSupplier(),
                new TestClassServerSupplier()
        );
    }

    @Override
    public List<Class<?>> alwaysEnabledValueTypes() {
        return List.of(RemoteProviders.class);
    }
}
