package br.com.edonde.jsondiff.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import br.com.edonde.jsondiff.controller.DiffCalculatorService;
import br.com.edonde.jsondiff.model.DiffElement;
import br.com.edonde.jsondiff.model.Request;

/**
 * Input Controller, responsible to receive the base64 jsons for diff.
 */
@RestController
public class InputController {

    private Logger logger = LoggerFactory.getLogger(InputController.class);

    @Autowired
    private DiffCalculatorService calculator;

    @RequestMapping(value="/v1/diff/{id}/left", method=POST, consumes=APPLICATION_JSON_VALUE, produces=APPLICATION_JSON_VALUE)
    public Request receiveLeftJson(@PathVariable String id, @RequestBody Request request) {
        return receiveJson(id, request, DiffElement.Side.LEFT);
    }

    @RequestMapping(value="/v1/diff/{id}/right", method=POST, consumes=APPLICATION_JSON_VALUE, produces=APPLICATION_JSON_VALUE)
    public Request receiveRightJson(@PathVariable String id, @RequestBody Request request) {
        return receiveJson(id, request, DiffElement.Side.RIGHT);
    }

    /**
     * Receives the json and register it on {@link DiffCalculatorService}
     *
     * @param id The id of the request.
     * @param request The request object with input json.
     * @param side The diff side received.
     * @return The created request element.
     */
    private Request receiveJson(String id, Request request, DiffElement.Side side) {
        logger.info(String.format("Received input with id %s and inputString %s", id, request.getInputString()));
        calculator.setSide(id, request.getInputString(), side);
        request.setId(id);
        return request;
    }

}
