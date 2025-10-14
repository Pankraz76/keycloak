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
package org.freedesktop.dbus.types;

import org.freedesktop.dbus.Struct;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * The type of a struct.
 * Should be used whenever you need a Type variable for a struct.
 */
public class DBusStructType implements ParameterizedType {
    private final Type[] contents;

    /**
    * Create a struct type.
    * @param _contents The types contained in this struct.
    */
    public DBusStructType(Type... _contents) {
        this.contents = _contents;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return contents;
    }

    @Override
    public Type getRawType() {
        return Struct.class;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }
}
