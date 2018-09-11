package br.com.edonde.jsondiff.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.security.SecureRandom;
import java.util.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.edonde.jsondiff.exceptions.DiffNotFoundException;
import br.com.edonde.jsondiff.exceptions.MissingDiffInputException;
import br.com.edonde.jsondiff.model.Diff;
import br.com.edonde.jsondiff.model.DiffElement;
import br.com.edonde.jsondiff.model.DiffElement.DiffResult;

/**
 * Test class for {@link DiffCalculatorService}
 */
@RunWith(MockitoJUnitRunner.class)
public class DiffCalculatorServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private DiffElement diffElement;

    /**
     * Given DiffCalculatorService<br>
     * When I call retrieveDiffResult with two equal inputs<br>
     * Then {@link DiffResult#EQUAL} shall be returned
     */
    @Test
    public void testRetrieveDiffResultEqual() {
        DiffCalculatorService diffCalculatorService = new DiffCalculatorService();
        DiffResult result = diffCalculatorService.retrieveDiffResult("1234", "1234");
        assertThat(result).isEqualTo(DiffResult.EQUAL);
    }

    /**
     * Given DiffCalculatorService<br>
     * When I call retrieveDiffResult with two inputs with different sizes<br>
     * Then {@link DiffResult#DIFFERENT_SIZES} shall be returned
     */
    @Test
    public void testRetrieveDiffResultDifferentSizes() {
        DiffCalculatorService diffCalculatorService = new DiffCalculatorService();
        DiffResult result = diffCalculatorService.retrieveDiffResult("12345", "1234");
        assertThat(result).isEqualTo(DiffResult.DIFFERENT_SIZES);
    }

    /**
     * Given DiffCalculatorService<br>
     * When I call retrieveDiffResult with inputs with same size and different contents<br>
     * Then {@link DiffResult#DIFFERENT_CONTENTS} shall be returned
     */
    @Test
    public void testRetrieveDiffResultDifferentContents() {
        DiffCalculatorService diffCalculatorService = new DiffCalculatorService();
        DiffResult result = diffCalculatorService.retrieveDiffResult("1234", "4321");
        assertThat(result).isEqualTo(DiffResult.DIFFERENT_CONTENTS);
    }

    /**
     * Given DiffCalculatorService<br>
     * When I call calculateDiff with two equal inputs<br>
     * Then an empty list shall be returned
     */
    @Test
    public void testCalculateDiffEqualElements() {
        DiffCalculatorService diffCalculatorService = new DiffCalculatorService();
        List<Diff> result = diffCalculatorService.calculateDiff("1234", "1234");
        assertThat(result).isEmpty();
    }

    /**
     * Given DiffCalculatorService<br>
     * When I call calculateDiff with two inputs with different sizes<br>
     * Then an empty list shall be returned
     */
    @Test
    public void testCalculateDiffDifferentSizeElements() {
        DiffCalculatorService diffCalculatorService = new DiffCalculatorService();
        List<Diff> result = diffCalculatorService.calculateDiff("12345", "1234");
        assertThat(result).isEmpty();
    }

    /**
     * Given DiffCalculatorService<br>
     * When I call calculateDiff with two inputs with different contents<br>
     * Then a list with the differences shall be returned
     */
    @Test
    public void testCalculateDiffDifferentContentElements() {
        DiffCalculatorService diffCalculatorService = new DiffCalculatorService();
        List<Diff> result = diffCalculatorService.calculateDiff("1234567890", "12ab56cde0");
        assertThat(result).hasSize(2);

        Diff diff1 = result.get(0);
        assertThat(diff1.getOffset()).isEqualTo(2);
        assertThat(diff1.getLength()).isEqualTo(2);

        Diff diff2 = result.get(1);
        assertThat(diff2.getOffset()).isEqualTo(6);
        assertThat(diff2.getLength()).isEqualTo(3);
    }

    /**
     * Given DiffCalculatorService<br>
     * When I call getDiff with non existing id<br>
     * Then {@link DiffNotFoundException} shall be thrown.
     */
    @Test
    public void testGetDiffWithNotFoundId() {
        Random random = new SecureRandom();
        String id = String.valueOf(random.nextInt(1000));

        thrown.expect(DiffNotFoundException.class);
        thrown.expectMessage("No diff with id "+id+" was found");
        DiffCalculatorService diffCalculatorService = new DiffCalculatorService();
        diffCalculatorService.getDiff(id);
    }

    /**
     * Given DiffCalculatorService<br>
     * When I call getDiff for an element missing inputs<br>
     * Then {@link MissingDiffInputException} shall be thrown.
     */
    @Test
    public void testGetDiffWithInputsNotSet() {
        Random random = new SecureRandom();
        String id = String.valueOf(random.nextInt(1000));

        when(diffElement.areBothSidesSet()).thenReturn(false);
        Map<String, DiffElement> diffElements = new HashMap<>();
        diffElements.put(id, diffElement);

        thrown.expect(MissingDiffInputException.class);
        thrown.expectMessage("The left and/or right jsons were not specified.");

        DiffCalculatorService diffCalculatorService = new DiffCalculatorService();
        DiffCalculatorService.setDiffElements(diffElements);
        diffCalculatorService.getDiff(id);
    }

    /**
     * Given DiffCalculatorService<br>
     * When I call getDiff for an element with the correct inputs set<br>
     * Then the expected element shall be returned.
     */
    @Test
    public void testGetDiff() {
        Random random = new SecureRandom();
        String id = String.valueOf(random.nextInt(1000));

        when(diffElement.areBothSidesSet()).thenReturn(true);
        Map<String, DiffElement> diffElements = new HashMap<>();
        diffElements.put(id, diffElement);

        DiffCalculatorService diffCalculatorService = new DiffCalculatorService();
        DiffCalculatorService.setDiffElements(diffElements);
        DiffElement result = diffCalculatorService.getDiff(id);
        assertThat(result).isSameAs(diffElement);
    }

}
