package com.github.woonsanko.katharsis.examples.hippo.katharsis.resource.exception;

import javax.servlet.http.HttpServletResponse;

import io.katharsis.errorhandling.ErrorData;
import io.katharsis.errorhandling.ErrorDataBuilder;
import io.katharsis.errorhandling.ErrorResponse;
import io.katharsis.errorhandling.mapper.ExceptionMapperProvider;
import io.katharsis.errorhandling.mapper.JsonApiExceptionMapper;

@ExceptionMapperProvider
public class ResourceNotFoundExceptionMapper implements JsonApiExceptionMapper<ResourceNotFoundException> {

    @Override
    public ErrorResponse toErrorResponse(ResourceNotFoundException exception) {
        ErrorData errorData = new ErrorDataBuilder()
            .setStatus(Integer.toString(HttpServletResponse.SC_NOT_FOUND))
            .setTitle("Resource not found")
            .setDetail(exception.getMessage())
            .build();

        return ErrorResponse.builder()
                .setStatus(HttpServletResponse.SC_NOT_FOUND)
                .setSingleErrorData(errorData)
                .build();
    }

}
