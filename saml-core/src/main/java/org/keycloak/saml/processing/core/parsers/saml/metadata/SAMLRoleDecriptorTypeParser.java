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

import org.keycloak.dom.saml.v2.metadata.RoleDescriptorType;
import org.keycloak.saml.common.exceptions.ParsingException;
import org.keycloak.saml.common.util.StaxParserUtil;
import org.w3c.dom.Element;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.StartElement;

/**
 * @author mhajas
 */
public abstract class SAMLRoleDecriptorTypeParser<T extends RoleDescriptorType> extends AbstractStaxSamlMetadataParser<T> {

    public SAMLRoleDecriptorTypeParser(SAMLMetadataQNames expectedStartElement) {
        super(expectedStartElement);
    }

    protected void parseOptionalArguments(StartElement element, RoleDescriptorType descriptor) throws ParsingException {
        descriptor.setID(StaxParserUtil.getAttributeValue(element, SAMLMetadataQNames.ATTR_ID));
        descriptor.setValidUntil(StaxParserUtil.getXmlTimeAttributeValue(element, SAMLMetadataQNames.ATTR_VALID_UNTIL));
        descriptor.setCacheDuration(StaxParserUtil.getXmlDurationAttributeValue(element, SAMLMetadataQNames.ATTR_CACHE_DURATION));
    }

    @Override
    protected void processSubElement(XMLEventReader xmlEventReader, T target, SAMLMetadataQNames element, StartElement elementDetail) throws ParsingException {
        switch (element) {
            case KEY_DESCRIPTOR:
                target.addKeyDescriptor(SAMLKeyDescriptorParser.getInstance().parse(xmlEventReader));
                break;

            case SIGNATURE:
                Element sig = StaxParserUtil.getDOMElement(xmlEventReader);
                target.setSignature(sig);
                break;

            case EXTENSIONS:
                target.setExtensions(SAMLExtensionsParser.getInstance().parse(xmlEventReader));
                break;

            case ORGANIZATION:
                target.setOrganization(SAMLOrganizationParser.getInstance().parse(xmlEventReader));
                break;

            case CONTACT_PERSON:
                target.addContactPerson(SAMLContactPersonParser.getInstance().parse(xmlEventReader));
                break;

            default:
                throw LOGGER.parserUnknownTag(StaxParserUtil.getElementName(elementDetail), elementDetail.getLocation());
        }
    }
}
