package com.github.woonsanko.katharsis.examples.hippo.katharsis.resource.model;

import java.util.List;

import org.hippoecm.hst.content.beans.standard.HippoBean;

import com.github.woonsanko.katharsis.examples.hippo.beans.TaskDocument;

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
        if (!(hippoBean instanceof TaskDocument)) {
            throw new IllegalArgumentException("Not a task document: " + hippoBean);
        }

        TaskDocument taskDoc = (TaskDocument) hippoBean;

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