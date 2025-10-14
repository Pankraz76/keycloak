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
package org.freedesktop.dbus.utils;

/**
 * Supplier which allows throwing any exception.
 *
 * @param <V> type which is supplied
 * @param <T> type of exception which gets thrown
 *
 * @author hypfvieh
 * @since v1.3.0 - 2023-01-12
 */
@FunctionalInterface
public interface IThrowingSupplier<V, T extends Throwable> {
    /**
     * Returns the result of the supplier or throws an exception.
     *
     * @return result of supplied function
     * @throws T exception
     */
    V get() throws T;
}
