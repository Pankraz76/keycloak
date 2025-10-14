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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class MethodTuple {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final String name;
    private final String sig;

    public MethodTuple(String _name, String _sig) {
        name = _name;
        if (null != _sig) {
            sig = _sig;
        } else {
            sig = "";
        }
        logger.trace("new MethodTuple({}, {})", name, sig);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, sig);
    }

    @Override
    public boolean equals(Object _obj) {
        if (this == _obj) {
            return true;
        }
        if (!(_obj instanceof MethodTuple)) {
            return false;
        }
        MethodTuple other = (MethodTuple) _obj;
        return Objects.equals(name, other.name) && Objects.equals(sig, other.sig);
    }

    public Logger getLogger() {
        return logger;
    }

    public String getName() {
        return name;
    }

    public String getSig() {
        return sig;
    }
}
