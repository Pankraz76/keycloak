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
package org.keycloak.broker.spiffe;

import org.keycloak.models.IdentityProviderModel;
import org.keycloak.models.IdentityProviderShowInAccountConsole;
import org.keycloak.models.RealmModel;

import java.util.regex.Pattern;

import static org.keycloak.common.util.UriUtils.checkUrl;

public class SpiffeIdentityProviderConfig extends IdentityProviderModel {

    public static final String BUNDLE_ENDPOINT_KEY = "bundleEndpoint";

    private static final Pattern TRUST_DOMAIN_PATTERN = Pattern.compile("spiffe://[a-z0-9.\\-_]*");

    public SpiffeIdentityProviderConfig() {
        getConfig().put(IdentityProviderModel.SHOW_IN_ACCOUNT_CONSOLE, IdentityProviderShowInAccountConsole.NEVER.name());
    }

    public SpiffeIdentityProviderConfig(IdentityProviderModel model) {
        super(model);
    }

    @Override
    public boolean isHideOnLogin() {
        return true;
    }

    public int getAllowedClockSkew() {
        String allowedClockSkew = getConfig().get(ALLOWED_CLOCK_SKEW);
        if (allowedClockSkew == null || allowedClockSkew.isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(getConfig().get(ALLOWED_CLOCK_SKEW));
        } catch (NumberFormatException e) {
            // ignore it and use default
            return 0;
        }
    }

    public String getTrustDomain() {
        return getConfig().get(ISSUER);
    }

    public String getBundleEndpoint() {
        return getConfig().get(BUNDLE_ENDPOINT_KEY);
    }

    @Override
    public void validate(RealmModel realm) {
        super.validate(realm);

        String trustDomain = getTrustDomain();
        if (trustDomain == null || !TRUST_DOMAIN_PATTERN.matcher(trustDomain).matches()) {
            throw new IllegalArgumentException("Invalid trust domain name");
        }

        checkUrl(realm.getSslRequired(), getBundleEndpoint(), BUNDLE_ENDPOINT_KEY);
    }
}
