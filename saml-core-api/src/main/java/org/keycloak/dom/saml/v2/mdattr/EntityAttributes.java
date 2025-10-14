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
package org.keycloak.dom.saml.v2.mdattr;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.keycloak.dom.saml.v2.assertion.AssertionType;
import org.keycloak.dom.saml.v2.assertion.AttributeType;

/**
 *
 * *
 * <p>
 * Java class for EntityAttributes complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
* 	&lt;element name="EntityAttributes" type="mdattr:EntityAttributesType"/>
* 	&lt;complexType name="EntityAttributesType">
* 		&lt;choice maxOccurs="unbounded">
* 			&lt;element ref="saml:Attribute"/>
* 			&lt;element ref="saml:Assertion"/>
* 		&lt;/sequence>
* 	&lt;/complexType>
 *
 * </pre>
 *
 */

public class EntityAttributes implements Serializable {

    protected List<AttributeType> attribute = new ArrayList<>();
    protected List<AssertionType> assertion = new ArrayList<>();

    public List<AttributeType> getAttribute() {
        return attribute;
    }

    public void addAttribute(AttributeType attributeType) {
        attribute.add(attributeType);
    }

    public void removeAttribute(AttributeType attributeType) {
        attribute.remove(attributeType);
    }

    public List<AssertionType> getAssertion() {
        return assertion;
    }

    public void addAssertion(AssertionType assertionType) {
        assertion.add(assertionType);
    }

    public void removeAssertion(AssertionType assertionType) {
        assertion.remove(assertionType);
    }

}
