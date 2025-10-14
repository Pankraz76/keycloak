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
package org.keycloak.testframework.injection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.LinkedList;
import java.util.List;

public class DefaultAnnotationProxyTest {

    @Test
    public void testGetField() {
        MockAnnotation proxy = DefaultAnnotationProxy.proxy(MockAnnotation.class, "");
        Assertions.assertEquals(LifeCycle.CLASS, proxy.lifecycle());
        Assertions.assertEquals(LinkedList.class, proxy.config());
        Assertions.assertEquals("", proxy.ref());
        Assertions.assertEquals("else", proxy.something());
    }

    @Test
    public void testCustomRef() {
        MockAnnotation proxy = DefaultAnnotationProxy.proxy(MockAnnotation.class, "myref");
        Assertions.assertEquals("myref", proxy.ref());
    }

    @Test
    public void testAnnotationReflection() {
        MockAnnotation proxy = DefaultAnnotationProxy.proxy(MockAnnotation.class, "");
        Assertions.assertEquals(LifeCycle.CLASS, SupplierHelpers.getAnnotationField(proxy, "lifecycle"));
        Assertions.assertEquals(LinkedList.class, SupplierHelpers.getAnnotationField(proxy, "config"));
        Assertions.assertEquals("", SupplierHelpers.getAnnotationField(proxy, "ref"));
        Assertions.assertEquals("else", SupplierHelpers.getAnnotationField(proxy, "something"));
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface MockAnnotation {

        Class<? extends List> config() default LinkedList.class;

        LifeCycle lifecycle() default LifeCycle.CLASS;

        String ref() default "";

        String something() default "else";

    }

}
