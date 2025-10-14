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
package org.keycloak.models;

/**
 * @author <a href="mailto:masales@redhat.com">Marcelo Sales</a>
 */
public class ClientSecretConstants {

    // client attribute names
    public static final String CLIENT_SECRET_ROTATION_ENABLED = "client.secret.rotation.enabled";
    public static final String CLIENT_SECRET_CREATION_TIME = "client.secret.creation.time";
    public static final String CLIENT_SECRET_EXPIRATION = "client.secret.expiration.time";
    public static final String CLIENT_ROTATED_SECRET = "client.secret.rotated";
    public static final String CLIENT_ROTATED_SECRET_CREATION_TIME = "client.secret.rotated.creation.time";
    public static final String CLIENT_ROTATED_SECRET_EXPIRATION_TIME = "client.secret.rotated.expiration.time";
    public static final String CLIENT_SECRET_REMAINING_EXPIRATION_TIME = "client.secret.remaining.expiration.time";

}
