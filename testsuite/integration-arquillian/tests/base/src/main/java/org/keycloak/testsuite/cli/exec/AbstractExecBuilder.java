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

import java.io.InputStream;

/**
 * @author <a href="mailto:mstrukel@redhat.com">Marko Strukelj</a>
 */
public abstract class AbstractExecBuilder<T> {

    protected String workDir;
    protected String argsLine;
    protected InputStream stdin;
    protected String env;
    protected boolean dumpStreams;

    public AbstractExecBuilder<T> workDir(String path) {
        this.workDir = path;
        return this;
    }

    public AbstractExecBuilder<T> argsLine(String cmd) {
        this.argsLine = cmd;
        return this;
    }

    public AbstractExecBuilder<T> stdin(InputStream is) {
        this.stdin = is;
        return this;
    }

    public AbstractExecBuilder<T> env(String env) {
        this.env = env;
        return this;
    }

    public AbstractExecBuilder<T> fullStreamDump() {
        this.dumpStreams = true;
        return this;
    }

    public abstract T execute();

    public abstract T executeAsync();
}
