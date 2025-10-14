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
package org.freedesktop.dbus.messages;

import org.freedesktop.dbus.FileDescriptor;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.types.UInt32;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class MethodBase extends Message {

    MethodBase() {
    }

    public MethodBase(byte _endianness, byte _methodCall, byte _flags) throws DBusException {
        super(_endianness, _methodCall, _flags);
    }

    /**
     * Appends filedescriptors (if any).
     *
     * @param _hargs
     * @param _sig
     * @param _args
     * @throws DBusException
     */
    void appendFileDescriptors(List<Object> _hargs, String _sig, Object... _args) throws DBusException {
        Objects.requireNonNull(_hargs);

        int totalFileDes = _args == null ? 0 : Arrays.stream(_args).filter(x -> x instanceof FileDescriptor).mapToInt(i -> 1).sum();

        if (totalFileDes > 0) {
            _hargs.add(createHeaderArgs(Message.HeaderField.UNIX_FDS, ArgumentType.UINT32_STRING, new UInt32(totalFileDes)));
        }

    }
}
