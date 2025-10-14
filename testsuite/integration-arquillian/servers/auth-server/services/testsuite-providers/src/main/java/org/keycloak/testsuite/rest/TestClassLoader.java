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
package org.keycloak.testsuite.rest;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class TestClassLoader extends URLClassLoader {

    private static TestClassLoader instance;

    public static TestClassLoader getInstance() {
        if (instance == null) {
            ClassLoader parent = TestClassLoader.class.getClassLoader();
            try {
                instance = new TestClassLoader(parent);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
        return instance;
    }

    private TestClassLoader(ClassLoader parent) throws MalformedURLException {
        super(new URL[] { new URL("http://localhost:8500/") }, parent);
    }

}
