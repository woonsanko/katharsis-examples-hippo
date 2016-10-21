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

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.github.woonsanko.katharsis.examples.hippo.beans.Project;
import com.github.woonsanko.katharsis.examples.hippo.beans.Task;
import com.github.woonsanko.katharsis.examples.hippo.katharsis.resource.exception.ResourceNotFoundException;
import com.github.woonsanko.katharsis.examples.hippo.katharsis.resource.model.ProjectResource;
import com.github.woonsanko.katharsis.examples.hippo.katharsis.resource.model.TaskResource;

import io.katharsis.queryParams.QueryParams;
import io.katharsis.queryParams.include.Inclusion;
import io.katharsis.queryParams.params.FilterParams;
import io.katharsis.queryParams.params.IncludedRelationsParams;
import io.katharsis.repository.ResourceRepository;

@Component
public class TaskRepository extends AbstractRepository implements ResourceRepository<TaskResource, String> {

    private static Logger log = LoggerFactory.getLogger(TaskRepository.class);

    @Override
    public TaskResource findOne(String id, QueryParams queryParams) {
        Task taskDoc = (Task) findHippoBeanByIdentifier(id);

        if (taskDoc == null) {
            throw new ResourceNotFoundException("Task not found by '" + id + "'.");
        }

        TaskResource taskRes = new TaskResource().represent(taskDoc);

        final Map<String, IncludedRelationsParams> typeRelationsParams = queryParams.getIncludedRelations().getParams();
        if (typeRelationsParams.containsKey("tasks")) {
            final IncludedRelationsParams tasksParams = typeRelationsParams.get("tasks");
            final Set<Inclusion> inclusions = tasksParams.getParams();
            if (CollectionUtils.isNotEmpty(inclusions)
                    && StringUtils.equals("projects", inclusions.iterator().next().getPath())) {
                includeProjectResources(taskDoc, taskRes);
            }
        }

        return taskRes;
    }

    @Override
    public Iterable<TaskResource> findAll(QueryParams queryParams) {
        List<TaskResource> taskResList = new LinkedList<>();

        try {
            final HstRequestContext requestContext = RequestContextProvider.get();
            final HippoBean scope = requestContext.getSiteContentBaseBean();
            final HstQuery hstQuery = requestContext.getQueryManager().createQuery(scope, Task.class, true);

            String queryTerm = null;

            final Map<String, FilterParams> typeFilterParams = queryParams.getFilters().getParams();
            if (typeFilterParams.containsKey("tasks")) {
                final FilterParams tasksParams = typeFilterParams.get("tasks");
                final Set<String> filterValues = tasksParams.getParams().get("$contains");
                if (CollectionUtils.isNotEmpty(filterValues)) {
                    queryTerm = StringUtils.trim(filterValues.iterator().next());
                }
            }

            if (StringUtils.isNotEmpty(queryTerm)) {
                final Filter filter = hstQuery.createFilter();
                filter.addContains(".", queryTerm);
                hstQuery.setFilter(filter);
            }

            hstQuery.setOffset(getPaginationOffset(queryParams, 0));
            hstQuery.setLimit(this.getPaginationLimit(queryParams, 200));

            final HstQueryResult result = hstQuery.execute();

            Task taskDoc;
            TaskResource taskRes;

            for (HippoBeanIterator it = result.getHippoBeans(); it.hasNext(); ) {
                taskDoc = (Task) it.nextHippoBean();
                taskRes = new TaskResource().represent(taskDoc);
                taskResList.add(taskRes);
            }
        } catch (Exception e) {
            log.error("Failed to query tasks.", e);
        }

        return taskResList;
    }

    @Override
    public Iterable<TaskResource> findAll(Iterable<String> ids, QueryParams queryParams) {
        return findAll(queryParams);
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

    private void includeProjectResources(final Task taskDoc, final TaskResource taskRes) {
        final List<Project> referringProjectDocList = taskDoc.getReferringProjects();

        if (CollectionUtils.isNotEmpty(referringProjectDocList)) {
            List<ProjectResource> projectResList = new LinkedList<>();
            ProjectResource projectRes;

            for (Project projectDoc : referringProjectDocList) {
                projectRes = new ProjectResource().represent(projectDoc);
                projectResList.add(projectRes);
            }

            taskRes.setProjects(projectResList);
        }
    }
}