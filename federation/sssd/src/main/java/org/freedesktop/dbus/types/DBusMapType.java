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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * The type of a map.
 * Should be used whenever you need a Type variable for a map.
 */
public class DBusMapType implements ParameterizedType {
    private final Type k;
    private final Type v;

    /**
    * Create a map type.
    * @param _k The type of the keys.
    * @param _v The type of the values.
    */
    public DBusMapType(Type _k, Type _v) {
        this.k = _k;
        this.v = _v;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return new Type[] {
                k, v
        };
    }

    @Override
    public Type getRawType() {
        return Map.class;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }
}
