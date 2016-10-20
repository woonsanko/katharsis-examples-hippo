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
package com.github.woonsanko.katharsis.examples.hippo.katharsis.resource.repository;

import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.hst.content.beans.manager.ObjectBeanManager;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.katharsis.queryParams.QueryParams;
import io.katharsis.queryParams.RestrictedPaginationKeys;
import io.katharsis.queryParams.include.Inclusion;
import io.katharsis.queryParams.params.IncludedRelationsParams;
import io.katharsis.queryParams.params.TypedParams;

/**
 * Abstract JSON API Resource Repository class,
 * containing some utility methods for child classes.
 */
abstract public class AbstractRepository {

    private static Logger log = LoggerFactory.getLogger(AbstractRepository.class);

    /**
     * Returns JSON API pagination offset parameter value as integer or {@code defaultValue} if not provided.
     * @param requestParams {@link RequestParams} instance
     * @param defaultValue default offset value
     * @return JSON API pagination offset parameter value or {@code defaultValue} if not provided
     */
    protected int getPaginationOffset(final QueryParams requestParams, final int defaultValue) {
        int value = defaultValue;

        if (requestParams != null && requestParams.getPagination() != null
                && requestParams.getPagination().containsKey(RestrictedPaginationKeys.offset)) {
            value = requestParams.getPagination().get(RestrictedPaginationKeys.offset);
        }

        return Math.max(0, value);
    }

    /**
     * Returns JSON API pagination limit parameter value as integer or {@code defaultValue} if not provided.
     * @param requestParams {@link RequestParams} instance
     * @param defaultValue default offset value
     * @return JSON API pagination limit parameter value as integer or {@code defaultValue} if not provided
     */
    protected int getPaginationLimit(final QueryParams requestParams, final int defaultValue) {
        int value = defaultValue;

        if (requestParams != null && requestParams.getPagination() != null
                && requestParams.getPagination().containsKey(RestrictedPaginationKeys.limit)) {
            value = requestParams.getPagination().get(RestrictedPaginationKeys.limit);
        }

        return Math.max(1, value);
    }

    /**
     * Returns JSON API Inclusion data as {@link Inclusion} instance after finding it by {@code path}.
     * @param requestParams {@link RequestParams} instance
     * @param path JSON API inclusion path
     * @return JSON API Inclusion data as {@link Inclusion} instance after finding it by {@code path}
     */
    protected Inclusion getInclusionByPath(final QueryParams requestParams, final String path) {
        Inclusion inclusion = null;

        TypedParams<IncludedRelationsParams> inclusions = requestParams.getIncludedRelations();

        if (inclusions != null) {
            // TODO
        }

        return inclusion;
    }

    /**
     * Find a {@link HippoBean} instance by the given {@code identifier}, using JCR API.
     * @param identifier identifier of a {@link HippoBean} instance
     * @return a {@link HippoBean} instance by the given {@code identifier}, using JCR API
     */
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