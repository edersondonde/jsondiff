package br.com.edonde.jsondiff.rest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.edonde.jsondiff.controller.DiffCalculatorService;
import br.com.edonde.jsondiff.model.Diff;
import br.com.edonde.jsondiff.model.Response;

/**
 * Diff Controller. Returns the result of the diff.
 */
@RestController
public class DiffController {

    @Autowired
    private DiffCalculatorService diffCalculator;

    @RequestMapping(value="/v1/diff/{id}", method=GET)
    public Response getDiffResult(@PathVariable String id) {
        List<Diff> diffs = diffCalculator.getDiff(id);
        String inputLeft = diffCalculator.getLeft(id);
        Response response = new Response();
        response.setId(id);
        response.setInputLeft(inputLeft);
        response.setDiffs(diffs);
        return response;
    }

    void setDiffCalculator(DiffCalculatorService diffCalculator) {
        this.diffCalculator = diffCalculator;
    }

}
