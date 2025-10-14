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

public class HtmlUtilsTest {

  @Test
  public void escapeAttribute() {
    Assert.assertEquals("1&lt;2", HtmlUtils.escapeAttribute("1<2"));
    Assert.assertEquals("2&lt;3&amp;&amp;3&gt;2", HtmlUtils.escapeAttribute("2<3&&3>2") );
    Assert.assertEquals("test", HtmlUtils.escapeAttribute("test"));
    Assert.assertEquals("&apos;test&apos;", HtmlUtils.escapeAttribute("\'test\'"));
    Assert.assertEquals("&quot;test&quot;", HtmlUtils.escapeAttribute("\"test\""));
  }
}
