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
package org.freedesktop.dbus.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mark an exported method as ignored.<br>
 * It will not be included in introspection data, and it will not be
 * remotely callable. This is only useful for a local DBus object, it has no meaning to remote objects.
 * <p>
 * Usage:
 * </p>
 * <pre>
 * {@literal @}DBusInterfaceName("com.example.Bar")
 * public interface Bar extends DBusInterface {
 *
 *     {@literal @}DBusIgnore
 *     public void doSomethingInternal() {
 *     }
 * }
 * </pre>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DBusIgnore {
}
