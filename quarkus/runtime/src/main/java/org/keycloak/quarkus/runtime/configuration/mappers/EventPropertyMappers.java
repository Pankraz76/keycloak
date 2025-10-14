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

import org.keycloak.common.Profile;

import static org.keycloak.config.EventOptions.USER_EVENT_METRICS_ENABLED;
import static org.keycloak.config.EventOptions.USER_EVENT_METRICS_EVENTS;
import static org.keycloak.config.EventOptions.USER_EVENT_METRICS_TAGS;
import static org.keycloak.quarkus.runtime.configuration.Configuration.isTrue;
import static org.keycloak.quarkus.runtime.configuration.mappers.MetricsPropertyMappers.METRICS_ENABLED_MSG;
import static org.keycloak.quarkus.runtime.configuration.mappers.MetricsPropertyMappers.metricsEnabled;
import static org.keycloak.quarkus.runtime.configuration.mappers.PropertyMapper.fromOption;

import java.util.List;


final class EventPropertyMappers implements PropertyMapperGrouping {

    @Override
    public List<PropertyMapper<?>> getPropertyMappers() {
        return List.of(
                fromOption(USER_EVENT_METRICS_ENABLED)
                        .to("kc.spi-events-listener--micrometer-user-event-metrics--enabled")
                        .isEnabled(EventPropertyMappers::userEventsMetricsEnabled, METRICS_ENABLED_MSG + " and feature " + Profile.Feature.USER_EVENT_METRICS.getKey() + " is enabled")
                        .build(),
                fromOption(USER_EVENT_METRICS_TAGS)
                        .to("kc.spi-events-listener--micrometer-user-event-metrics--tags")
                        .paramLabel("tags")
                        .isEnabled(EventPropertyMappers::userEventsMetricsTags, "user event metrics are enabled")
                        .build(),
                fromOption(USER_EVENT_METRICS_EVENTS)
                        .to("kc.spi-events-listener--micrometer-user-event-metrics--events")
                        .paramLabel("events")
                        .isEnabled(EventPropertyMappers::userEventsMetricsTags, "user event metrics are enabled")
                        .build()
        );
    }

    private static boolean userEventsMetricsEnabled() {
        return metricsEnabled() && Profile.isFeatureEnabled(Profile.Feature.USER_EVENT_METRICS);
    }

    private static boolean userEventsMetricsTags() {
        return isTrue(USER_EVENT_METRICS_ENABLED);
    }

}
