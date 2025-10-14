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
package org.keycloak.saml.processing.core.util;

/**
 * Constants copied from XMLConstants to work around issues with IntelliJ
 *
 * See https://issues.redhat.com/browse/KEYCLOAK-19403
 */
public class FixXMLConstants {

    public static final String ACCESS_EXTERNAL_DTD = "http://javax.xml.XMLConstants/property/accessExternalDTD";

    public static final String ACCESS_EXTERNAL_SCHEMA = "http://javax.xml.XMLConstants/property/accessExternalSchema";

    public static final String ACCESS_EXTERNAL_STYLESHEET = "http://javax.xml.XMLConstants/property/accessExternalStylesheet";

}
