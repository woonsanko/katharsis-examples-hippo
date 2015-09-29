package com.github.woonsanko.katharsis.examples.hippo.beans;

import java.util.Calendar;

import org.hippoecm.hst.content.beans.Node;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;

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

    @HippoEssentialsGenerated(internalName = "katharsisexampleshippo:startDate")
    public Calendar getStartDate() {
        return getProperty("katharsisexampleshippo:startDate");
    }

    @HippoEssentialsGenerated(internalName = "katharsisexampleshippo:endDate")
    public Calendar getEndDate() {
        return getProperty("katharsisexampleshippo:endDate");
    }
}
