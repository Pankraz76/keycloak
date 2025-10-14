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
package org.freedesktop.dbus.errors;

import org.freedesktop.dbus.exceptions.DBusExecutionException;

/**
 * Thrown if a attempt to edit read only property
 */
public class PropertyReadOnly extends DBusExecutionException {
    private static final long serialVersionUID = -8493757965292570003L;

    public PropertyReadOnly(String _message) {
        super(_message);
    }
}
