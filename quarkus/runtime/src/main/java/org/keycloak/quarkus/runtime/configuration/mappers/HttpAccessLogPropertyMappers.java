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

import org.keycloak.config.HttpAccessLogOptions;
import org.keycloak.quarkus.runtime.configuration.Configuration;

import static org.keycloak.quarkus.runtime.configuration.mappers.PropertyMapper.fromOption;

import java.util.List;

public class HttpAccessLogPropertyMappers implements PropertyMapperGrouping {
    public static final String ACCESS_LOG_ENABLED_MSG = "HTTP Access log is enabled";

    @Override
    public List<PropertyMapper<?>> getPropertyMappers() {
        return List.of(
                fromOption(HttpAccessLogOptions.HTTP_ACCESS_LOG_ENABLED)
                        .to("quarkus.http.access-log.enabled")
                        .build(),
                fromOption(HttpAccessLogOptions.HTTP_ACCESS_LOG_PATTERN)
                        .isEnabled(HttpAccessLogPropertyMappers::isHttpAccessLogEnabled, ACCESS_LOG_ENABLED_MSG)
                        .to("quarkus.http.access-log.pattern")
                        .build(),
                fromOption(HttpAccessLogOptions.HTTP_ACCESS_LOG_EXCLUDE)
                        .isEnabled(HttpAccessLogPropertyMappers::isHttpAccessLogEnabled, ACCESS_LOG_ENABLED_MSG)
                        .to("quarkus.http.access-log.exclude-pattern")
                        .build()
        );
    }

    static boolean isHttpAccessLogEnabled() {
        return Configuration.isTrue(HttpAccessLogOptions.HTTP_ACCESS_LOG_ENABLED);
    }
}
