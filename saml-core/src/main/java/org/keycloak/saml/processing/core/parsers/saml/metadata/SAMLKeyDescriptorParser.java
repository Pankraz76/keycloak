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
package org.keycloak.saml.processing.core.parsers.saml.metadata;

import org.keycloak.dom.saml.v2.metadata.KeyDescriptorType;
import org.keycloak.dom.saml.v2.metadata.KeyTypes;
import org.keycloak.saml.common.exceptions.ParsingException;
import org.keycloak.saml.common.util.StaxParserUtil;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.StartElement;

import static org.keycloak.saml.processing.core.parsers.saml.metadata.SAMLMetadataQNames.KEY_DESCRIPTOR;

/**
 * @author mhajas
 */
public class SAMLKeyDescriptorParser extends AbstractStaxSamlMetadataParser<KeyDescriptorType> {

    private static final SAMLKeyDescriptorParser INSTANCE = new SAMLKeyDescriptorParser();

    public SAMLKeyDescriptorParser() {
        super(KEY_DESCRIPTOR);
    }

    public static SAMLKeyDescriptorParser getInstance() {
        return INSTANCE;
    }

    @Override
    protected KeyDescriptorType instantiateElement(XMLEventReader xmlEventReader, StartElement element) throws ParsingException {
        KeyDescriptorType keyDescriptor = new KeyDescriptorType();

        String use = StaxParserUtil.getAttributeValue(element, SAMLMetadataQNames.ATTR_USE);

        if (use != null && !use.isEmpty()) {
            keyDescriptor.setUse(KeyTypes.fromValue(use));
        }

        return keyDescriptor;
    }

    @Override
    protected void processSubElement(XMLEventReader xmlEventReader, KeyDescriptorType target, SAMLMetadataQNames element, StartElement elementDetail) throws ParsingException {
        switch(element) {
            case KEY_INFO:
                target.setKeyInfo(StaxParserUtil.getDOMElement(xmlEventReader));
                break;

            case ENCRYPTION_METHOD:
                target.addEncryptionMethod(SAMLEncryptionMethodParser.getInstance().parse(xmlEventReader));
                break;

            default:
                throw LOGGER.parserUnknownTag(StaxParserUtil.getElementName(elementDetail), elementDetail.getLocation());
        }
    }
}
