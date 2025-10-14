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
import org.keycloak.dom.saml.v2.protocol.StatusCodeType;
import org.keycloak.dom.saml.v2.protocol.StatusResponseType;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;

/**
 *
 * @author hmlnarik
 */
public class SamlStatusResponseTypeMatcher extends BaseMatcher<SAML2Object> {

    private final List<Matcher<URI>> statusMatchers;

    public SamlStatusResponseTypeMatcher(URI... statusMatchers) {
        this.statusMatchers = new ArrayList(statusMatchers.length);
        for (URI uri : statusMatchers) {
            this.statusMatchers.add(is(uri));
        }
    }

    public SamlStatusResponseTypeMatcher(List<Matcher<URI>> statusMatchers) {
        this.statusMatchers = statusMatchers;
    }

    @Override
    public boolean matches(Object item) {
        StatusCodeType statusCode = ((StatusResponseType) item).getStatus().getStatusCode();
        for (Matcher<URI> statusMatcher : statusMatchers) {
            if (! statusMatcher.matches(statusCode.getValue())) {
                return false;
            }
            statusCode = statusCode.getStatusCode();
        }
        return true;
    }

    @Override
    public void describeMismatch(Object item, Description description) {
        StatusCodeType statusCode = ((StatusResponseType) item).getStatus().getStatusCode();
        description.appendText("was ");
        while (statusCode != null) {
            description.appendText("/").appendValue(statusCode.getValue());
            statusCode = statusCode.getStatusCode();
        }
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("SAML status response status matches ");
        for (Matcher<URI> statusMatcher : statusMatchers) {
            description.appendText("/").appendDescriptionOf(statusMatcher);
        }
    }
}
