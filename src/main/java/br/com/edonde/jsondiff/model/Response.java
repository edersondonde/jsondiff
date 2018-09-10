package br.com.edonde.jsondiff.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.edonde.jsondiff.converter.Base64Serializer;

/**
 * Response object to return the diff results to the client.
 */
public class Response {

    private String id;

    @JsonSerialize(using=Base64Serializer.class)
    private String inputLeft;

    private List<Diff> diffs;

    public Response() {
        diffs = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInputLeft() {
        return inputLeft;
    }

    public void setInputLeft(String inputLeft) {
        this.inputLeft = inputLeft;
    }

    public List<Diff> getDiffs() {
        return diffs;
    }

    public void setDiffs(List<Diff> diffs) {
        this.diffs = diffs;
    }

    public void addDiff (Diff diff) {
        this.diffs.add(diff);
    }

}
