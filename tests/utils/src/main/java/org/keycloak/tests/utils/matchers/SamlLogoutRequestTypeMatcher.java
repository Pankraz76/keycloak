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
package org.keycloak.tests.utils.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.keycloak.dom.saml.v2.SAML2Object;
import org.keycloak.dom.saml.v2.protocol.LogoutRequestType;

import java.net.URI;

import static org.hamcrest.Matchers.is;

/**
 *
 * @author hmlnarik
 */
public class SamlLogoutRequestTypeMatcher extends BaseMatcher<SAML2Object> {

    private final Matcher<URI> destinationMatcher;

    public SamlLogoutRequestTypeMatcher(URI destination) {
        this.destinationMatcher = is(destination);
    }

    public SamlLogoutRequestTypeMatcher(Matcher<URI> destinationMatcher) {
        this.destinationMatcher = destinationMatcher;
    }

    @Override
    public boolean matches(Object item) {
        return destinationMatcher.matches(((LogoutRequestType) item).getDestination());
    }

    @Override
    public void describeMismatch(Object item, Description description) {
        description.appendText("was ").appendValue(((LogoutRequestType) item).getDestination());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("SAML logout request destination matches ").appendDescriptionOf(this.destinationMatcher);
    }
}
