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

import org.keycloak.dom.saml.v2.metadata.AttributeConsumingServiceType;
import org.keycloak.dom.saml.v2.metadata.LocalizedNameType;
import org.keycloak.saml.common.exceptions.ParsingException;
import org.keycloak.saml.common.util.StaxParserUtil;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.StartElement;

import static org.keycloak.saml.processing.core.parsers.saml.metadata.SAMLMetadataQNames.ATTR_LANG;


/**
 * @author mhajas
 */
public class SAMLAttributeConsumingServiceParser extends AbstractStaxSamlMetadataParser<AttributeConsumingServiceType> {

    private static final SAMLAttributeConsumingServiceParser INSTANCE = new SAMLAttributeConsumingServiceParser();

    public SAMLAttributeConsumingServiceParser() {
        super(SAMLMetadataQNames.ATTRIBUTE_CONSUMING_SERVICE);
    }

    public static SAMLAttributeConsumingServiceParser getInstance() {
        return INSTANCE;
    }

    @Override
    protected AttributeConsumingServiceType instantiateElement(XMLEventReader xmlEventReader, StartElement element) throws ParsingException {
        int index = Integer.parseInt(StaxParserUtil.getRequiredAttributeValue(element, SAMLMetadataQNames.ATTR_INDEX));

        AttributeConsumingServiceType service = new AttributeConsumingServiceType(index);
        service.setIsDefault(StaxParserUtil.getBooleanAttributeValue(element, SAMLMetadataQNames.ATTR_IS_DEFAULT));

        return service;
    }

    @Override
    protected void processSubElement(XMLEventReader xmlEventReader, AttributeConsumingServiceType target, SAMLMetadataQNames element, StartElement elementDetail) throws ParsingException {
        switch (element) {
            case SERVICE_NAME:
                LocalizedNameType serviceName = new LocalizedNameType(StaxParserUtil.getAttributeValue(elementDetail, ATTR_LANG));
                StaxParserUtil.advance(xmlEventReader);
                serviceName.setValue(StaxParserUtil.getElementText(xmlEventReader));
                target.addServiceName(serviceName);
                break;

            case SERVICE_DESCRIPTION:
                LocalizedNameType serviceDescription = new LocalizedNameType(StaxParserUtil.getAttributeValue(elementDetail, ATTR_LANG));
                StaxParserUtil.advance(xmlEventReader);
                serviceDescription.setValue(StaxParserUtil.getElementText(xmlEventReader));
                target.addServiceDescription(serviceDescription);
                break;

            case REQUESTED_ATTRIBUTE:
                target.addRequestedAttribute(SAMLRequestedAttributeParser.getInstance().parse(xmlEventReader));
                break;

            default:
                throw LOGGER.parserUnknownTag(StaxParserUtil.getElementName(elementDetail), elementDetail.getLocation());
        }
    }
}
