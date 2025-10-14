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

import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

class LoggingOutputStream extends FilterOutputStream {

    private ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private String name;

    public LoggingOutputStream(String name, OutputStream os) {
        super(os);
        this.name = name;
    }

    @Override
    public void write(int b) throws IOException {
        super.write(b);
        if (b == 10) {
            log();
        } else {
            buffer.write(b);
        }
    }

    @Override
    public void write(byte[] buf) throws IOException {
        write(buf, 0, buf.length);
    }

    @Override
    public void write(byte[] buf, int offs, int len) throws IOException {
        for (int i = 0; i < len; i++) {
            write(buf[offs+i]);
        }
    }

    @Override
    public void close() throws IOException {
        super.close();
        if (buffer.size() > 0) {
            log();
        }
    }

    private void log() {
        String log = new String(buffer.toByteArray());
        buffer.reset();
        System.out.println("[" + name + "] " + log);
    }
}