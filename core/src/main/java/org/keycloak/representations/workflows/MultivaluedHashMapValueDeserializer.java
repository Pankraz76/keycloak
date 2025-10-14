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
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.keycloak.common.util.MultivaluedHashMap;

public final class MultivaluedHashMapValueDeserializer extends JsonDeserializer {

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        MultivaluedHashMap<String, String> map = new MultivaluedHashMap<>();
        JsonNode node = p.getCodec().readTree(p);

        if (node.isObject()) {
            for (Entry<String, JsonNode> property : node.properties()) {
                String key = property.getKey();
                JsonNode values = property.getValue();

                if (values.isArray()) {
                    for (JsonNode value : values) {
                        map.add(key, value.asText());
                    }
                } else {
                    map.add(key, values.asText());
                }
            }
        }

        return map;
    }
}
