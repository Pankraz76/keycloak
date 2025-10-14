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

import org.keycloak.common.Profile;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author mhajas
 */
@Retention(RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Repeatable(EnableFeatures.class)
public @interface EnableFeature {

    /**
     * Feature, which should be enabled.
     */
    Profile.Feature value();

    /**
     * The feature will be enabled without restarting of a server.
     */
    boolean skipRestart() default false;

    /**
     * Feature enable should be the last action in @Before context.
     * If the test halted, the feature is returned to the previous state.
     * If it's false, feature will be enabled before @Before method.
     */
    boolean executeAsLast() default true;
}
