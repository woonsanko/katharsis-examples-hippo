package com.github.woonsanko.katharsis.examples.hippo.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoDocument;

@HippoEssentialsGenerated(internalName = "katharsisexampleshippo:task")
@Node(jcrType = "katharsisexampleshippo:task")
public class Task extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "katharsisexampleshippo:name")
    public String getName() {
        return getProperty("katharsisexampleshippo:name");
    }

    @HippoEssentialsGenerated(internalName = "katharsisexampleshippo:description")
    public String getDescription() {
        return getProperty("katharsisexampleshippo:description");
    }
}
