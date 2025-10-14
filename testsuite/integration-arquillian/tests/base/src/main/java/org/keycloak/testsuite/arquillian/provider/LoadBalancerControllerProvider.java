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
package org.keycloak.testsuite.arquillian.provider;

import org.keycloak.testsuite.arquillian.annotation.LoadBalancer;
import java.lang.annotation.Annotation;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.arquillian.test.spi.enricher.resource.ResourceProvider;
import org.keycloak.testsuite.arquillian.LoadBalancerController;
import org.jboss.arquillian.container.spi.Container;
import org.jboss.arquillian.container.spi.ContainerRegistry;

/**
 *
 * @author hmlnarik
 */
public class LoadBalancerControllerProvider implements ResourceProvider {

    @Inject
    private Instance<ContainerRegistry> registry;

    @Override
    public boolean canProvide(Class<?> type) {
        return type.equals(LoadBalancerController.class);
    }

    @Override
    public Object lookup(ArquillianResource resource, Annotation... qualifiers) {
        String balancerName = null;

        // Check for the presence of possible qualifiers
        for (Annotation a : qualifiers) {
            Class<? extends Annotation> annotationType = a.annotationType();

            if (annotationType.equals(LoadBalancer.class)) {
                balancerName = ((LoadBalancer) a).value();
            }
        }

        ContainerRegistry reg = registry.get();
        Container container = null;
        if (balancerName == null || "".equals(balancerName.trim())) {
            if (reg.getContainers().size() == 1) {
                container = reg.getContainers().get(0);
            } else {
                throw new IllegalArgumentException("Invalid load balancer configuration request - need to specify load balancer name in @LoadBalancerController");
            }
        } else {
            container = reg.getContainer(balancerName);
        }

        if (container == null) {
            throw new IllegalArgumentException("Invalid load balancer configuration - load balancer not found: '" + balancerName + "'");
        }
        if (! (container.getDeployableContainer() instanceof LoadBalancerController)) {
            throw new IllegalArgumentException("Invalid load balancer configuration - container " + container.getName() + " is not a load balancer");
        }

        return container.getDeployableContainer();
    }
}
