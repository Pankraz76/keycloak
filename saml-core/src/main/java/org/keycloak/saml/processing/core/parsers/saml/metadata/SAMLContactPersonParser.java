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

import org.keycloak.dom.saml.v2.metadata.ContactType;
import org.keycloak.dom.saml.v2.metadata.ContactTypeType;
import org.keycloak.saml.common.exceptions.ParsingException;
import org.keycloak.saml.common.util.StaxParserUtil;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.StartElement;

import static org.keycloak.saml.processing.core.parsers.saml.metadata.SAMLMetadataQNames.ATTR_CONTACT_TYPE;
import static org.keycloak.saml.processing.core.parsers.saml.metadata.SAMLMetadataQNames.CONTACT_PERSON;

/**
 * @author mhajas
 */
public class SAMLContactPersonParser extends AbstractStaxSamlMetadataParser<ContactType> {

    private static final SAMLContactPersonParser INSTANCE = new SAMLContactPersonParser();

    public SAMLContactPersonParser() {
        super(CONTACT_PERSON);
    }

    public static SAMLContactPersonParser getInstance() {
        return INSTANCE;
    }

    @Override
    protected ContactType instantiateElement(XMLEventReader xmlEventReader, StartElement element) throws ParsingException {
        return new ContactType(ContactTypeType.fromValue(StaxParserUtil.getRequiredAttributeValue(element, ATTR_CONTACT_TYPE)));
    }

    @Override
    protected void processSubElement(XMLEventReader xmlEventReader, ContactType target, SAMLMetadataQNames element, StartElement elementDetail) throws ParsingException {
        switch (element) {
            case COMPANY:
                StaxParserUtil.advance(xmlEventReader);
                target.setCompany(StaxParserUtil.getElementText(xmlEventReader));
                break;

            case GIVEN_NAME:
                StaxParserUtil.advance(xmlEventReader);
                target.setGivenName(StaxParserUtil.getElementText(xmlEventReader));
                break;

            case SURNAME:
                StaxParserUtil.advance(xmlEventReader);
                target.setSurName(StaxParserUtil.getElementText(xmlEventReader));
                break;

            case EMAIL_ADDRESS:
                StaxParserUtil.advance(xmlEventReader);
                target.addEmailAddress(StaxParserUtil.getElementText(xmlEventReader));
                break;

            case TELEPHONE_NUMBER:
                StaxParserUtil.advance(xmlEventReader);
                target.addTelephone(StaxParserUtil.getElementText(xmlEventReader));
                break;

            case EXTENSIONS:
                target.setExtensions(SAMLExtensionsParser.getInstance().parse(xmlEventReader));
                break;

            default:
                throw LOGGER.parserUnknownTag(StaxParserUtil.getElementName(elementDetail), elementDetail.getLocation());
        }
    }
}
