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

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

record AggregatedCompatibilityResult(Set<CompatibilityResult> compatibilityResults) implements CompatibilityResult {

    public AggregatedCompatibilityResult(CompatibilityResult compatibilityResult) {
        this(new HashSet<>());
        this.compatibilityResults.add(compatibilityResult);
    }

    public AggregatedCompatibilityResult add(CompatibilityResult a) {
        compatibilityResults.add(a);
        return this;
    }

    @Override
    public int exitCode() {
        return compatibilityResults.stream()
                .anyMatch(r -> r.exitCode() == ExitCode.RECREATE.value())
                ? ExitCode.RECREATE.value() : ExitCode.ROLLING.value();
    }

    @Override
    public Optional<String> errorMessage() {
        StringBuilder sb = new StringBuilder("Aggregated incompatible results:\n");
        for (CompatibilityResult result : compatibilityResults) {
            sb.append(result.errorMessage()).append("\n");
        }
        return Optional.of(sb.toString());
    }

    @Override
    public Optional<Set<String>> incompatibleAttributes() {
        return Optional.of(compatibilityResults.stream()
                .filter(r -> ProviderIncompatibleResult.class.isAssignableFrom(r.getClass()))
                .map(ProviderIncompatibleResult.class::cast)
                .flatMap(r -> r.incompatibleAttributes().orElse(Set.of()).stream())
                .collect(Collectors.toSet()));
    }
}
