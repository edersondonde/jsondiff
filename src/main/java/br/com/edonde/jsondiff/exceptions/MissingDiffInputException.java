package br.com.edonde.jsondiff.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class to be used when one of  the inputs, left or right,
 * is missing.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MissingDiffInputException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MissingDiffInputException(String message) {
        super(message);
    }

}
