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

import java.net.URI;

/**
 * <p>
 * Java class for localizedURIType complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 *  &lt;complexType name="LogoType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>anyURI">
 *       &lt;attribute name="height" type="positiveInteger" use="required""/>
 *       &lt;attribute name="width" type="positiveInteger" use="required""/>
 *       &lt;attribute ref="{http://www.w3.org/XML/1998/namespace}lang "/>
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 */
public class LogoType {

    protected URI value;
    protected int height;
    protected int width;
    protected String lang;

    public LogoType(int height, int width) {
        this.height = height;
        this.width = width;
    }

    /**
     * Gets the value of the value property.
     *
     * @return possible object is {@link String }
     */
    public URI getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     *
     * @param value allowed object is {@link String }
     */
    public void setValue(URI value) {
        this.value = value;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

}