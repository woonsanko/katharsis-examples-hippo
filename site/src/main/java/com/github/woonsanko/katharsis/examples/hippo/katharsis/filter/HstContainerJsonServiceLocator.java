/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.woonsanko.katharsis.examples.hippo.katharsis.filter;

import java.util.Map;

import org.hippoecm.hst.core.container.ComponentManager;
import org.hippoecm.hst.site.HstServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.katharsis.locator.JsonServiceLocator;
import io.katharsis.repository.ResourceRepository;

/**
 * Custom {@link JsonServiceLocator} implementation
 * which looks up {@link ResourceRepository} beans from HST Container (through {@link ComponentManager}).
 */
public class HstContainerJsonServiceLocator implements JsonServiceLocator {

    private static Logger log = LoggerFactory.getLogger(HstContainerJsonServiceLocator.class);

    /**
     * Looks up {@link ResourceRepository} bean by the {@code clazz} bean type
     * from HST Container (through {@link ComponentManager})
     */
    @Override
    public <T> T getInstance(Class<T> clazz) {
        Map<String, T> components = HstServices.getComponentManager().getComponentsOfType(clazz);

        if (!components.isEmpty()) {
            if (components.size() > 1) {
                log.warn("There are multiple components of type '{}' in HST Container!", clazz);
            }

            return components.values().iterator().next();
        }

        log.error("There is no component found by type '{}' in HST Container!", clazz);

        return null;
    }

}
