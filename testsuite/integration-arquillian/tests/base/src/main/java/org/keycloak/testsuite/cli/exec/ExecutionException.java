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
package org.keycloak.testsuite.cli.exec;

/**
 * @author <a href="mailto:marko.strukelj@gmail.com">Marko Strukelj</a>
 */
public class ExecutionException extends RuntimeException {

    private int exitCode = -1;

    public ExecutionException(int exitCode) {
        this.exitCode = exitCode;
    }

    public ExecutionException(String message, int exitCode) {
        super(message);
        this.exitCode = exitCode;
    }

    public int exitCode() {
        return exitCode;
    }

    @Override
    public String toString() {
        return super.toString() + ", exitCode: " + exitCode;
    }
}
