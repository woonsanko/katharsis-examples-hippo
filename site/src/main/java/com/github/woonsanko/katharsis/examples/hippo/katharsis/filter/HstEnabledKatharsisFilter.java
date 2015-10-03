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

import java.net.URI;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.core.container.ComponentManager;
import org.hippoecm.hst.core.linking.HstLink;
import org.hippoecm.hst.core.linking.HstLinkCreator;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.site.HstServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.katharsis.invoker.KatharsisInvokerBuilder;
import io.katharsis.locator.JsonServiceLocator;
import io.katharsis.servlet.AbstractKatharsisFilter;
import io.katharsis.servlet.SampleKatharsisFilter;

/**
 * Katharsis Filter implementation which overrides
 * {@link SampleKatharsisFilter#getResourceDefaultDomain()} (to read HST mount path dynamically)
 * and {@link AbstractKatharsisFilter#getKatharsisInvoker()} (to load resource repository beans from HST Container).
 */
public class HstEnabledKatharsisFilter extends SampleKatharsisFilter {

    private static final Logger log = LoggerFactory.getLogger(HstEnabledKatharsisFilter.class);

    /**
     * Returns the currently resolved domain by HST host/site mapping.
     * @return
     */
    @Override
    public String getResourceDefaultDomain() {
        final HstRequestContext requestContext = RequestContextProvider.get();

        if (requestContext == null) {
            // only for debugging purpose...
            log.warn("HST RequestContext is not available.");
            return "http://localhost:8080";
        }

        HstLinkCreator linkCreator = HstServices.getComponentManager().getComponent(HstLinkCreator.class.getName());
        HstLink link = linkCreator.create("/", requestContext.getResolvedMount().getMount());
        URI uri = URI.create(link.toUrlForm(requestContext, true));
        return StringUtils.removeEnd(uri.toString(), "/");
    }

    /**
     * Replaces {@link JsonServiceLocator} with {@link HstContainerJsonServiceLocator}
     * in order to load resource repository beans from HST Container (through {@link ComponentManager}).
     */
    @Override
    protected KatharsisInvokerBuilder createKatharsisInvokerBuilder() {
        JsonServiceLocator jsonServiceLocator = new HstContainerJsonServiceLocator();
        return super.createKatharsisInvokerBuilder().jsonServiceLocator(jsonServiceLocator);
    }

}
