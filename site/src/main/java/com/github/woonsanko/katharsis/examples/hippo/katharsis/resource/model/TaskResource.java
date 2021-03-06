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
package com.github.woonsanko.katharsis.examples.hippo.katharsis.resource.model;

import java.util.List;

import org.hippoecm.hst.content.beans.standard.HippoBean;

import com.github.woonsanko.katharsis.examples.hippo.beans.Task;

import io.katharsis.resource.annotations.JsonApiId;
import io.katharsis.resource.annotations.JsonApiIncludeByDefault;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToMany;

@JsonApiResource(type="tasks")
public class TaskResource extends AbstractResource {

    @JsonApiId
    private String id;

    private String name;

    private String description;

    @JsonApiToMany
    @JsonApiIncludeByDefault
    private List<ProjectResource> projects;

    @Override
    public <T extends AbstractResource> T represent(HippoBean hippoBean) {
        if (!(hippoBean instanceof Task)) {
            throw new IllegalArgumentException("Not a task document: " + hippoBean);
        }

        Task taskDoc = (Task) hippoBean;

        setId(taskDoc.getCanonicalHandleUUID());
        setName(taskDoc.getName());
        setDescription(taskDoc.getDescription());

        return (T) this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProjectResource> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectResource> projects) {
        this.projects = projects;
    }

}