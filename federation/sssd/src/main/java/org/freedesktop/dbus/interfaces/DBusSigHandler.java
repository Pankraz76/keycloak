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

import org.freedesktop.dbus.messages.DBusSignal;

/**
 * Handle a signal on DBus. All Signal handlers are run in their own Thread. Application writers are responsible for
 * managing any concurrency issues.
 */
public interface DBusSigHandler<T extends DBusSignal> {
    /**
     * Handle a signal.
     *
     * @param _signal The signal to handle. If such a class exists, the signal will be an instance of the class with the
     *            correct type signature. Otherwise it will be an instance of DBusSignal
     */
    void handle(T _signal);
}
