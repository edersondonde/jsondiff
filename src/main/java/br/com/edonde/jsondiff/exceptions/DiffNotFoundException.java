package br.com.edonde.jsondiff.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class to be used when the id was not found.
 * This happens when neither left and right jsons are set.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class DiffNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DiffNotFoundException(String message) {
        super(message);
    }

}
