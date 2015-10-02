package com.github.woonsanko.katharsis.examples.hippo.beans;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.util.ContentBeanUtils;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@HippoEssentialsGenerated(internalName = "katharsisexampleshippo:task")
@Node(jcrType = "katharsisexampleshippo:task")
public class TaskDocument extends BaseDocument {

    private static Logger log = LoggerFactory.getLogger(TaskDocument.class);

    private List<ProjectDocument> referringProjectDocuments;

    @HippoEssentialsGenerated(internalName = "katharsisexampleshippo:name")
    public String getName() {
        return getProperty("katharsisexampleshippo:name");
    }

    @HippoEssentialsGenerated(internalName = "katharsisexampleshippo:description")
    public String getDescription() {
        return getProperty("katharsisexampleshippo:description");
    }

    public List<ProjectDocument> getReferringProjectDocuments() {
        if (referringProjectDocuments == null) {
            try {
                final HstRequestContext requestContext = RequestContextProvider.get();
                final HippoBean scope = requestContext.getSiteContentBaseBean();
                final HstQuery hstQuery = ContentBeanUtils.createIncomingBeansQuery(this, scope, 2,
                        ProjectDocument.class, true);
                final HstQueryResult result = hstQuery.execute();

                referringProjectDocuments = new LinkedList<>();

                ProjectDocument projectDoc;

                for (HippoBeanIterator it = result.getHippoBeans(); it.hasNext(); ) {
                    projectDoc = (ProjectDocument) it.nextHippoBean();
                    referringProjectDocuments.add(projectDoc);
                }
            } catch (IllegalStateException | QueryException e) {
                log.error("Failed to query projects referring to this task.", e);
            }
        }

        if (referringProjectDocuments == null) {
            return Collections.emptyList();
        }

        return referringProjectDocuments;
    }
}
