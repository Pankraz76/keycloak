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
package org.keycloak.testsuite.arquillian.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SetDefaultProvider {
    String spi();
    String providerId();

    /**
     * <p>Defines whether the default provider should be set by updating an existing Spi configuration.
     *
     * <p>This flag is useful when running the Wildfly distribution and when the server is already configured
     * with a Spi that should only be updated with the default provider.
     *
     * @return {@code true} if the default provider should update an existing Spi configuration. Otherwise, the Spi
     * configuration will be added with the default provider set.
     */
    boolean onlyUpdateDefault() default false;

    /**
     * <p>Defines whether the default provider should be set prior to enabling a feature.
     *
     * <p>This flag should be used together with {@link EnableFeature} so that the default provider
     * is set after enabling a feature. It is useful in case the default provider is not enabled by default,
     * thus requiring the feature to be enabled first.
     *
     * @return {@code true} if the default should be set prior to enabling a feature. Otherwise,
     * the default provider is only set after enabling a feature.
     */
    boolean beforeEnableFeature() default true;

    String defaultProvider() default "";

    /**
     * Configuration for the provider in the form option1, value1, option2, value2
     * @return The config options and values
     */
    String[] config() default {};
}
