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
package org.keycloak.dom.saml.v2.mdui;

import java.util.List;

/**
 * <p>
 * Java class for localizedURIType complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 *  &lt;complexType name="KeywordsType">
 *   &lt;simpleContent>
 *     &lt;extension base="mdui:listOfStrings">
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}lang  use="required""/>
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * &lt;simpleType name="listOfStrings">
 *   &lt;list itemType="string"/>
 * &lt;/simpleType>
 * </pre>
 */
public class KeywordsType {

    protected List<String> values;
    protected String lang;

    public KeywordsType(String lang) {
        this.lang = lang;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public String getLang() {
        return lang;
    }



}