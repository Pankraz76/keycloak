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
package org.keycloak.common.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

public class PathMatcherTest {

    @Test
    public void keycloak15833Test() {
        TestingPathMatcher matcher = new TestingPathMatcher();

        Assert.assertEquals("/api/v1/1/campaigns/*/excelFiles", matcher.customBuildUriFromTemplate("/api/v1/{clientId}/campaigns/*/excelFiles", "/api/v1/1/contentConnectorConfigs/29/contentConnectorContents", false));
    }
    
    private static final class TestingPathMatcher extends PathMatcher<Object> {

        @Override
        protected String getPath(Object entry) {
            return null;
        }

        @Override
        protected Collection<Object> getPaths() {
            return null;
        }

        // Make buildUriFromTemplate accessible from test
        public String customBuildUriFromTemplate(String template, String targetUri, boolean onlyFirstParam) {
            return buildUriFromTemplate(template, targetUri, onlyFirstParam);
        }
    }
}
