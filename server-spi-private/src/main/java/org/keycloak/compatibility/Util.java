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

import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public final class Util {

    private Util() {
    }

    public static <T> Stream<T> mergeKeySet(Map<T, ?> map1, Map<T, ?> map2) {
        return Stream.concat(
                map1.keySet().stream(),
                map2.keySet().stream()
        ).distinct();
    }

    public static CompatibilityResult isCompatible(String provider, Map<String, String> old, Map<String, String> current) {
        return mergeKeySet(old, current)
                .sorted()
                .map(key -> compare(provider, key, old.get(key), current.get(key)))
                .filter(Util::isNotCompatible)
                .reduce((a, b) -> {
                    if (! (a instanceof AggregatedCompatibilityResult)) {
                        a = new AggregatedCompatibilityResult(a);
                    }

                    return ((AggregatedCompatibilityResult) a).add(b);
                })
                .orElse(CompatibilityResult.providerCompatible(provider));
    }

    public static boolean isNotCompatible(CompatibilityResult result) {
        return result.exitCode() != CompatibilityResult.ExitCode.ROLLING.value();
    }

    private static CompatibilityResult compare(String provider, String key, String old, String current) {
        return Objects.equals(old, current) ?
                CompatibilityResult.providerCompatible(provider) :
                CompatibilityResult.incompatibleAttribute(provider, key, old, current);
    }

}
