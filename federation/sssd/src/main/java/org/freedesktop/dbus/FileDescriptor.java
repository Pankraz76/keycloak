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
package org.freedesktop.dbus;

import org.freedesktop.dbus.exceptions.MarshallingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Represents a FileDescriptor to be passed over the bus.  Can be created from
 * either an integer(gotten through some JNI/JNA/JNR call) or from a
 * java.io.FileDescriptor.
 *
 */
public class FileDescriptor {

    private final Logger      logger          = LoggerFactory.getLogger(getClass());

    private final int fd;

    public FileDescriptor(int _fd) {
        fd = _fd;
    }

    // TODO this should have a better exception?
    public FileDescriptor(java.io.FileDescriptor _data) throws MarshallingException {
        fd = getFileDescriptor(_data);
    }

    // TODO this should have a better exception?
    public java.io.FileDescriptor toJavaFileDescriptor() throws MarshallingException {
        return createFileDescriptorByReflection(fd);
    }

    public int getIntFileDescriptor() {
        return fd;
    }

    private int getFileDescriptor(java.io.FileDescriptor _data) throws MarshallingException {
        Field declaredField;
        try {
            declaredField = _data.getClass().getDeclaredField("fd");
            declaredField.setAccessible(true);
            return declaredField.getInt(_data);
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException _ex) {
            logger.error("Could not get filedescriptor by reflection.", _ex);
            throw new MarshallingException("Could not get member 'fd' of FileDescriptor by reflection!", _ex);
        }
    }

    private java.io.FileDescriptor createFileDescriptorByReflection(long _demarshallint) throws MarshallingException {
        try {
            Constructor<java.io.FileDescriptor> constructor = java.io.FileDescriptor.class.getDeclaredConstructor(int.class);
            constructor.setAccessible(true);
            return constructor.newInstance((int) _demarshallint);
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException _ex) {
            logger.error("Could not create new FileDescriptor instance by reflection.", _ex);
            throw new MarshallingException("Could not create new FileDescriptor instance by reflection", _ex);
        }
    }
}
