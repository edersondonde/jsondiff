package br.com.edonde.jsondiff.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Index Controller. Returns an help message with instructions on how to use
 * the JsonDiff.
 */
@RestController
public class IndexController {

    private static final String MESSAGE =
            "Welcome to JsonDiff!\n" +
            "To use, please provide the jsons base64 encoded " +
            "on the following interfaces:\n" +
            " - /v1/diff/<ID>/left\n" +
            " - /v1/diff/<ID>/right\n" +
            "You can retrieve the result diff on:\n" +
            " - /v1/diff/<ID>";

	@RequestMapping("/")
	public String index() {
		return MESSAGE;
	}

}