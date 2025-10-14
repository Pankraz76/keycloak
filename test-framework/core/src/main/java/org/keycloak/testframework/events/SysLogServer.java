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
package org.keycloak.testframework.events;

import org.jboss.logging.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SysLogServer {

    private static final Logger LOGGER = Logger.getLogger(SysLogServer.class);
    private static final int MAX_THREADS = 5;
    private final ServerSocket serverSocket;
    private final List<Thread> threads = Collections.synchronizedList(new LinkedList<>());
    private final Set<SysLogListener> listeners = new HashSet<>();
    private boolean running = true;

    public SysLogServer() throws IOException {
        serverSocket = new ServerSocket(0);
        startThread();
    }

    public void stop() throws InterruptedException, IOException {
        LOGGER.tracev("Shutdown, threads={0}", threads.size());
        running = false;
        serverSocket.close();
        for (Thread t : threads) {
            t.join();
        }
    }

    public void addListener(SysLogListener listener) {
        listeners.add(listener);
    }

    public void removeListener(SysLogListener listener) {
        listeners.remove(listener);
    }

    public String getEndpoint() {
        return "localhost:" + serverSocket.getLocalPort();
    }

    protected void startThread() {
        if (running && threads.size() < MAX_THREADS) {
            Thread thread = new Thread(new BasicSocketHandler());
            thread.start();
            threads.add(thread);

            LOGGER.tracev("Started new thread, running threads={0}", threads.size());
        }
    }

    private class BasicSocketHandler implements Runnable {

        @Override
        public void run() {
            while (running) {
                try {
                    Socket socket = serverSocket.accept();
                    LOGGER.trace("Socket accepted");
                    startThread();

                    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    for (String l = br.readLine(); l != null; l = br.readLine()) {
                        try {
                            SysLog sysLog = SysLog.parse(l);
// TODO This shows an issue when using embedded Keycloak server logging from the testsuite (client side) is also sent over syslog :/
//                            LOGGER.tracev("New message={0}", sysLog.getMessage());
                            listeners.forEach(listener -> listener.onLog(sysLog));
                        } catch (Throwable t) {
                            LOGGER.tracev("Failed to parse message={0}", l);
                        }
                    }
                    socket.close();
                    LOGGER.trace("Socket closed");
                } catch (Throwable t) {
                    if (!serverSocket.isClosed()) {
                        LOGGER.trace(t.getMessage(), t);
                    }
                }
            }
        }
    }

}
