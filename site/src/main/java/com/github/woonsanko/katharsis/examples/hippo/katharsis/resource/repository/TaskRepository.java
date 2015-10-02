package com.github.woonsanko.katharsis.examples.hippo.katharsis.resource.repository;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
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
import com.github.woonsanko.katharsis.examples.hippo.beans.TaskDocument;
import com.github.woonsanko.katharsis.examples.hippo.katharsis.resource.exception.ResourceNotFoundException;
import com.github.woonsanko.katharsis.examples.hippo.katharsis.resource.model.ProjectResource;
import com.github.woonsanko.katharsis.examples.hippo.katharsis.resource.model.TaskResource;

import io.katharsis.queryParams.RequestParams;
import io.katharsis.queryParams.include.Inclusion;
import io.katharsis.repository.ResourceRepository;

@Component
public class TaskRepository extends AbstractRepository implements ResourceRepository<TaskResource, String> {

    private static Logger log = LoggerFactory.getLogger(TaskRepository.class);

    @Override
    public TaskResource findOne(String id, RequestParams requestParams) {
        TaskDocument taskDoc = (TaskDocument) findHippoBeanByIdentifier(id);

        if (taskDoc == null) {
            throw new ResourceNotFoundException("Task not found by '" + id + "'.");
        }

        TaskResource taskRes = new TaskResource().represent(taskDoc);

        Inclusion inclusion = getInclusionByPath(requestParams, "projects");

        if (inclusion != null) {
            includeProjectResources(taskDoc, taskRes);
        }

        return taskRes;
    }

    @Override
    public Iterable<TaskResource> findAll(RequestParams requestParams) {
        List<TaskResource> taskResList = new LinkedList<>();

        try {
            final HstRequestContext requestContext = RequestContextProvider.get();
            final HippoBean scope = requestContext.getSiteContentBaseBean();
            final HstQuery hstQuery = requestContext.getQueryManager().createQuery(scope, TaskDocument.class, true);

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

            TaskDocument taskDoc;
            TaskResource taskRes;

            for (HippoBeanIterator it = result.getHippoBeans(); it.hasNext(); ) {
                taskDoc = (TaskDocument) it.nextHippoBean();
                taskRes = new TaskResource().represent(taskDoc);
                taskResList.add(taskRes);
            }
        } catch (Exception e) {
            log.error("Failed to query tasks.", e);
        }

        return taskResList;
    }

    @Override
    public Iterable<TaskResource> findAll(Iterable<String> ids, RequestParams requestParams) {
        return findAll(requestParams);
    }

    @Override
    public <S extends TaskResource> S save(S entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(String id) {
        // TODO Auto-generated method stub
        
    }

    private void includeProjectResources(final TaskDocument taskDoc, final TaskResource taskRes) {
        final List<ProjectDocument> referringProjectDocList = taskDoc.getReferringProjectDocuments();

        if (CollectionUtils.isNotEmpty(referringProjectDocList)) {
            List<ProjectResource> projectResList = new LinkedList<>();
            ProjectResource projectRes;

            for (ProjectDocument projectDoc : referringProjectDocList) {
                projectRes = new ProjectResource().represent(projectDoc);
                projectResList.add(projectRes);
            }

            taskRes.setProjects(projectResList);
        }
    }
}
