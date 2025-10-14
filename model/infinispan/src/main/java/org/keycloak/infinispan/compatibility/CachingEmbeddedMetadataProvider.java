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
package org.keycloak.infinispan.compatibility;

import java.util.Map;
import java.util.stream.Stream;

import org.infinispan.commons.util.Version;
import org.keycloak.Config;
import org.keycloak.compatibility.AbstractCompatibilityMetadataProvider;
import org.keycloak.infinispan.util.InfinispanUtils;
import org.keycloak.spi.infinispan.CacheEmbeddedConfigProviderSpi;
import org.keycloak.spi.infinispan.impl.embedded.DefaultCacheEmbeddedConfigProviderFactory;

public class CachingEmbeddedMetadataProvider extends AbstractCompatibilityMetadataProvider {

    public CachingEmbeddedMetadataProvider() {
        super(CacheEmbeddedConfigProviderSpi.SPI_NAME, DefaultCacheEmbeddedConfigProviderFactory.PROVIDER_ID);
    }

    @Override
    protected boolean isEnabled(Config.Scope scope) {
        return InfinispanUtils.isEmbeddedInfinispan();
    }

    @Override
    public Map<String, String> customMeta() {
        return Map.of(
              "version", Version.getVersion(),
              "jgroupsVersion", org.jgroups.Version.printVersion()
        );
    }

    @Override
    public Stream<String> configKeys() {
        return Stream.of(DefaultCacheEmbeddedConfigProviderFactory.CONFIG, DefaultCacheEmbeddedConfigProviderFactory.STACK);
    }
}
