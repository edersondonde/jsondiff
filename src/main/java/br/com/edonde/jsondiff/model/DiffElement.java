package br.com.edonde.jsondiff.model;

import java.util.List;

/**
 * Diff element, contains all the structure related to a diff
 * between two inputs.
 */
public class DiffElement {

    public enum Side {
        LEFT, RIGHT;
    }

    private String id;

    private String left;

    private String right;

    private List<Diff> diffs;

    public DiffElement(String id) {
        setId(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLeft() {
        return left;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public String getRight() {
        return right;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public List<Diff> getDiffs() {
        return diffs;
    }

    public void setDiffs(List<Diff> diffs) {
        this.diffs = diffs;
    }

    public boolean areBothSidesSet() {
        return left != null && right != null;
    }

}
