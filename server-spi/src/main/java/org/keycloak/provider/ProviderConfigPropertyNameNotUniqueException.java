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

import org.keycloak.models.ModelException;

/**
 * Exception thrown when a provider configuration property name is not unique.
 * This is used to indicate that a property with the same name already exists
 * in the configuration, which violates the uniqueness constraint.
 */
public class ProviderConfigPropertyNameNotUniqueException extends ModelException {

  public ProviderConfigPropertyNameNotUniqueException(String message) {
    super(message);
  }
}
