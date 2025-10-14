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
package org.keycloak.testframework;

import org.infinispan.util.function.SerializableComparator;
import org.junit.jupiter.api.ClassDescriptor;
import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.ClassOrdererContext;
import org.keycloak.testframework.annotations.KeycloakIntegrationTest;

import java.util.Optional;

public class ServerConfigClassOrderer implements ClassOrderer {

    @Override
    public void orderClasses(ClassOrdererContext classOrdererContext) {
        classOrdererContext.getClassDescriptors().sort(new ServerConfigComparator());
    }

    static class ServerConfigComparator implements SerializableComparator<ClassDescriptor> {

        @Override
        public int compare(ClassDescriptor o1, ClassDescriptor o2) {
            Optional<KeycloakIntegrationTest> a1 = o1.findAnnotation(KeycloakIntegrationTest.class);
            Optional<KeycloakIntegrationTest> a2 = o2.findAnnotation(KeycloakIntegrationTest.class);

            if (a1.isPresent() && a2.isPresent()) {
                return a1.get().config().getName().compareTo(a2.get().config().getName());
            } else if (a1.isPresent()) {
                return 1;
            } else if (a2.isPresent()) {
                return 2;
            } else {
                return 0;
            }
        }

    }

}
