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
package org.keycloak.testframework.annotations;

import org.keycloak.testframework.injection.LifeCycle;
import org.keycloak.testframework.realm.ClientConfig;
import org.keycloak.testframework.realm.DefaultClientConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InjectClient {

    Class<? extends ClientConfig> config() default DefaultClientConfig.class;

    LifeCycle lifecycle() default LifeCycle.CLASS;

    String ref() default "";

    String realmRef() default "";

    /**
     * Attach to an existing client instead of creating one; when attaching to an existing client the config will be ignored
     * and the client will not be deleted automatically.
     *
     * @return the client-id of the existing client to attach to
     */
    String attachTo() default "";

}
