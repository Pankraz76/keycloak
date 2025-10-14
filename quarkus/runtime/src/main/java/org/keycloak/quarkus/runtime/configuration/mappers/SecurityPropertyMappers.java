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
package org.keycloak.quarkus.runtime.configuration.mappers;

import static org.keycloak.quarkus.runtime.configuration.mappers.PropertyMapper.fromOption;

import java.util.List;

import org.keycloak.common.Profile;
import org.keycloak.common.Profile.Feature;
import org.keycloak.common.crypto.FipsMode;
import org.keycloak.config.SecurityOptions;

import io.smallrye.config.ConfigSourceInterceptorContext;

final class SecurityPropertyMappers implements PropertyMapperGrouping {


    @Override
    public List<PropertyMapper<?>> getPropertyMappers() {
        return List.of(
                fromOption(SecurityOptions.FIPS_MODE).transformer(SecurityPropertyMappers::resolveFipsMode)
                        .paramLabel("mode")
                        .build()
        );
    }

    private static String resolveFipsMode(String value, ConfigSourceInterceptorContext context) {
        if (value == null) {
            if (Profile.isFeatureEnabled(Feature.FIPS)) {
                return FipsMode.NON_STRICT.toString();
            }

            return FipsMode.DISABLED.toString();
        }

        return value;
    }
}
