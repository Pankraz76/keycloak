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
package org.keycloak.models.cache.infinispan.entities;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class RealmListQuery extends AbstractRevisioned implements RealmQuery {
    private final Set<String> realms;

    public RealmListQuery(Long revision, String id, String realm) {
        super(revision, id);
        realms = new HashSet<>();
        realms.add(realm);
    }
    public RealmListQuery(Long revision, String id, Set<String> realms) {
        super(revision, id);
        this.realms = realms;
    }

    @Override
    public Set<String> getRealms() {
        return realms;
    }
}