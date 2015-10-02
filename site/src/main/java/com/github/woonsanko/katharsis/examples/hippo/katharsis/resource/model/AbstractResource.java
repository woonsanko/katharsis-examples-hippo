package com.github.woonsanko.katharsis.examples.hippo.katharsis.resource.model;

import org.hippoecm.hst.content.beans.standard.HippoBean;

abstract public class AbstractResource {

    abstract public <T extends AbstractResource> T represent(HippoBean hippoBean);

}
