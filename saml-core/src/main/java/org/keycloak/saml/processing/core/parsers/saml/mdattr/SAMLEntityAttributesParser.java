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
package org.keycloak.saml.processing.core.parsers.saml.mdattr;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.StartElement;

import org.keycloak.dom.saml.v2.mdattr.EntityAttributes;
import org.keycloak.saml.common.exceptions.ParsingException;
import org.keycloak.saml.common.util.StaxParserUtil;
import org.keycloak.saml.processing.core.parsers.saml.assertion.SAMLAssertionParser;
import org.keycloak.saml.processing.core.parsers.saml.assertion.SAMLAttributeParser;
import org.keycloak.saml.processing.core.parsers.saml.metadata.AbstractStaxSamlMetadataParser;
import org.keycloak.saml.processing.core.parsers.saml.metadata.SAMLMetadataQNames;

public class SAMLEntityAttributesParser extends AbstractStaxSamlMetadataParser<EntityAttributes> {
    private static final SAMLEntityAttributesParser INSTANCE = new SAMLEntityAttributesParser();

    private SAMLEntityAttributesParser() {
        super(SAMLMetadataQNames.ENTITY_ATTRIBUTES);
    }

    public static SAMLEntityAttributesParser getInstance() {
        return INSTANCE;
    }

    @Override
    protected EntityAttributes instantiateElement(XMLEventReader xmlEventReader, StartElement element) throws ParsingException {
        return new EntityAttributes();
    }

    @Override
    protected void processSubElement(XMLEventReader xmlEventReader, EntityAttributes target, SAMLMetadataQNames element,
        StartElement elementDetail) throws ParsingException {
        switch (element) {
            case ATTRIBUTE:
                target.addAttribute(SAMLAttributeParser.getInstance().parse(xmlEventReader));
                break;
            case ASSERTION:
                target.addAssertion(SAMLAssertionParser.getInstance().parse(xmlEventReader));
                break;
            default:
                throw LOGGER.parserUnknownTag(StaxParserUtil.getElementName(elementDetail), elementDetail.getLocation());
        }
    }
}
