package com.github.woonsanko.katharsis.examples.hippo.katharsis.resource.repository;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.hst.content.beans.manager.ObjectBeanManager;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.katharsis.queryParams.PaginationKeys;
import io.katharsis.queryParams.RequestParams;
import io.katharsis.queryParams.include.Inclusion;

abstract public class AbstractRepository {

    private static Logger log = LoggerFactory.getLogger(AbstractRepository.class);

    protected int getPaginationOffset(final RequestParams requestParams, final int defaultValue) {
        int value = defaultValue;

        if (requestParams != null && requestParams.getPagination() != null
                && requestParams.getPagination().containsKey(PaginationKeys.offset)) {
            value = requestParams.getPagination().get(PaginationKeys.offset);
        }

        return Math.max(0, value);
    }

    protected int getPaginationLimit(final RequestParams requestParams, final int defaultValue) {
        int value = defaultValue;

        if (requestParams != null && requestParams.getPagination() != null
                && requestParams.getPagination().containsKey(PaginationKeys.limit)) {
            value = requestParams.getPagination().get(PaginationKeys.limit);
        }

        return Math.max(1, value);
    }

    protected Inclusion getInclusionByPath(final RequestParams requestParams, final String path) {
        Inclusion inclusion = null;

        List<Inclusion> inclusions = requestParams.getIncludedRelations();

        if (inclusions != null) {
            for (Inclusion inc : inclusions) {
                if (StringUtils.equals(path, inc.getPath())) {
                    inclusion = inc;
                    break;
                }
            }
        }

        return inclusion;
    }

    protected HippoBean findHippoBeanByIdentifier(final String identifier) {
        final HstRequestContext requestContext = RequestContextProvider.get();
        final ObjectBeanManager obm = requestContext.getObjectBeanManager();

        HippoBean hippoBean = null;

        try {
            hippoBean = (HippoBean) obm.getObjectByUuid(identifier);
        } catch (ObjectBeanManagerException e) {
            log.error("Failed to retrive hippo bean by id: '{}'.", identifier, e);
        }

        return hippoBean;
    }
}
