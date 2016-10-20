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

import org.hippoecm.hst.content.beans.standard.HippoBean;

/**
 * Abstract base resource class which defines the {@link #represent(HippoBean)} method
 * which should be implemented by child classes to convert a {@link HippoBean} instance
 * to a resource representing POJO bean.
 */
abstract public class AbstractResource {

    /**
     * Convert the {@code hippoBean} to a resource representing POJO bean.
     * @param hippoBean a {@link HippoBean} instance
     * @return a resource representing POJO bean
     */
    abstract public <T extends AbstractResource> T represent(HippoBean hippoBean);

}