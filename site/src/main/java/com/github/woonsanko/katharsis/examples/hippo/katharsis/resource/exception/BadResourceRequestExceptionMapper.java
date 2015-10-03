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
package com.github.woonsanko.katharsis.examples.hippo.katharsis.resource.exception;

import javax.servlet.http.HttpServletResponse;

import io.katharsis.errorhandling.ErrorData;
import io.katharsis.errorhandling.ErrorDataBuilder;
import io.katharsis.errorhandling.ErrorResponse;
import io.katharsis.errorhandling.mapper.ExceptionMapperProvider;
import io.katharsis.errorhandling.mapper.JsonApiExceptionMapper;

/**
 * Custom {@link JsonApiExceptionMapper} implementation
 * which is registered during the initialization and invoked when {@link BadResourceRequestException} occurs
 * by katharsis-core, in order to generate JSON API error response data.
 */
@ExceptionMapperProvider
public class BadResourceRequestExceptionMapper implements JsonApiExceptionMapper<BadResourceRequestException> {

    @Override
    public ErrorResponse toErrorResponse(BadResourceRequestException exception) {
        ErrorData errorData = new ErrorDataBuilder()
            .setStatus(Integer.toString(HttpServletResponse.SC_BAD_REQUEST))
            .setTitle("Bad request")
            .setDetail(exception.getMessage())
            .build();

        return ErrorResponse.builder()
                .setStatus(HttpServletResponse.SC_BAD_REQUEST)
                .setSingleErrorData(errorData)
                .build();
    }

}
