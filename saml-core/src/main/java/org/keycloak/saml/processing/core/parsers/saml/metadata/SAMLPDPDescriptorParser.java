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

import org.keycloak.dom.saml.v2.metadata.PDPDescriptorType;
import org.keycloak.saml.common.exceptions.ParsingException;
import org.keycloak.saml.common.util.StaxParserUtil;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.StartElement;
import java.util.List;

/**
 * @author mhajas
 */
public class SAMLPDPDescriptorParser extends SAMLRoleDecriptorTypeParser<PDPDescriptorType> {

    private static final SAMLPDPDescriptorParser INSTANCE = new SAMLPDPDescriptorParser();

    public SAMLPDPDescriptorParser() {
        super(SAMLMetadataQNames.PDP_DESCRIPTOR);
    }

    public static SAMLPDPDescriptorParser getInstance() {
        return INSTANCE;
    }

    @Override
    protected PDPDescriptorType instantiateElement(XMLEventReader xmlEventReader, StartElement element) throws ParsingException {
        List<String> protocolEnum = StaxParserUtil.getRequiredStringListAttributeValue(element, SAMLMetadataQNames.ATTR_PROTOCOL_SUPPORT_ENUMERATION);
        PDPDescriptorType descriptor = new PDPDescriptorType(protocolEnum);

        parseOptionalArguments(element, descriptor);

        return descriptor;
    }

    @Override
    protected void processSubElement(XMLEventReader xmlEventReader, PDPDescriptorType target, SAMLMetadataQNames element, StartElement elementDetail) throws ParsingException {
        switch (element) {
            case AUTHZ_SERVICE:
            target.addAuthZService(SAMLAuthzServiceParser.getInstance().parse(xmlEventReader));
            break;

        case ASSERTION_ID_REQUEST_SERVICE:
            target.addAssertionIDRequestService(SAMLAssertinIDRequestServiceParser.getInstance().parse(xmlEventReader));
            break;

        case NAMEID_FORMAT:
            StaxParserUtil.advance(xmlEventReader);
            target.addNameIDFormat(StaxParserUtil.getElementText(xmlEventReader));
            break;

        default:
            super.processSubElement(xmlEventReader, target, element, elementDetail);
        }
    }
}
