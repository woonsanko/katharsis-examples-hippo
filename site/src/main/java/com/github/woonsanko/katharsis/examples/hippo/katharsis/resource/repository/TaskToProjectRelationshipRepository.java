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

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.github.woonsanko.katharsis.examples.hippo.beans.Task;
import com.github.woonsanko.katharsis.examples.hippo.katharsis.resource.exception.ResourceNotFoundException;
import com.github.woonsanko.katharsis.examples.hippo.katharsis.resource.model.ProjectResource;
import com.github.woonsanko.katharsis.examples.hippo.katharsis.resource.model.TaskResource;

import io.katharsis.queryParams.QueryParams;
import io.katharsis.repository.RelationshipRepository;

@Component
public class TaskToProjectRelationshipRepository extends AbstractRepository
        implements RelationshipRepository<TaskResource, String, ProjectResource, String> {

    @Override
    public Iterable<ProjectResource> findManyTargets(String sourceId, String fieldName, QueryParams requestParams) {
        List<ProjectResource> projectResList = null;

        Task taskDoc = (Task) findHippoBeanByIdentifier(sourceId);

        if (taskDoc == null) {
            throw new ResourceNotFoundException("Task not found by '" + sourceId + "'.");
        }

        //FIXME
//        List<Project> projectDocs = taskDoc.getReferringProjectDocuments();
//
//        if (CollectionUtils.isNotEmpty(projectDocs)) {
//            projectResList = new ArrayList<>();
//            ProjectResource projectRes;
//
//            for (Project projectDoc : projectDocs) {
//                projectRes = new ProjectResource().represent(projectDoc);
//                projectResList.add(projectRes);
//            }
//        }

        if (projectResList == null) {
            projectResList = Collections.emptyList();
        }

        return projectResList;
    }

    @Override
    public ProjectResource findOneTarget(String sourceId, String fieldName, QueryParams requestParams) {
        return null;
    }

    @Override
    public void setRelation(TaskResource source, String targetId, String fieldName) {
    }

    @Override
    public void setRelations(TaskResource source, Iterable<String> targetIds, String fieldName) {
    }

    @Override
    public void addRelations(TaskResource source, Iterable<String> targetIds, String fieldName) {
    }

    @Override
    public void removeRelations(TaskResource source, Iterable<String> targetIds, String fieldName) {
    }

}