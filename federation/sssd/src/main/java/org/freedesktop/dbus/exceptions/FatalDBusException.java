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
package org.freedesktop.dbus.exceptions;

import org.freedesktop.dbus.interfaces.FatalException;

public class FatalDBusException extends DBusException implements FatalException {

    private static final long serialVersionUID = -3461692622913793488L;

    public FatalDBusException(String _message, Throwable _cause) {
        super(_message, _cause);
    }

    public FatalDBusException(Throwable _cause) {
        super(_cause);
    }

    public FatalDBusException(String _message) {
        super(_message);
    }
}
