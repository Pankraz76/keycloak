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
package org.keycloak.testframework.conditions;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.keycloak.testframework.injection.Extensions;
import org.keycloak.testframework.injection.Supplier;
import org.keycloak.testframework.injection.SupplierHelpers;

import java.lang.annotation.Annotation;
import java.util.Arrays;

abstract class AbstractDisabledForSupplierCondition implements ExecutionCondition {

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        Extensions extensions = Extensions.getInstance();

        Class<?> valueType = valueType();
        String valueTypeAlias = extensions.getValueTypeAlias().getAlias(valueType);

        Annotation annotation = getAnnotation(context, annotation());
        String[] excludedSuppliers = SupplierHelpers.getAnnotationField(annotation, "value");

        Supplier<?, ?> supplier = extensions.findSupplierByType(valueType);

        boolean excluded = Arrays.asList(excludedSuppliers).contains(supplier.getAlias());

        if (excluded) {
            return ConditionEvaluationResult.disabled("Disabled for " + valueTypeAlias + " " + supplier.getAlias());
        } else {
            return ConditionEvaluationResult.enabled("Enabled for " + valueTypeAlias + " " + supplier.getAlias());
        }
    }

    abstract Class<?> valueType();

    abstract Class<? extends Annotation> annotation();

    private <T extends Annotation> T getAnnotation(ExtensionContext context, Class<T> annotationClass) {
        T[] annotations = context.getElement().get().getAnnotationsByType(annotationClass);
        if (annotations.length == 0) {
            annotations = context.getParent().get().getElement().get().getAnnotationsByType(annotationClass);
        }
        return annotations[0];
    }
}
