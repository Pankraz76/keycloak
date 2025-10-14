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

public interface Supplier<T, S extends Annotation> {

    default Class<S> getAnnotationClass() {
        //noinspection unchecked
        return (Class<S>) ReflectionUtils.getAnnotationType(this);
    }

    default Class<T> getValueType() {
        //noinspection unchecked
        return (Class<T>) ReflectionUtils.getValueType(this);
    }

    T getValue(InstanceContext<T, S> instanceContext);

    default String getRef(S annotation) {
        return StringUtil.convertEmptyToNull(SupplierHelpers.getAnnotationField(annotation, AnnotationFields.REF));
    }

    default LifeCycle getLifeCycle(S annotation) {
        return SupplierHelpers.getAnnotationField(annotation, AnnotationFields.LIFECYCLE, getDefaultLifecycle());
    }

    default LifeCycle getDefaultLifecycle() {
        return LifeCycle.CLASS;
    }

    boolean compatible(InstanceContext<T, S> a, RequestedInstance<T, S> b);

    default void close(InstanceContext<T, S> instanceContext) {
    }

    default String getAlias() {
        return getClass().getSimpleName();
    }

    default void onBeforeEach(InstanceContext<T, S> instanceContext) {
    }

    default int order() {
        return SupplierOrder.DEFAULT;
    }

}
