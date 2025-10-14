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
package org.keycloak.cache;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

class ComputedKey {

    private ComputedKey() {
    }

    public static String computeKey(String realm, String type, String alternativeKey) {
        MessageDigest md = getMessageDigest();
        md.update(realm.getBytes(StandardCharsets.UTF_8));
        md.update(type.getBytes(StandardCharsets.UTF_8));
        md.update(alternativeKey.getBytes(StandardCharsets.UTF_8));
        return new String(md.digest(), StandardCharsets.UTF_8);
    }

    public static String computeKey(String realm, String type, Map<String, String> attributes) {
        MessageDigest md = getMessageDigest();
        md.update(realm.getBytes(StandardCharsets.UTF_8));
        md.update(type.getBytes(StandardCharsets.UTF_8));
        attributes.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(e -> {
            md.update(e.getKey().getBytes(StandardCharsets.UTF_8));
            md.update(e.getValue().getBytes(StandardCharsets.UTF_8));
        });
        return new String(md.digest(), StandardCharsets.UTF_8);
    }

    private static MessageDigest getMessageDigest() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
