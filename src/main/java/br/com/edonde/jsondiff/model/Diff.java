package br.com.edonde.jsondiff.model;

/**
 * Diff element, contains the offset and length of the difference between the jsons.
 */
public class Diff {

    private int offset;

    private int length;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

}
