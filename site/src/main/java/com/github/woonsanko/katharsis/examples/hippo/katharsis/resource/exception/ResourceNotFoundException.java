package com.github.woonsanko.katharsis.examples.hippo.katharsis.resource.exception;

import io.katharsis.errorhandling.exception.KatharsisException;

public class ResourceNotFoundException extends KatharsisException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String detail) {
        super(detail);
    }

}
