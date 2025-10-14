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
package org.keycloak.testsuite.theme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.NoCache;
import org.keycloak.Config.Scope;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.services.resource.AccountResourceProvider;
import org.keycloak.services.resource.AccountResourceProviderFactory;

public class CustomAccountResourceProviderFactory implements AccountResourceProviderFactory, AccountResourceProvider {
  public static final String ID = "ext-custom-account-console";

  @Override
  public String getId() {
    return ID;
  }

  @Override
  public AccountResourceProvider create(KeycloakSession session) {
    return this;
  }

  @Override
  public Object getResource() {
    return this;
  }

  @GET
  @NoCache
  @Produces(MediaType.TEXT_HTML)
  public Response getMainPage() {
    return Response.ok().entity("<html><head><title>Account</title></head><body><h1>Custom Account Console</h1></body></html>").build();
  }
  
  @Override
  public void init(Scope config) {}

  @Override
  public void postInit(KeycloakSessionFactory factory) {}

  @Override
  public void close() {}
}
