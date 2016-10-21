package com.github.woonsanko.katharsis.examples.hippo.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoDocument;
import java.util.List;
import org.hippoecm.hst.content.beans.standard.HippoBean;

@HippoEssentialsGenerated(internalName = "katharsisexampleshippo:project")
@Node(jcrType = "katharsisexampleshippo:project")
public class Project extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "katharsisexampleshippo:name")
    public String getName() {
        return getProperty("katharsisexampleshippo:name");
    }

    @HippoEssentialsGenerated(internalName = "katharsisexampleshippo:description")
    public String getDescription() {
        return getProperty("katharsisexampleshippo:description");
    }

    @HippoEssentialsGenerated(internalName = "katharsisexampleshippo:task")
    public List<HippoBean> getTask() {
        return getLinkedBeans("katharsisexampleshippo:task", HippoBean.class);
    }
}
