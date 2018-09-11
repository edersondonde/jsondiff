package br.com.edonde.jsondiff.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class to be used when an exception happens during
 * decoding/deserializing of a Base64 encoded string.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class Base64DeserializationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public Base64DeserializationException(String message) {
        super(message);
    }

}
