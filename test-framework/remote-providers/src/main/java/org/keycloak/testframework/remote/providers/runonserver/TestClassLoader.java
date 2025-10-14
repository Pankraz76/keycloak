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
package org.keycloak.testframework.remote.providers.runonserver;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class TestClassLoader extends URLClassLoader {

    private static final String testClassUrl = "http://localhost:8500/test-classes/";

    public TestClassLoader() throws MalformedURLException {
        super(new URL[] { new URL(testClassUrl)}, TestClassLoader.class.getClassLoader());
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> loadClass = super.loadClass(name, resolve);

        // When running in embedded mode all classes available on the test side are available in the system classloader.
        // However, these will also load classes from the system classloader instead of the Quarkus classloader. To
        // prevent this we are detecting if a class was loaded by the system classloader, and if it was we're forcing
        // it to load the class from the remote test class provider.
        if (loadClass != null && loadClass.getClassLoader() != null && loadClass.getClassLoader().equals(ClassLoader.getSystemClassLoader())) {
            return findClass(name);
        } else {
            return loadClass;
        }
    }
}
