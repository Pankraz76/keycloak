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
package org.keycloak.test.examples;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.testframework.annotations.InjectAdminClient;
import org.keycloak.testframework.annotations.InjectHttpClient;
import org.keycloak.testframework.annotations.InjectKeycloakUrls;
import org.keycloak.testframework.annotations.KeycloakIntegrationTest;
import org.keycloak.testframework.https.InjectCertificates;
import org.keycloak.testframework.https.ManagedCertificates;
import org.keycloak.testframework.oauth.OAuthClient;
import org.keycloak.testframework.oauth.annotations.InjectOAuthClient;
import org.keycloak.testframework.server.KeycloakServerConfig;
import org.keycloak.testframework.server.KeycloakServerConfigBuilder;
import org.keycloak.testframework.server.KeycloakUrls;

import java.io.IOException;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;

@KeycloakIntegrationTest(config = TlsEnabledTest.TlsEnabledServerConfig.class)
public class TlsEnabledTest {

    @InjectHttpClient
    HttpClient httpClient;

    @InjectOAuthClient
    OAuthClient oAuthClient;

    @InjectAdminClient
    Keycloak adminClient;

    @InjectCertificates
    ManagedCertificates managedCertificates;

    @InjectKeycloakUrls
    KeycloakUrls keycloakUrls;


    @Test
    public void testCertSupplier() throws KeyStoreException {
        Assertions.assertNotNull(managedCertificates);

        KeyStore trustStore = managedCertificates.getClientTrustStore();
        Assertions.assertNotNull(trustStore);

        X509Certificate cert = managedCertificates.getKeycloakServerCertificate();
        Assertions.assertNotNull(cert);
        Assertions.assertEquals(cert.getSerialNumber(), ((X509Certificate) trustStore.getCertificate(ManagedCertificates.CERT_ENTRY)).getSerialNumber());
    }

    @Test
    public void testCertDetails() throws CertificateNotYetValidException, CertificateExpiredException {
        X509Certificate cert = managedCertificates.getKeycloakServerCertificate();

        cert.checkValidity();
        Assertions.assertEquals("CN=localhost", cert.getSubjectX500Principal().getName());
        Assertions.assertEquals("CN=localhost", cert.getIssuerX500Principal().getName());
    }

    @Test
    public void testHttpClient() throws IOException {
        URL baseUrl = keycloakUrls.getBaseUrl();
        Assertions.assertEquals("https", baseUrl.getProtocol());

        HttpGet req = new HttpGet(baseUrl.toString());
        HttpResponse resp = httpClient.execute(req);
        Assertions.assertEquals(200, resp.getStatusLine().getStatusCode());
    }

    @Test
    public void testAdminClient() {
        adminClient.realm("default");
    }

    @Test
    public void testOAuthClient() {
        Assertions.assertTrue(oAuthClient.doWellKnownRequest().getTokenEndpoint().startsWith("https://"));
    }


    public static class TlsEnabledServerConfig implements KeycloakServerConfig {

        @Override
        public KeycloakServerConfigBuilder configure(KeycloakServerConfigBuilder config) {
            return config.tlsEnabled(true);
        }
    }
}
