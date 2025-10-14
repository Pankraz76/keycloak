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
package org.keycloak.provider;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class ProviderConfigurationBuilderTest {

  @Test
  public void testAddProperty() {
    ProviderConfigurationBuilder builder = ProviderConfigurationBuilder.create();

    builder.property()
        .name("property1")
        .label("Property 1")
        .helpText("Help text for property 1")
        .type("string")
        .defaultValue("default1")
        .add();

    builder.property()
        .name("property2")
        .label("Property 2")
        .helpText("Help text for property 2")
        .type("int")
        .defaultValue(10)
        .add();

    List<ProviderConfigProperty> properties = builder.build();

    Assert.assertEquals(2, properties.size());
    Assert.assertEquals("property1", properties.get(0).getName());
    Assert.assertEquals("Property 1", properties.get(0).getLabel());
    Assert.assertEquals("default1", properties.get(0).getDefaultValue());

    Assert.assertEquals("property2", properties.get(1).getName());
    Assert.assertEquals(10, properties.get(1).getDefaultValue());
  }

  @Test
  public void testDuplicatePropertyNameThrowsException() {
    ProviderConfigurationBuilder builder = ProviderConfigurationBuilder.create();

    builder.property()
        .name("property1")
        .label("Property 1")
        .helpText("Help text for property 1")
        .type("string")
        .defaultValue("default1")
        .add();

    ProviderConfigPropertyNameNotUniqueException exception = Assert.assertThrows(
        ProviderConfigPropertyNameNotUniqueException.class,
        () -> builder.property()
            .name("property1")
            .label("Duplicate Property 1")
            .helpText("Help text for duplicate property 1")
            .type("string")
            .defaultValue("default2")
            .add()
    );

    Assert.assertEquals("ProviderConfigProperty with name 'property1' already exists.", exception.getMessage());
  }
}
