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
package org.keycloak.representations.workflows;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.keycloak.common.util.MultivaluedHashMap;

public final class MultivaluedHashMapValueSerializer extends JsonSerializer<MultivaluedHashMap<String, String>> {

    @Override
    public void serialize(MultivaluedHashMap<String, String> map, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        Set<String> ignoredProperties = getIgnoredProperties(gen);

        gen.writeStartObject();

        for (Entry<String, List<String>> entry : map.entrySet()) {
            String key = entry.getKey();

            if (ignoredProperties.contains(key)) {
                continue;
            }

            List<String> values = entry.getValue();

            if (values.size() == 1) {
                String value = values.get(0);

                if (Boolean.TRUE.toString().equalsIgnoreCase(value) || Boolean.FALSE.toString().equalsIgnoreCase(value)) {
                    gen.writeBooleanField(key, Boolean.parseBoolean(value));
                } else {
                    gen.writeObjectField(key, value);
                }
            } else {
                gen.writeArrayFieldStart(key);
                for (String v : values) {
                    gen.writeString(v);
                }
                gen.writeEndArray();
            }
        }

        gen.writeEndObject();
    }

    private static Set<String> getIgnoredProperties(JsonGenerator gen) {
        Class<?> parentClazz = gen.currentValue().getClass();
        return Arrays.stream(parentClazz.getDeclaredMethods())
                .map(Method::getName)
                .filter(name -> name.startsWith("get"))
                .map(name -> name.substring(3).toLowerCase()).collect(Collectors.toSet());
    }
}
