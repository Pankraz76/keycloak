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
package org.freedesktop.dbus.interfaces;

import org.freedesktop.dbus.annotations.DBusInterfaceName;

/**
* Objects can provide introspection data via this interface and method.
* See the <a href="http://dbus.freedesktop.org/doc/dbus-specification.html#introspection-format">Introspection Format</a>.
*/
@DBusInterfaceName("org.freedesktop.DBus.Introspectable")
public interface Introspectable extends DBusInterface {
    /**
     * @return The XML introspection data for this object
     */
    //CHECKSTYLE:OFF
    String Introspect();
    //CHECKSTYLE:ON
}
