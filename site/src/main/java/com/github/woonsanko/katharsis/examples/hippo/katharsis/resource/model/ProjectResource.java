package com.github.woonsanko.katharsis.examples.hippo.katharsis.resource.model;

import org.hippoecm.hst.content.beans.standard.HippoBean;

import com.github.woonsanko.katharsis.examples.hippo.beans.ProjectDocument;

import io.katharsis.resource.annotations.JsonApiId;
import io.katharsis.resource.annotations.JsonApiResource;

@JsonApiResource(type="projects")
public class ProjectResource extends AbstractResource {

    @JsonApiId
    private String id;

    private String name;

    private String description;

    @Override
    public <T extends AbstractResource> T represent(HippoBean hippoBean) {
        if (!(hippoBean instanceof ProjectDocument)) {
            throw new IllegalArgumentException("Not a project document: " + hippoBean);
        }

        ProjectDocument projectDoc = (ProjectDocument) hippoBean;

        setId(projectDoc.getCanonicalHandleUUID());
        setName(projectDoc.getName());
        setDescription(projectDoc.getDescription());

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

}
