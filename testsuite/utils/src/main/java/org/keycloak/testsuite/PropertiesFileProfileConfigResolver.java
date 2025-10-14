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
package org.keycloak.testsuite;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.keycloak.common.profile.ProfileException;
import org.keycloak.common.profile.PropertiesProfileConfigResolver;


public class PropertiesFileProfileConfigResolver extends PropertiesProfileConfigResolver {

    public PropertiesFileProfileConfigResolver() {
        super(loadProperties());
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try {
            String jbossServerConfigDir = System.getProperty("jboss.server.config.dir");
            if (jbossServerConfigDir != null) {
                File file = new File(jbossServerConfigDir, "profile.properties");
                if (file.isFile()) {
                    try (FileInputStream is = new FileInputStream(file)) {
                        properties.load(is);
                    }
                }
            }
        } catch (IOException e) {
            throw new ProfileException("Failed to load profile properties file", e);
        }
        return properties;
    }

}
