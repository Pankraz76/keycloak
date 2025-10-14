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
package org.keycloak.testsuite.runonserver;

import org.keycloak.common.util.Base64;

import java.io.*;

/**
 * Created by st on 26.01.17.
 */
public class SerializationUtil {

    public static String encode(Object function) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(function);
            oos.close();

            return Base64.encodeBytes(os.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object decode(String encoded, ClassLoader classLoader) {
        try {
            byte[] bytes = Base64.decode(encoded);
            ByteArrayInputStream is = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(is) {
                @Override
                protected Class<?> resolveClass(ObjectStreamClass c) throws IOException, ClassNotFoundException {
                    try {
                        return Class.forName(c.getName(), false, classLoader);
                    } catch (ClassNotFoundException e) {
                        throw e;
                    }
                }
            };

            return ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String encodeException(Throwable t) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(t);
            oos.close();

            return "EXCEPTION:" + Base64.encodeBytes(os.toByteArray());
        } catch (NotSerializableException e) {
            // when the exception can't be serialized, at least log the original exception, so it can be analyzed
            throw new RuntimeException("Unable to serialize exception due to not serializable class " + e.getMessage(), t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Throwable decodeException(String result) {
        try {
            result = result.substring("EXCEPTION:".length());
            byte[] bytes = Base64.decode(result);
            ByteArrayInputStream is = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(is);
            return (Throwable) ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
