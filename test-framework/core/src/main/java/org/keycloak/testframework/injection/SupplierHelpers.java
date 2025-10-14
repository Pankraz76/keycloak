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

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class SupplierHelpers {

    public static <T> T getInstance(Class<T> clazz) {
        try {
            Constructor<T> declaredConstructor = clazz.getDeclaredConstructor();
            declaredConstructor.setAccessible(true);
            return declaredConstructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getInstance(String clazzName) {
        try {
            Class<T> clazz = (Class<T>) SupplierHelpers.class.getClassLoader().loadClass(clazzName);
            Constructor<T> declaredConstructor = clazz.getDeclaredConstructor();
            declaredConstructor.setAccessible(true);
            return declaredConstructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T getAnnotationField(Annotation annotation, String name, T defaultValue) {
        T value = getAnnotationField(annotation, name);
        return value != null ? value : defaultValue;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getAnnotationField(Annotation annotation, String name) {
        if (annotation != null) {
            for (Method m : annotation.annotationType().getMethods()) {
                if (m.getName().equals(name)) {
                    try {
                        return (T) m.invoke(annotation);
                    } catch (Exception e){
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return null;
    }

    public static String createName(InstanceContext<?, ?> instanceContext) {
        return instanceContext.getRef() != null ? instanceContext.getRef() : "default";
    }

}
