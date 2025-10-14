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
package org.keycloak.crypto.fips.test;

import java.security.SecureRandom;

import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.fips.FipsStatus;
import org.jboss.logging.Logger;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.keycloak.common.crypto.CryptoIntegration;
import org.keycloak.common.util.Environment;
import org.keycloak.rule.CryptoInitRule;

/**
 * @author <a href="mailto:mposolda@redhat.com">Marek Posolda</a>
 */
public class FIPS1402SecureRandomTest {

    @ClassRule
    public static CryptoInitRule cryptoInitRule = new CryptoInitRule();

    @Before
    public void before() {
        // Run this test just if java is in FIPS mode
        Assume.assumeTrue("Java is not in FIPS mode. Skipping the test.", Environment.isJavaInFipsMode());
    }

    protected static final Logger logger = Logger.getLogger(FIPS1402SecureRandomTest.class);

    @Test
    public void testSecureRandom() throws Exception {
        logger.info(CryptoIntegration.dumpJavaSecurityProviders());

        logger.infof("BC FIPS approved mode: %b, FIPS Status: %s", CryptoServicesRegistrar.isInApprovedOnlyMode(), FipsStatus.getStatusMessage());

        SecureRandom sc1 = new SecureRandom();
        logger.infof(dumpSecureRandom("new SecureRandom()", sc1));
        Assert.assertEquals("DEFAULT", sc1.getAlgorithm());
        Assert.assertEquals("BCFIPS", sc1.getProvider().getName());

        SecureRandom sc2 = SecureRandom.getInstance("DEFAULT", "BCFIPS");
        logger.infof(dumpSecureRandom("SecureRandom.getInstance(\"DEFAULT\", \"BCFIPS\")", sc2));
        Assert.assertEquals("DEFAULT", sc2.getAlgorithm());
        Assert.assertEquals("BCFIPS", sc2.getProvider().getName());
    }


    private String dumpSecureRandom(String prefix, SecureRandom secureRandom) {
        StringBuilder builder = new StringBuilder(prefix + ": algorithm: " + secureRandom.getAlgorithm() + ", provider: " + secureRandom.getProvider() + ", random numbers: ");
        for (int i=0; i < 5; i++) {
            builder.append(secureRandom.nextInt(1000) + ", ");
        }
        return builder.toString();
    }
}
