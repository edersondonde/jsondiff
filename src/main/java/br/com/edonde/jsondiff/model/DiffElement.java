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

    public enum DiffResult {
        EQUAL("Equal"), DIFFERENT_SIZES("Different sizes"), DIFFERENT_CONTENTS("Different Contents");

        private String state;

        private DiffResult(String state) {
            this.state = state;
        }

        @Override
        public String toString() {
            return state;
        }
    }

    private String id;

    private String left;

    private String right;

    private DiffResult diffResult;

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

    public boolean areBothSidesSet() {
        return left != null && right != null;
    }

}
