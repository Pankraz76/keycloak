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
package org.freedesktop.dbus.connections;

import java.io.IOException;

/**
 * Callback interface which can be used to get notified about connection losses.
 *
 * @author hypfvieh
 * @version 4.1.0 - 2022-02-03
 */
public interface IDisconnectCallback {
    /**
     * Called when the connection is closed due to a connection error (e.g. stream closed).
     *
     * @param _ex exception which was thrown by transport
     */
    default void disconnectOnError(IOException _ex) {}

    /**
     * Called when the disconnect was intended.
     * @param _connectionId connection Id if this was a shared connection,
     *                      null if last shared or non-shared connection
     */
    default void requestedDisconnect(Integer _connectionId) {}

    /**
     * Called when a client disconnected (only if this is a server/listening connection).
     */
    default void clientDisconnect() {}

    /**
     * Called when the transport throws an exception
     * while connection was already terminating.
     *
     * @param _ex exception which was thrown by transport
     */
    default void exceptionOnTerminate(IOException _ex) {}

}
