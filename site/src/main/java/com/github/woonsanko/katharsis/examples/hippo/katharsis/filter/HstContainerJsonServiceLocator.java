package com.github.woonsanko.katharsis.examples.hippo.katharsis.filter;

import java.util.Map;

import org.hippoecm.hst.site.HstServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.katharsis.locator.JsonServiceLocator;

public class HstContainerJsonServiceLocator implements JsonServiceLocator {

    private static Logger log = LoggerFactory.getLogger(HstContainerJsonServiceLocator.class);

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
