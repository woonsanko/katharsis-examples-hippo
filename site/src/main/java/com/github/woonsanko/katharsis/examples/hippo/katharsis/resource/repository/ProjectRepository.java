package com.github.woonsanko.katharsis.examples.hippo.katharsis.resource.repository;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.filter.Filter;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.github.woonsanko.katharsis.examples.hippo.beans.ProjectDocument;
import com.github.woonsanko.katharsis.examples.hippo.katharsis.resource.exception.ResourceNotFoundException;
import com.github.woonsanko.katharsis.examples.hippo.katharsis.resource.model.ProjectResource;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.repository.ResourceRepository;

@Component
public class ProjectRepository extends AbstractRepository implements ResourceRepository<ProjectResource, String> {

    private static Logger log = LoggerFactory.getLogger(ProjectRepository.class);

    @Override
    public ProjectResource findOne(String id, RequestParams requestParams) {
        ProjectDocument projectDoc = (ProjectDocument) findHippoBeanByIdentifier(id);

        if (projectDoc == null) {
            throw new ResourceNotFoundException("Project not found by '" + id + "'.");
        }

        ProjectResource projectRes = new ProjectResource().represent(projectDoc);
        return projectRes;
    }

    @Override
    public Iterable<ProjectResource> findAll(RequestParams requestParams) {
        List<ProjectResource> projectResList = new LinkedList<>();

        try {
            final HstRequestContext requestContext = RequestContextProvider.get();
            final HippoBean scope = requestContext.getSiteContentBaseBean();
            final HstQuery hstQuery = requestContext.getQueryManager().createQuery(scope, ProjectDocument.class, true);

            String queryTerm = null;

            if (requestParams.getFilters() != null) {
                queryTerm = StringUtils.trim(requestParams.getFilters().get("q").asText());
            }

            if (StringUtils.isNotEmpty(queryTerm)) {
                final Filter filter = hstQuery.createFilter();
                filter.addContains(".", queryTerm);
                hstQuery.setFilter(filter);
            }

            hstQuery.setOffset(getPaginationOffset(requestParams, 0));
            hstQuery.setLimit(this.getPaginationLimit(requestParams, 200));

            final HstQueryResult result = hstQuery.execute();

            ProjectDocument projectDoc;
            ProjectResource projectRes;

            for (HippoBeanIterator it = result.getHippoBeans(); it.hasNext(); ) {
                projectDoc = (ProjectDocument) it.nextHippoBean();
                projectRes = new ProjectResource().represent(projectDoc);
                projectResList.add(projectRes);
            }
        } catch (Exception e) {
            log.error("Failed to query projects.", e);
        }

        return projectResList;
    }

    @Override
    public Iterable<ProjectResource> findAll(Iterable<String> ids, RequestParams requestParams) {
        return findAll(requestParams);
    }

    @Override
    public <S extends ProjectResource> S save(S entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(String id) {
        // TODO Auto-generated method stub
        
    }

}
