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
package org.keycloak.authentication;

import org.keycloak.credential.CredentialProvider;
import org.keycloak.credential.CredentialTypeMetadata;
import org.keycloak.credential.CredentialTypeMetadataContext;
import org.keycloak.models.AuthenticationExecutionModel;
import org.keycloak.models.KeycloakSession;

public class AuthenticationSelectionOption {

    private final AuthenticationExecutionModel authExec;
    private final CredentialTypeMetadata credentialTypeMetadata;

    public AuthenticationSelectionOption(KeycloakSession session, AuthenticationExecutionModel authExec) {
        this.authExec = authExec;
        Authenticator authenticator = session.getProvider(Authenticator.class, authExec.getAuthenticator());
        if (authenticator instanceof CredentialValidator) {
            CredentialProvider credentialProvider = ((CredentialValidator) authenticator).getCredentialProvider(session);

            CredentialTypeMetadataContext ctx = CredentialTypeMetadataContext.builder()
                    .build(session);
            credentialTypeMetadata = credentialProvider.getCredentialTypeMetadata(ctx);
        } else {
            credentialTypeMetadata = null;
        }
    }


    public AuthenticationExecutionModel getAuthenticationExecution() {
        return authExec;
    }

    public String getAuthExecId(){
        return authExec.getId();
    }

    public String getDisplayName() {
        return credentialTypeMetadata == null ? authExec.getAuthenticator() + "-display-name" : credentialTypeMetadata.getDisplayName();
    }

    public String getHelpText() {
        return credentialTypeMetadata == null ? authExec.getAuthenticator() + "-help-text" : credentialTypeMetadata.getHelpText();
    }

    public String getIconCssClass() {
        // For now, we won't allow to retrieve "iconCssClass" from the AuthenticatorFactory. We will see in the future if we need
        // this capability for authenticator factories, which authenticators don't implement credentialProvider
        return credentialTypeMetadata == null ? CredentialTypeMetadata.DEFAULT_ICON_CSS_CLASS : credentialTypeMetadata.getIconCssClass();
    }


    @Override
    public String toString() {
        return " authSelection - " + authExec.getAuthenticator();
    }
}
