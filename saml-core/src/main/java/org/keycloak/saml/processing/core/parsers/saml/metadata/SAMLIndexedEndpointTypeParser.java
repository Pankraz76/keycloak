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

import org.keycloak.dom.saml.v2.metadata.IndexedEndpointType;
import org.keycloak.saml.common.exceptions.ParsingException;
import org.keycloak.saml.common.util.StaxParserUtil;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.StartElement;
import java.net.URI;

/**
 * @author mhajas
 */
public abstract class SAMLIndexedEndpointTypeParser extends AbstractStaxSamlMetadataParser<IndexedEndpointType> {

    public SAMLIndexedEndpointTypeParser(SAMLMetadataQNames expectedStartElement) {
        super(expectedStartElement);
    }

    @Override
    protected IndexedEndpointType instantiateElement(XMLEventReader xmlEventReader, StartElement element) throws ParsingException {
        String binding = StaxParserUtil.getRequiredAttributeValue(element, SAMLMetadataQNames.ATTR_BINDING);
        String location = StaxParserUtil.getRequiredAttributeValue(element, SAMLMetadataQNames.ATTR_LOCATION);

        IndexedEndpointType endpoint = new IndexedEndpointType(URI.create(binding), URI.create(location));

        Boolean isDefault = StaxParserUtil.getBooleanAttributeValue(element, SAMLMetadataQNames.ATTR_IS_DEFAULT);
        if (isDefault != null) {
            endpoint.setIsDefault(isDefault);
        }
        
        Integer index = StaxParserUtil.getIntegerAttributeValue(element, SAMLMetadataQNames.ATTR_INDEX);
        if (index != null)
            endpoint.setIndex(index);

        // EndpointType attributes
        String responseLocation = StaxParserUtil.getAttributeValue(element, SAMLMetadataQNames.ATTR_RESPONSE_LOCATION);

        if (responseLocation != null) {
            endpoint.setResponseLocation(URI.create(responseLocation));
        }

        return endpoint;
    }

    @Override
    protected void processSubElement(XMLEventReader xmlEventReader, IndexedEndpointType target, SAMLMetadataQNames element, StartElement elementDetail) throws ParsingException {
        throw LOGGER.parserUnknownTag(StaxParserUtil.getElementName(elementDetail), elementDetail.getLocation());
    }
}
