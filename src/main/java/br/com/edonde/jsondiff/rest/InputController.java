package br.com.edonde.jsondiff.rest;

import org.springframework.web.bind.annotation.*;

/**
 * Input Controller, responsiblle to receive the base64 jsons for diff.
 */
@RestController
public class InputController {

    @PostMapping("/v1/diff/{id}/left")
    public String receiveLeftJson(@PathVariable String id, @RequestBody String jsonBase64) {
        return String.format("Received id=%s and body=%s", id, jsonBase64);
    }

    @PostMapping("/v1/diff/{id}/right")
    public String receiveRightJson(@PathVariable String id, @RequestBody String jsonBase64) {
        return String.format("Received id=%s and body=%s", id, jsonBase64);
    }

}
