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
package org.keycloak.protocol.saml.mappers;

import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.saml.common.constants.JBossSAMLURIConstants;

import java.util.ArrayList;
import java.util.List;

public class NameIdMapperHelper {

    public static final String NAMEID_MAPPER_CATEGORY = "NameID Mapper";

    // The Class implemented NameIDMapper needs the following attributes.
    public static final String MAPPER_NAMEID_FORMAT = "mapper.nameid.format";
    public static final String MAPPER_NAMEID_FORMAT_LABEL = "name-id-format";
    public static final String MAPPER_NAMEID_FORMAT_HELP_TEXT = "mapper.nameid.format.tooltip";

    public static void setConfigProperties(List<ProviderConfigProperty> configProperties) {
        ProviderConfigProperty property = new ProviderConfigProperty();
        property.setName(NameIdMapperHelper.MAPPER_NAMEID_FORMAT);
        property.setLabel(NameIdMapperHelper.MAPPER_NAMEID_FORMAT_LABEL);
        property.setHelpText(NameIdMapperHelper.MAPPER_NAMEID_FORMAT_HELP_TEXT);
        List<String> types = new ArrayList<String>();
        types.add(JBossSAMLURIConstants.NAMEID_FORMAT_UNSPECIFIED.get());
        types.add(JBossSAMLURIConstants.NAMEID_FORMAT_EMAIL.get());
        types.add(JBossSAMLURIConstants.NAMEID_FORMAT_PERSISTENT.get());
        types.add(JBossSAMLURIConstants.NAMEID_FORMAT_TRANSIENT.get());
        property.setType(ProviderConfigProperty.LIST_TYPE);
        property.setOptions(types);
        configProperties.add(property);
    }
}
