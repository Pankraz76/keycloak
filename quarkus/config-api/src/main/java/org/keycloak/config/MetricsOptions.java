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
package org.keycloak.config;

public class MetricsOptions {

    public static final Option<Boolean> METRICS_ENABLED = new OptionBuilder<>("metrics-enabled", Boolean.class)
            .category(OptionCategory.METRICS)
            .description("If the server should expose metrics. If enabled, metrics are available at the '/metrics' endpoint.")
            .buildTime(true)
            .defaultValue(Boolean.FALSE)
            .build();

}
