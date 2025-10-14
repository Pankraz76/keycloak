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

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.keycloak.testframework.annotations.KeycloakIntegrationTest;
import org.keycloak.testframework.injection.Registry;

import java.util.Optional;

public class KeycloakIntegrationTestExtension implements BeforeAllCallback, BeforeEachCallback, AfterEachCallback, AfterAllCallback, TestWatcher {

    private static final LogHandler logHandler = new LogHandler();

    @Override
    public void beforeAll(ExtensionContext context) {
        if (isExtensionEnabled(context)) {
            logHandler.beforeAll(context);
        }
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        if (isExtensionEnabled(context)) {
            logHandler.beforeEachStarting(context);
            getRegistry(context).beforeEach(context.getRequiredTestInstance());
            logHandler.beforeEachCompleted(context);
        }
    }

    @Override
    public void afterEach(ExtensionContext context) {
        if (isExtensionEnabled(context)) {
            logHandler.afterEachStarting(context);
            getRegistry(context).afterEach();
            logHandler.afterEachCompleted(context);
        }
    }

    @Override
    public void afterAll(ExtensionContext context) {
        if (isExtensionEnabled(context)) {
            logHandler.afterAll(context);
            getRegistry(context).afterAll();
        }
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        if (isExtensionEnabled(context)) {
            logHandler.testFailed(context);
        }
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        if (isExtensionEnabled(context)) {
            logHandler.testDisabled(context);
        }
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        if (isExtensionEnabled(context)) {
            logHandler.testSuccessful(context);
        }
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        if (isExtensionEnabled(context)) {
            logHandler.testAborted(context);
        }
    }

    private boolean isExtensionEnabled(ExtensionContext context) {
        return context.getRequiredTestClass().isAnnotationPresent(KeycloakIntegrationTest.class);
    }

    private Registry getRegistry(ExtensionContext context) {
        ExtensionContext.Store store = context.getRoot().getStore(ExtensionContext.Namespace.GLOBAL);
        Registry registry = (Registry) store.getOrComputeIfAbsent(Registry.class, r -> new Registry());
        registry.setCurrentContext(context);
        return registry;
    }

}
