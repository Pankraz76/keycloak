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
package org.keycloak.representations.workflows;

import static java.util.Optional.ofNullable;
import static org.keycloak.representations.workflows.WorkflowConstants.CONFIG_ERROR;

import java.util.Collections;
import java.util.List;

public class WorkflowStateRepresentation {

    private List<String> errors = Collections.emptyList();

    public WorkflowStateRepresentation() {}

    public WorkflowStateRepresentation(WorkflowRepresentation workflow) {
        this.errors = ofNullable(workflow.getConfigValues(CONFIG_ERROR)).orElse(Collections.emptyList());
    }

    public List<String> getErrors() {
        return errors;
    }
}
