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

import java.io.IOException;

/**
 * Exception which indicates a terminated connection.
 *
 * @author hypfvieh
 * @since v4.2.2 - 2023-02-01
 */
public class SocketClosedException extends IOException {
    private static final long serialVersionUID = 1L;

    public SocketClosedException() {
        super();
    }

    public SocketClosedException(String _message, Throwable _cause) {
        super(_message, _cause);
    }

    public SocketClosedException(String _message) {
        super(_message);
    }

    public SocketClosedException(Throwable _cause) {
        super(_cause);
    }

}
