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
package org.keycloak.testsuite.theme;

import org.keycloak.Config;
import org.keycloak.platform.Platform;
import org.keycloak.provider.EnvironmentDependentProviderFactory;
import org.keycloak.theme.ClasspathThemeResourceProviderFactory;

public class TestThemeResourceProvider extends ClasspathThemeResourceProviderFactory implements EnvironmentDependentProviderFactory {

    public TestThemeResourceProvider() {
        super("test-resources", TestThemeResourceProvider.class.getClassLoader());
    }

    /**
     * Quarkus detects theme resources automatically, so this provider should only be enabled on Undertow
     *
     * @return true if platform is Undertow
     */
    @Override
    public boolean isSupported(Config.Scope config) {
        return Platform.getPlatform().name().equals("Undertow");
    }
}
