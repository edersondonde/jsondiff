package br.com.edonde.jsondiff.rest;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.com.edonde.jsondiff.controller.DiffCalculatorService;
import br.com.edonde.jsondiff.model.DiffElement;
import br.com.edonde.jsondiff.model.Request;

/**
 * Input Controller, responsiblle to receive the base64 jsons for diff.
 */
@RestController
public class InputController {

    private Logger logger = LoggerFactory.getLogger(InputController.class);

    @Autowired
    private DiffCalculatorService calculator;

    @RequestMapping(value="/v1/diff/{id}/left", method=POST)
    public Request receiveLeftJson(@PathVariable String id, @RequestBody Request request) {
        logger.info(String.format("Received input with id %s and inputString %s", id, request.getInputString()));
        calculator.setSide(id, request.getInputString(), DiffElement.Side.LEFT);
        request.setId(id);
        return request;
    }

    @RequestMapping(value="/v1/diff/{id}/right", method=POST)
    public Request receiveRightJson(@PathVariable String id, @RequestBody Request request) {
        logger.info(String.format("Received input with id %s and inputString %s", id, request.getInputString()));
        calculator.setSide(id, request.getInputString(), DiffElement.Side.RIGHT);
        request.setId(id);
        return request;
    }

}
