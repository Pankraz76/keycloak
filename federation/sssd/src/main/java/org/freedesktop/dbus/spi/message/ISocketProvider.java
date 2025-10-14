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
package org.freedesktop.dbus.spi.message;

import org.freedesktop.dbus.connections.transports.AbstractTransport;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public interface ISocketProvider {
    /**
     * Method to create a {@link IMessageReader} implementation.
     *
     * @param _socket socket to use for reading
     * @return MessageReader
     * @throws IOException if reader could not be created
     */
    IMessageReader createReader(SocketChannel _socket) throws IOException;

    /**
     * Method to create a {@link IMessageWriter} implementation.
     *
     * @param _socket socket to write to
     * @return MessageWriter
     * @throws IOException if write could not be created
     */
    IMessageWriter createWriter(SocketChannel _socket) throws IOException;

    /**
     * Called to indicate if the current {@link AbstractTransport} implementation
     * supports file descriptor passing.
     *
     * @param _support true if file descriptor passing is supported, false otherwise
     */
    void setFileDescriptorSupport(boolean _support);

    /**
     * Indicate if reader/writer supports file descriptor passing.
     * This is to show if the provider is able to handle file descriptors.
     *
     * @return true if file descriptors are supported by this provider, false otherwise
     */
    boolean isFileDescriptorPassingSupported();
}
