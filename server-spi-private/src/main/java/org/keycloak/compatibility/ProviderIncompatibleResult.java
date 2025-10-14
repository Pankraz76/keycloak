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
package org.keycloak.compatibility;

import java.util.Optional;
import java.util.Set;

/**
 * Internal class to signal that the provider is not compatible with the previous metadata.
 * <p>
 * It provides information about the provider's ID and the attribute previous and current values.
 */
record ProviderIncompatibleResult(String providerId, String attribute, String previousValue,
                                  String currentValue) implements CompatibilityResult {
    @Override
    public int exitCode() {
        return ExitCode.RECREATE.value();
    }

    @Override
    public Optional<String> errorMessage() {
        return Optional.of("[%s] Rolling Update is not available. '%s.%s' is incompatible: %s -> %s.".formatted(providerId, providerId, attribute, previousValue, currentValue));
    }

    @Override
    public Optional<Set<String>> incompatibleAttributes() {
        return Optional.of(Set.of(attribute));
    }
}
