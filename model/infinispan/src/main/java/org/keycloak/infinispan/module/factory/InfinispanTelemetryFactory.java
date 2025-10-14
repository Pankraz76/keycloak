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
package org.keycloak.infinispan.module.factory;

import io.opentelemetry.api.OpenTelemetry;
import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.spi.CDI;
import org.infinispan.factories.AbstractComponentFactory;
import org.infinispan.factories.AutoInstantiableFactory;
import org.infinispan.factories.annotations.DefaultFactoryFor;
import org.infinispan.factories.scopes.Scope;
import org.infinispan.factories.scopes.Scopes;
import org.infinispan.telemetry.InfinispanTelemetry;
import org.infinispan.telemetry.impl.DisabledInfinispanTelemetry;

@Scope(Scopes.GLOBAL)
@DefaultFactoryFor(classes = InfinispanTelemetry.class)
public class InfinispanTelemetryFactory extends AbstractComponentFactory implements AutoInstantiableFactory {

    @Override
    public Object construct(String componentName) {
        CDI<Object> current;
        try {
            current = CDI.current();
        } catch (IllegalStateException e) {
            // No CDI context, assume tracing is not available
            return new DisabledInfinispanTelemetry();
        }
        Instance<OpenTelemetry> selector = current.select(OpenTelemetry.class);
        if (!selector.isResolvable()) {
            return new DisabledInfinispanTelemetry();
        } else {
            return new OpenTelemetryService(selector.get());
        }
    }
}
