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

import java.util.List;
import java.util.stream.Collectors;

import org.keycloak.models.ProtocolMapperModel;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.representations.provider.ScriptProviderMetadata;

/**
 * @author <a href="mailto:mposolda@redhat.com">Marek Posolda</a>
 */
public class DeployedScriptSAMLProtocolMapper extends ScriptBasedMapper {

    protected ScriptProviderMetadata metadata;

    public DeployedScriptSAMLProtocolMapper(ScriptProviderMetadata metadata) {
        this.metadata = metadata;
    }

    public DeployedScriptSAMLProtocolMapper() {
        // for reflection
    }

    @Override
    public String getId() {
        return metadata.getId();
    }

    @Override
    public String getDisplayType() {
        return metadata.getName();
    }

    @Override
    public String getHelpText() {
        return metadata.getDescription();
    }

    @Override
    protected String getScriptCode(ProtocolMapperModel mapperModel) {
        return metadata.getCode();
    }

    public List<ProviderConfigProperty> getConfigProperties() {
        return super.getConfigProperties().stream()
                .filter(providerConfigProperty -> !ProviderConfigProperty.SCRIPT_TYPE.equals(providerConfigProperty.getName())) // filter "script" property
                .collect(Collectors.toList());
    }

    public void setMetadata(ScriptProviderMetadata metadata) {
        this.metadata = metadata;
    }

    public ScriptProviderMetadata getMetadata() {
        return metadata;
    }
}
