package com.github.woonsanko.katharsis.examples.hippo.katharsis.resource.exception;

import io.katharsis.errorhandling.exception.KatharsisException;

public class BadResourceRequestException extends KatharsisException {

    private static final long serialVersionUID = 1L;

    public BadResourceRequestException(String detail) {
        super(detail);
    }

}
