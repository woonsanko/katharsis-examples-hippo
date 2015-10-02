package com.github.woonsanko.katharsis.examples.hippo.beans;

import java.util.List;

import org.hippoecm.hst.content.beans.Node;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;

@HippoEssentialsGenerated(internalName = "katharsisexampleshippo:project")
@Node(jcrType = "katharsisexampleshippo:project")
public class ProjectDocument extends BaseDocument {

    @HippoEssentialsGenerated(internalName = "katharsisexampleshippo:name")
    public String getName() {
        return getProperty("katharsisexampleshippo:name");
    }

    @HippoEssentialsGenerated(internalName = "katharsisexampleshippo:description")
    public String getDescription() {
        return getProperty("katharsisexampleshippo:description");
    }

    @HippoEssentialsGenerated(internalName = "katharsisexampleshippo:task")
    public List<TaskDocument> getTaskDocuments() {
        return getLinkedBeans("katharsisexampleshippo:task", TaskDocument.class);
    }
}
