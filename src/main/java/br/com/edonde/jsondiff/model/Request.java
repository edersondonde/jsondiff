package br.com.edonde.jsondiff.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.edonde.jsondiff.converter.Base64Deserializer;
import br.com.edonde.jsondiff.converter.Base64Serializer;

/**
 * Request object that contains the input jsons.
 */
public class Request {

    private String id;

    @JsonDeserialize(using=Base64Deserializer.class)
    @JsonSerialize(using=Base64Serializer.class)
    private String inputString;

    public Request() {
        this.inputString = null;
    }

    public Request(String inputString) {
        setInputString(inputString);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInputString() {
        return inputString;
    }

    public void setInputString(String inputString) {
        this.inputString = inputString;
    }

}
