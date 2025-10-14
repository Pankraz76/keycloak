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
package org.keycloak.adapters.saml.descriptor.parsers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.InputStream;
import org.junit.Test;
import org.keycloak.common.util.MultivaluedHashMap;
import org.keycloak.saml.common.exceptions.ParsingException;

public class SamlDescriptorIDPKeysExtractorTest {

  @Test
  public void testParsingFileContainingEntityDescriptorAsRootElement() {
    testParse("saml-idp-metadata-with-entity-descriptor-as-root-element.xml");
  }

  @Test
  public void testParsingFileContainingEntitiesDescriptorAsRootElement() {
    testParse("saml-idp-metadata-with-entities-descriptor-as-root-element.xml");
  }


  public void testParse(String fileToParse) {
    InputStream stream = getClass().getResourceAsStream(fileToParse);
    SamlDescriptorIDPKeysExtractor extractor = new SamlDescriptorIDPKeysExtractor();
    try {
      MultivaluedHashMap keyMap = extractor.parse(stream);
      assertFalse(keyMap.isEmpty());
      assertTrue(keyMap.containsKey("signing"));
      assertTrue(keyMap.containsKey("encryption"));
    } catch (ParsingException e) {
      fail(e.getMessage());
    }
  }


}
