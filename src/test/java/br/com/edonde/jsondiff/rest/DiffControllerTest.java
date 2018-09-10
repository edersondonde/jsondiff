package br.com.edonde.jsondiff.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.edonde.jsondiff.controller.DiffCalculatorService;
import br.com.edonde.jsondiff.exceptions.DiffNotFoundException;
import br.com.edonde.jsondiff.exceptions.MissingDiffInputException;
import br.com.edonde.jsondiff.model.DiffElement;
import br.com.edonde.jsondiff.model.DiffElement.DiffResult;

/**
 * Test class for {@link DiffController}
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DiffControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private DiffController diffController;

    @Mock
    private DiffCalculatorService diffCalculator;

    @Mock
    private DiffElement diffElement;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Given a REST interface and mocked diffCalculator with inputLeft and empty diff list<br>
     * When I send a get request to /v1/diff/&lt;id&gt;<br>
     * Then I should receive a response with status ok and a response with id, inputLeft and diff list.
     *
     * @throws Exception if an error happens during get request
     */
    @Test
    public void testDiffGet() throws Exception {
        Random random = new SecureRandom();
        String id = String.valueOf(random.nextInt(1000));
        when(diffElement.getLeft()).thenReturn("TestData");
        when(diffElement.getRight()).thenReturn("TestData");
        when(diffElement.getDiffs()).thenReturn(new ArrayList<>());
        when(diffElement.getDiffResult()).thenReturn(DiffResult.EQUAL);

        when(diffCalculator.getDiff(id)).thenReturn(diffElement);

        diffController.setDiffCalculator(diffCalculator);

        mvc.perform(MockMvcRequestBuilders.get("/v1/diff/"+id)
           .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(content().string(equalTo("{\"id\":\""+id+"\",\"inputLeft\":\"VGVzdERhdGE=\",\"inputRight\":\"VGVzdERhdGE=\",\"diffResult\":\"EQUAL\",\"diffs\":[]}")));
    }

    /**
     * Given a REST interface and a diff id that doesn't exists<br>
     * When I send a get request to /v1/diff/&lt;id&gt;<br>
     * Then I should receive an error 404.
     *
     * @throws Exception if an error happens during get request.
     */
    @Test
    public void testDiffGetEmptyInput() throws Exception {
        Random random = new SecureRandom();
        String id = String.valueOf(random.nextInt(1000));
        String errorMessage = "ErrorMessage";

        when(diffCalculator.getDiff(id)).thenThrow(new DiffNotFoundException(errorMessage));
        diffController.setDiffCalculator(diffCalculator);

        mvc.perform(MockMvcRequestBuilders.get("/v1/diff/"+id)
           .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isNotFound());
    }

    /**
     * Given a REST interface and a diff id that is not correctly initialized<br>
     * When I send a get request to /v1/diff/&lt;id&gt;<br>
     * Then I should receive an error 400.
     *
     * @throws Exception if an error happens during get request.
     */
    @Test
    public void testDiffGetInputNotInitialized() throws Exception {
        Random random = new SecureRandom();
        String id = String.valueOf(random.nextInt(1000));
        String errorMessage = "ErrorMessage";

        when(diffCalculator.getDiff(id)).thenThrow(new MissingDiffInputException(errorMessage));
        diffController.setDiffCalculator(diffCalculator);

        mvc.perform(MockMvcRequestBuilders.get("/v1/diff/"+id)
           .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isBadRequest());
    }
}
