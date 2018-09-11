package br.com.edonde.jsondiff.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.edonde.jsondiff.controller.DiffCalculatorService;
import br.com.edonde.jsondiff.model.DiffElement;
import br.com.edonde.jsondiff.model.Response;

/**
 * Diff Controller. Returns the result of the diff.
 */
@RestController
public class DiffController {

    @Autowired
    private DiffCalculatorService diffCalculator;

    @RequestMapping(value="/v1/diff/{id}", method=GET, produces=APPLICATION_JSON_VALUE)
    public Response getDiffResult(@PathVariable String id) {
        DiffElement diff = diffCalculator.getDiff(id);
        Response response = generateResponse(diff);
        return response;
    }

    /**
     * Generates the response to the request
     *
     * @param diff The diff object that the response is based on.
     * @return The populated Response object.
     */
    private Response generateResponse(DiffElement diff) {
        Response response = new Response();
        response.setId(diff.getId());
        response.setInputLeft(diff.getLeft());
        response.setInputRight(diff.getRight());
        response.setDiffs(diff.getDiffs());
        response.setDiffResult(diff.getDiffResult());
        return response;
    }

    void setDiffCalculator(DiffCalculatorService diffCalculator) {
        this.diffCalculator = diffCalculator;
    }

}
