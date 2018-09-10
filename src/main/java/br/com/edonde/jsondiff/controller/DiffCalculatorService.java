package br.com.edonde.jsondiff.controller;

import static br.com.edonde.jsondiff.model.DiffElement.Side.LEFT;

import java.util.*;

import org.springframework.stereotype.Service;

import br.com.edonde.jsondiff.exceptions.DiffNotFoundException;
import br.com.edonde.jsondiff.exceptions.MissingDiffInputException;
import br.com.edonde.jsondiff.model.Diff;
import br.com.edonde.jsondiff.model.DiffElement;

/**
 * Main class to process the differences between the inputs.
 * After setting both left and right jsons, it calculates the
 * difference between them, and set the results on object
 * {@link diffElement} related to that id.
 */
@Service
public class DiffCalculatorService {

    private static Map<String, DiffElement> diffElements;

    static {
        diffElements = new HashMap<>();
    }

    /**
     * Sets the json on one of the sides of the diff.
     *
     * @param id The id of the diff.
     * @param json Json string to be set.
     * @param side Side to be set.
     */
    public void setSide(String id, String json, DiffElement.Side side) {
        DiffElement diffElement = diffElements.getOrDefault(id, new DiffElement(id));
        if (LEFT.equals(side)) {
            diffElement.setLeft(json);
        } else {
            diffElement.setRight(json);
        }
        if (diffElement.areBothSidesSet()) {
            calculateDiff(diffElement);
        }
        diffElements.putIfAbsent(id, diffElement);
    }

    /**
     * Executes the calculation of the diff between the elements.
     *
     * @param diffElement The element to calculate the diff.
     */
    private void calculateDiff(DiffElement diffElement) {
        diffElement.setDiffs(new ArrayList<>());
    }

    /**
     * Returns the calculated diff between the inputs.
     *
     * @param id The id of the diff to be calculated.
     * @return The list of diffs found.
     * @throws DiffNotFoundException If no diff with that id was found.
     * @throws MissingDiffInputException If one of the sides of the diff was not set.
     */
    public List<Diff> getDiff(String id) throws DiffNotFoundException, MissingDiffInputException {
        DiffElement diffElement = diffElements.get(id);
        if (diffElement == null) {
            throw new DiffNotFoundException(String.format("No diff with id %s was found", id));
        }
        if (!diffElement.areBothSidesSet()) {
            throw new MissingDiffInputException("The left and/or right jsons were not specified.");
        }
        return diffElement.getDiffs();
    }

    /**
     * Returns the left element of the diff to display in the result json.
     *
     * @param id The id of the diff.
     * @return The input related to that id, null if not found.
     */
    public String getLeft(String id) {
        DiffElement diffElement = diffElements.get(id);
        if (diffElement == null) {
            return null;
        }
        return diffElement.getLeft();
    }

}
