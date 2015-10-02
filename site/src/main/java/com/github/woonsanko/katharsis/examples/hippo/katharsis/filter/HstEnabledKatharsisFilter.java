package com.github.woonsanko.katharsis.examples.hippo.katharsis.filter;

import java.net.URI;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.core.linking.HstLink;
import org.hippoecm.hst.core.linking.HstLinkCreator;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.site.HstServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.katharsis.invoker.KatharsisInvokerBuilder;
import io.katharsis.locator.JsonServiceLocator;
import io.katharsis.servlet.SampleKatharsisFilter;

public class HstEnabledKatharsisFilter extends SampleKatharsisFilter {

    private static final Logger log = LoggerFactory.getLogger(HstEnabledKatharsisFilter.class);

    /**
     * Returns the currently resolved domain by HST site mapping.
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

    @Override
    protected KatharsisInvokerBuilder createKatharsisInvokerBuilder() {
        JsonServiceLocator jsonServiceLocator = new HstContainerJsonServiceLocator();
        return super.createKatharsisInvokerBuilder().jsonServiceLocator(jsonServiceLocator);
    }

}
