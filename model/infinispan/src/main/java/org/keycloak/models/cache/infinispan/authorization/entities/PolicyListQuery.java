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
package org.keycloak.models.cache.infinispan.authorization.entities;

import org.keycloak.models.cache.infinispan.entities.AbstractRevisioned;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class PolicyListQuery extends AbstractRevisioned implements PolicyQuery {
    private final Set<String> policies;
    private final String serverId;

    public PolicyListQuery(Long revision, String id, String policyId, String serverId) {
        super(revision, id);
        this.serverId = serverId;
        policies = new HashSet<>();
        policies.add(policyId);
    }
    public PolicyListQuery(Long revision, String id, Set<String> policies, String serverId) {
        super(revision, id);
        this.serverId = serverId;
        this.policies = policies;
    }

    @Override
    public String getResourceServerId() {
        return serverId;
    }

    public Set<String> getPolicies() {
        return policies;
    }

    @Override
    public boolean isInvalid(Set<String> invalidations) {
        return invalidations.contains(getId()) || invalidations.contains(getResourceServerId());
    }
}