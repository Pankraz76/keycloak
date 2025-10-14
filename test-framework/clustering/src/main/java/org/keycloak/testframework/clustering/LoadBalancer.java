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
package org.keycloak.testframework.clustering;

import java.util.HashMap;

import io.vertx.httpproxy.ProxyContext;
import io.vertx.httpproxy.ProxyInterceptor;
import io.vertx.httpproxy.ProxyResponse;
import org.jboss.logging.Logger;
import org.keycloak.testframework.server.ClusteredKeycloakServer;
import org.keycloak.testframework.server.KeycloakUrls;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpServer;
import io.vertx.httpproxy.HttpProxy;

public class LoadBalancer {

    private static final Logger LOGGER = Logger.getLogger(LoadBalancer.class);
    public static final String HOSTNAME = "http://localhost:9999";

    private final ClusteredKeycloakServer server;
    private final HashMap<Integer, Origin> urls = new HashMap<>();
    private final Vertx vertx;
    private final HttpProxy proxy;

    public LoadBalancer(ClusteredKeycloakServer server) {
        this.server = server;

        this.vertx = Vertx.vertx();
        HttpClient proxyClient = vertx.createHttpClient();
        proxy = HttpProxy.reverseProxy(proxyClient);
        proxy.addInterceptor(new ProxyInterceptor() {
            @Override
            public Future<ProxyResponse> handleProxyRequest(ProxyContext context) {
                LOGGER.debugf("Proxy request intercepted: %s", context.request().getURI());
                return ProxyInterceptor.super.handleProxyRequest(context);
            }
        });
        node(0);

        HttpServer proxyServer = vertx.createHttpServer();
        proxyServer.requestHandler(proxy).listen(9999, "localhost");
    }

    public void node(int index) {
        Origin origin = origin(index);
        LOGGER.debugf("Setting proxy origin to: %s:%d", origin.host, origin.port);
        proxy.origin(origin.port, origin.host);
    }

    public KeycloakUrls nodeUrls(int index) {
        return origin(index).urls;
    }

    private Origin origin(int index) {
        if (index >= server.clusterSize()) {
            throw new IllegalArgumentException("Node index out of bounds. Requested nodeIndex: %d, cluster size: %d".formatted(server.clusterSize(), index));
        }
        return urls.computeIfAbsent(index, i ->
              new Origin("localhost", server.getBasePort(i), new KeycloakUrls(server.getBaseUrl(i), server.getManagementBaseUrl(i)))
        );
    }

    public void close() {
        Future.await(vertx.close());
    }

    record Origin(String host, int port, KeycloakUrls urls) {
    }
}
