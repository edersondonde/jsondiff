package br.com.edonde.jsondiff.rest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiffController {

    @RequestMapping("/v1/diff/{id}")
    public String index(@PathVariable String id) {
        return String.format("Received request for diff id=%s", id);
    }

}
