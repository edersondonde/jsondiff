package br.com.edonde.jsondiff.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.edonde.jsondiff.converter.Base64Serializer;
import br.com.edonde.jsondiff.model.DiffElement.DiffResult;

/**
 * Response object to return the diff results to the client.
 */
public class Response {

    private String id;

    @JsonSerialize(using=Base64Serializer.class)
    private String inputLeft;

    @JsonSerialize(using=Base64Serializer.class)
    private String inputRight;

    private DiffResult diffResult;

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

    public String getInputRight() {
        return inputRight;
    }

    public void setInputRight(String inputRight) {
        this.inputRight = inputRight;
    }

    public DiffResult getDiffResult() {
        return diffResult;
    }

    public void setDiffResult(DiffResult diffResult) {
        this.diffResult = diffResult;
    }

    public List<Diff> getDiffs() {
        return diffs;
    }

    public void setDiffs(List<Diff> diffs) {
        this.diffs = diffs;
    }

}
