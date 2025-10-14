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
package org.keycloak.testsuite.util.oauth;

import jakarta.ws.rs.core.UriBuilder;
import org.keycloak.protocol.oidc.OIDCLoginProtocolService;
import org.keycloak.protocol.oidc.grants.ciba.CibaGrantType;
import org.keycloak.protocol.oidc.grants.device.DeviceGrantType;
import org.keycloak.protocol.oidc.par.endpoints.ParEndpoint;
import org.keycloak.services.resources.RealmsResource;

public class Endpoints {

    private final String baseUrl;
    private final String realm;

    public Endpoints(String baseUrl, String realm) {
        this.baseUrl = baseUrl;
        this.realm = realm;
    }

    public String getOpenIDConfiguration() {
        return asString(getBase().path(RealmsResource.class).path("{realm}/.well-known/openid-configuration"));
    }

    public String getIssuer() {
        return asString(getBase().path(RealmsResource.class).path("{realm}"));
    }

    public String getAuthorization() {
        return asString(OIDCLoginProtocolService.authUrl(getBase()));
    }

    public String getRegistration() {
        return asString(OIDCLoginProtocolService.registrationsUrl(getBase()));
    }

    public String getToken() {
        return asString(OIDCLoginProtocolService.tokenUrl(getBase()));
    }

    public String getIntrospection() {
        return asString(OIDCLoginProtocolService.tokenIntrospectionUrl(getBase()));
    }

    public String getRevocation() {
        return asString(OIDCLoginProtocolService.tokenRevocationUrl(getBase()));
    }

    public String getUserInfo() {
        return asString(OIDCLoginProtocolService.userInfoUrl(getBase()));
    }

    public String getJwks() {
        return asString(OIDCLoginProtocolService.certsUrl(getBase()));
    }

    public String getDeviceAuthorization() {
        return asString(DeviceGrantType.oauth2DeviceAuthUrl(getBase()));
    }

    public String getPushedAuthorizationRequest() {
        return asString(ParEndpoint.parUrl(getBase()));
    }

    public String getLogout() {
        return asString(OIDCLoginProtocolService.logoutUrl(getBase()));
    }

    public String getBackChannelLogout() {
        return asString(OIDCLoginProtocolService.logoutUrl(getBase()).path("/backchannel-logout"));
    }

    public String getBackchannelAuthentication() {
        return asString(CibaGrantType.authorizationUrl(getBase()));
    }

    public String getBackchannelAuthenticationCallback() {
        return asString(CibaGrantType.authenticationUrl(getBase()));
    }

    UriBuilder getBase() {
        return UriBuilder.fromUri(baseUrl);
    }

    String asString(UriBuilder b) {
        return b.build(realm).toString();
    }

}
