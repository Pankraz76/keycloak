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

import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.exceptions.IllegalThreadPoolStateException;
import org.freedesktop.dbus.interfaces.FatalException;
import org.freedesktop.dbus.messages.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.RejectedExecutionException;

public class IncomingMessageThread extends Thread {
    private final Logger             logger = LoggerFactory.getLogger(getClass());

    private volatile boolean         terminate;
    private final AbstractConnection connection;

    public IncomingMessageThread(AbstractConnection _connection, BusAddress _busAddress) {
        Objects.requireNonNull(_connection);
        connection = _connection;
        setName("DBusConnection [listener=" + _busAddress.isListeningSocket() + "]");
        setDaemon(true);
    }

    public void terminate() {
        terminate = true;
        interrupt();
    }

    @Override
    public void run() {

        Message msg;
        while (!terminate) {
            msg = null;

            // read from the wire
            try {
                // this blocks on outgoing being non-empty or a message being available.
                msg = connection.readIncoming();
                if (msg != null) {
                    logger.trace("Got Incoming Message: {}", msg);

                    connection.handleMessage(msg);
                }
            } catch (DBusException | RejectedExecutionException | IllegalThreadPoolStateException _ex) {
                if (_ex instanceof FatalException) {
                    if (terminate) { // requested termination, ignore failures
                        return;
                    }
                    logger.error("FatalException in connection thread", _ex);
                    if (connection.isConnected()) {
                        terminate = true;
                        if (_ex.getCause() instanceof IOException) {
                            connection.internalDisconnect((IOException) _ex.getCause());
                        } else {
                            connection.internalDisconnect(null);
                        }
                    }
                    return;
                }

                if (!terminate) { // only log exceptions if the connection was not intended to be closed
                    logger.error("Exception in connection thread", _ex);
                }
            }
        }
    }
}
