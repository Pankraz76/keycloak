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
package org.keycloak.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class BasicAuthHelperTest {

    @Test
    public void createHeader() {
        String username = "Aladdin";
        String password = "open sesameopen sesameopen sesameopen sesameopen sesameopen sesame";

        String actual = BasicAuthHelper.createHeader(username, password);
        String expect = "Basic QWxhZGRpbjpvcGVuIHNlc2FtZW9wZW4gc2VzYW1lb3BlbiBzZXNhbWVvcGVuIHNlc2FtZW9wZW4gc2VzYW1lb3BlbiBzZXNhbWU=";

        assertEquals(expect, actual);
    }

    @Test
    public void parseHeader() {
        String username = "Aladdin";
        String password = "open sesameopen sesameopen sesameopen sesameopen sesameopen sesameopen sesame";

        String header = BasicAuthHelper.createHeader(username, password);
        String[] actual = BasicAuthHelper.parseHeader(header);

        assertArrayEquals(new String[] {username, password}, actual);
    }

    @Test
    public void rfc6749_createHeader() {
        String username = "user";
        String password = "secret/with=special?character";

        String actual = BasicAuthHelper.RFC6749.createHeader(username, password);
        String expect = "Basic dXNlcjpzZWNyZXQlMkZ3aXRoJTNEc3BlY2lhbCUzRmNoYXJhY3Rlcg==";

        assertEquals(expect, actual);
    }

    @Test
    public void rfc6749_parseHeader() {
        String username = "user";
        String password = "secret/with=special?character";

        String header = BasicAuthHelper.createHeader(username, password);
        String[] actual = BasicAuthHelper.parseHeader(header);

        assertArrayEquals(new String[] {username, password}, actual);
    }
}
