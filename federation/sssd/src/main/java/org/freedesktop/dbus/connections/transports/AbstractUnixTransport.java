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
package org.freedesktop.dbus.connections.transports;

import org.freedesktop.dbus.connections.BusAddress;
import org.freedesktop.dbus.connections.config.TransportConfig;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public abstract class AbstractUnixTransport extends AbstractTransport {

    protected AbstractUnixTransport(BusAddress _address, TransportConfig _config) {
        super(_address, _config);
    }

    public abstract int getUid(SocketChannel _sock) throws IOException;

}
