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
package org.keycloak.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.binder.cache.CaffeineStatsCounter;
import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

import java.util.concurrent.TimeUnit;

public class DefaultAlternativeLookupProviderFactory implements AlternativeLookupProviderFactory {

    private Cache<String, String> lookupCache;

    @Override
    public String getId() {
        return "default";
    }

    @Override
    public AlternativeLookupProvider create(KeycloakSession session) {
        return new DefaultAlternativeLookupProvider(lookupCache);
    }

    @Override
    public void init(Config.Scope config) {
        Integer maximumSize = config.getInt("maximumSize", 1000);
        Integer expireAfter = config.getInt("expireAfter", 60);

        CaffeineStatsCounter metrics = new CaffeineStatsCounter(Metrics.globalRegistry, "lookup");

        this.lookupCache = Caffeine.newBuilder()
                .maximumSize(maximumSize)
                .expireAfterAccess(expireAfter, TimeUnit.MINUTES)
                .recordStats(() -> metrics)
                .build();

        metrics.registerSizeMetric(lookupCache);
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
    }

    @Override
    public void close() {
        lookupCache.cleanUp();
        lookupCache = null;
    }

}
