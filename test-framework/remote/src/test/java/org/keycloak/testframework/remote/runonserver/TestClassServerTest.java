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
package org.keycloak.testframework.remote.runonserver;

import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.keycloak.representations.idm.RealmRepresentation;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Set;

public class TestClassServerTest {

    private static HttpServer SERVER;

    @BeforeAll
    public static void setupServer() throws IOException {
        SERVER = HttpServer.create(new InetSocketAddress("127.0.0.1", 8500), 10);
        SERVER.start();
    }

    @AfterAll
    public static void closeServer() {
        SERVER.stop(0);
    }

    @Test
    public void testPermittedPackage() throws ClassNotFoundException, MalformedURLException {
        TestClassServer server = new TestClassServer(SERVER);
        server.addPermittedPackages(Set.of("org.keycloak.representations.idm"));

        URLClassLoader urlClassLoader = new URLClassLoader(new URL[] { new URL("http://localhost:8500/test-classes/") }, null);
        Assertions.assertNotNull(urlClassLoader.loadClass(RealmRepresentation.class.getName()));

        server.close();
    }

    @Test
    public void testInvalidPackage() throws MalformedURLException {
        TestClassServer server = new TestClassServer(SERVER);

        URLClassLoader urlClassLoader = new URLClassLoader(new URL[] { new URL("http://localhost:8500/test-classes/") }, null);
        Assertions.assertThrows(ClassNotFoundException.class, () -> urlClassLoader.loadClass(RealmRepresentation.class.getName()));

        server.close();
    }

}
