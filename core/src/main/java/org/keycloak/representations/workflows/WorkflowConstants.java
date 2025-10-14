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

public final class WorkflowConstants {

    public static final String DEFAULT_WORKFLOW = "event-based-workflow";

    public static final String CONFIG_USES = "uses";
    public static final String CONFIG_WITH = "with";

    // Entry configuration keys for Workflow
    public static final String CONFIG_ON_EVENT = "on";
    public static final String CONFIG_RESET_ON = "reset-on";
    public static final String CONFIG_NAME = "name";
    public static final String CONFIG_RECURRING = "recurring";
    public static final String CONFIG_SCHEDULED = "scheduled";
    public static final String CONFIG_ENABLED = "enabled";
    public static final String CONFIG_CONDITIONS = "conditions";
    public static final String CONFIG_STEPS = "steps";
    public static final String CONFIG_ERROR = "error";
    public static final String CONFIG_STATE = "state";

    // Entry configuration keys for WorkflowCondition
    public static final String CONFIG_IF = "if";

    // Entry configuration keys for WorkflowStep
    public static final String CONFIG_AFTER = "after";
    public static final String CONFIG_PRIORITY = "priority";
}
