package br.com.edonde.jsondiff.controller;

import static br.com.edonde.jsondiff.model.DiffElement.Side.LEFT;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import br.com.edonde.jsondiff.exceptions.DiffNotFoundException;
import br.com.edonde.jsondiff.exceptions.MissingDiffInputException;
import br.com.edonde.jsondiff.model.Diff;
import br.com.edonde.jsondiff.model.DiffElement;
import br.com.edonde.jsondiff.model.DiffElement.DiffResult;
import br.com.edonde.jsondiff.repository.DiffElementRepository;

/**
 * Main class to process the differences between the inputs.
 * After setting both left and right jsons, it calculates the
 * difference between them, and set the results on object
 * {@link diffElement} related to that id.
 */
@Service
public class DiffCalculatorService {

    @Autowired
    private DiffElementRepository repository;

    @Autowired
    private TaskExecutor taskExecutor;

    /**
     * Sets the json on one of the sides of the diff.
     *
     * @param id The id of the diff.
     * @param json Json string to be set.
     * @param side Side to be set.
     */
    public void setSide(String id, String json, DiffElement.Side side) {
        DiffElement diffElement = repository.findById(id).orElse(new DiffElement(id));
        if (LEFT.equals(side)) {
            diffElement.setLeft(json);
        } else {
            diffElement.setRight(json);
        }
        if (diffElement.areBothSidesSet()) {
            calculateDiffAsync(diffElement);
        }
        repository.save(diffElement);
    }

    /**
     * Executes the diff calculation async, unblocking the input post request.
     *
     * @param diffElement The {@link DiffElement} to calculate the diff.
     */
    private void calculateDiffAsync(DiffElement diffElement) {
        taskExecutor.execute(() -> {
            List<Diff> diffs = calculateDiff(diffElement.getLeft(), diffElement.getRight());
            DiffResult diffResult = retrieveDiffResult(diffElement.getLeft(), diffElement.getRight());
            diffElement.setDiffs(diffs);
            diffElement.setDiffResult(diffResult);
        });

    }

    /**
     * Retrieve the diff result between the inputs.
     * <br>
     * <ul>
     * <li>If the inputs are equal, {@link DiffResult#EQUAL} is returned;</li>
     * <li>If the inputs are of different size, {@link DiffResult#DIFFERENT_SIZES} is returned;</li>
     * <li>If the inputs are of same size and different contents, {@link DiffResult#DIFFERENT_CONTENTS} is returned;</li>
     *
     * @param left The left string to be diff'ed.
     * @param right The right string to be diff'ed.
     * @return The {@link DiffResult} representing the difference between the inputs.
     */
    DiffResult retrieveDiffResult(String left, String right) {
        if(left.equals(right)) {
            return DiffResult.EQUAL;
        } if (left.length() != right.length()) {
            return DiffResult.DIFFERENT_SIZES;
        }
        return DiffResult.DIFFERENT_CONTENTS;
    }

    /**
     * Executes the calculation of the diff between the elements.
     *
     * @param left The left string to be diff'ed.
     * @param right The right string to be diff'ed.
     * @return List of {@link Diff} objects. Empty List if no differences were found.
     */
    List<Diff> calculateDiff(String left, String right) {
        List<Diff> diffs = new ArrayList<>();

        if (left.equals(right) || left.length() != right.length()) {
            return diffs;
        }

        int offset = 0;
        int length = 0;
        boolean differenceFound = false;
        Diff diff;
        for (int i = 0; i < left.length(); ++i) {
            if (!differenceFound) {
                if (left.charAt(i) != right.charAt(i)) {
                    differenceFound = true;
                    offset = i;
                    length = 1;
                }
            } else {
                if (left.charAt(i) != right.charAt(i)) {
                    length++;
                } else {
                    diff = new Diff();
                    diff.setOffset(offset);
                    diff.setLength(length);
                    diffs.add(diff);
                    differenceFound = false;
                }
            }
        }

        return diffs;
    }

    /**
     * Returns the calculated diff between the inputs.
     *
     * @param id The id of the diff to be calculated.
     * @return The list of diffs found.
     * @throws DiffNotFoundException If no diff with that id was found.
     * @throws MissingDiffInputException If one of the sides of the diff was not set.
     */
    public DiffElement getDiff(String id) throws DiffNotFoundException, MissingDiffInputException {
        Optional<DiffElement> optionalDiffElement = repository.findById(id);
        if (!optionalDiffElement.isPresent()) {
            throw new DiffNotFoundException(String.format("No diff with id %s was found", id));
        }
        DiffElement diffElement = optionalDiffElement.get();
        if (!diffElement.areBothSidesSet()) {
            throw new MissingDiffInputException("The left and/or right jsons were not specified.");
        }
        return diffElement;
    }

    void setRepository(DiffElementRepository repository) {
        this.repository = repository;
    }

}
