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

import org.keycloak.dom.saml.v2.metadata.LocalizedNameType;
import org.keycloak.dom.saml.v2.metadata.LocalizedURIType;
import org.keycloak.dom.saml.v2.metadata.OrganizationType;
import org.keycloak.saml.common.exceptions.ParsingException;
import org.keycloak.saml.common.util.StaxParserUtil;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.StartElement;
import java.net.URI;

import static org.keycloak.saml.processing.core.parsers.saml.metadata.SAMLMetadataQNames.ATTR_LANG;
import static org.keycloak.saml.processing.core.parsers.saml.metadata.SAMLMetadataQNames.ORGANIZATION;

/**
 * @author mhajas
 */
public class SAMLOrganizationParser extends AbstractStaxSamlMetadataParser<OrganizationType> {

    private static final SAMLOrganizationParser INSTANCE = new SAMLOrganizationParser();

    public SAMLOrganizationParser() {
        super(ORGANIZATION);
    }

    public static SAMLOrganizationParser getInstance() {
        return INSTANCE;
    }
    @Override
    protected OrganizationType instantiateElement(XMLEventReader xmlEventReader, StartElement element) throws ParsingException {
        return new OrganizationType();
    }

    @Override
    protected void processSubElement(XMLEventReader xmlEventReader, OrganizationType target, SAMLMetadataQNames element, StartElement elementDetail) throws ParsingException {
        switch (element) {
            case ORGANIZATION_NAME:
                LocalizedNameType orgName = new LocalizedNameType(StaxParserUtil.getAttributeValue(elementDetail, ATTR_LANG));
                StaxParserUtil.advance(xmlEventReader);
                orgName.setValue(StaxParserUtil.getElementText(xmlEventReader));
                target.addOrganizationName(orgName);
                break;

            case ORGANIZATION_DISPLAY_NAME:
                LocalizedNameType orgDispName = new LocalizedNameType(StaxParserUtil.getAttributeValue(elementDetail, ATTR_LANG));
                StaxParserUtil.advance(xmlEventReader);
                orgDispName.setValue(StaxParserUtil.getElementText(xmlEventReader));
                target.addOrganizationDisplayName(orgDispName);
                break;

            case ORGANIZATION_URL:
            case ORGANIZATION_URL_ALT:
                LocalizedURIType orgURL = new LocalizedURIType(StaxParserUtil.getAttributeValue(elementDetail, ATTR_LANG));
                StaxParserUtil.advance(xmlEventReader);
                orgURL.setValue(URI.create(StaxParserUtil.getElementText(xmlEventReader)));
                target.addOrganizationURL(orgURL);
                break;

            case EXTENSIONS:
                target.setExtensions(SAMLExtensionsParser.getInstance().parse(xmlEventReader));
                break;

            default:
                throw LOGGER.parserUnknownTag(StaxParserUtil.getElementName(elementDetail), elementDetail.getLocation());
        }
    }
}
