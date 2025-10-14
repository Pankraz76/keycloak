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
package org.keycloak.testsuite.arquillian.h2;

import org.h2.tools.Server;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.test.spi.event.suite.AfterSuite;
import org.jboss.arquillian.test.spi.event.suite.BeforeSuite;
import org.jboss.logging.Logger;

import java.sql.SQLException;

/**
 * Starts H2 before suite and stops it after.
 *
 * @author tkyjovsk
 */
public class H2TestEnricher {

    protected final Logger log = Logger.getLogger(this.getClass());

    boolean runH2 = Boolean.parseBoolean(System.getProperty("run.h2", "false"));
    boolean dockerDatabaseSkip = Boolean.parseBoolean(System.getProperty("docker.database.skip", "true"));

    private Server server = null;

    public void startH2(@Observes(precedence = 3) BeforeSuite event) throws SQLException {
        if (runH2 && dockerDatabaseSkip) {
            log.info("Starting H2 database.");
            server = Server.createTcpServer();
            server.start();
            log.info(String.format("URL: %s", server.getURL()));
        }
    }

    public void stopH2(@Observes(precedence = -2) AfterSuite event) {
        if (runH2 && dockerDatabaseSkip && server.isRunning(false)) {
            log.info("Stopping H2 database.");
            server.stop();
            assert !server.isRunning(false);
        }
    }

    // Ability to run H2 database available via TCP in separate process. Useful for dev purposes
    public static void main(String[] args) {
        System.setProperty("run.h2", "true");

        final H2TestEnricher h2Enricher = new H2TestEnricher();

        try {
            h2Enricher.startH2(null);
        } catch (Exception se) {
            h2Enricher.stopH2(null);
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                h2Enricher.stopH2(null);
            }

        });
    }

}