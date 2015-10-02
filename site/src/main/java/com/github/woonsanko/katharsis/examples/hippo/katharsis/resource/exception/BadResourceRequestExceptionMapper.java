package com.github.woonsanko.katharsis.examples.hippo.katharsis.resource.exception;

import io.katharsis.errorhandling.ErrorData;
import io.katharsis.errorhandling.ErrorDataBuilder;
import io.katharsis.errorhandling.ErrorResponse;
import io.katharsis.errorhandling.mapper.ExceptionMapperProvider;
import io.katharsis.errorhandling.mapper.JsonApiExceptionMapper;

import javax.servlet.http.HttpServletResponse;

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
