package br.com.edonde.jsondiff.rest;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * Test class for {@link InputController}
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class InputControllerTest {

    @Autowired
    private MockMvc mvc;

    /**
     * Given a REST interface<br>
     * When I send a post request to /v1/diff/&lt;id&gt;/left<br>
     * Then I should receive a response with status ok and a dummy answer.
     *
     * @throws Exception if an error happens during request
     */
    @Test
    public void testPostLeft() throws Exception {
        String id = "id123";
        String content = "PostContent";
        mvc.perform(MockMvcRequestBuilders.post("/v1/diff/"+id+"/left")
           .content(content)
           .contentType(MediaType.APPLICATION_JSON)
           .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(content().string(equalTo("Received id="+id+" and body="+content)));
    }

    /**
     * Given a REST interface<br>
     * When I send a post request to /v1/diff/&lt;id&gt;/right<br>
     * Then I should receive a response with status ok and a dummy answer.
     *
     * @throws Exception if an error happens during request
     */
    @Test
    public void testPostRight() throws Exception {
        String id = "id123";
        String content = "PostContent";
        mvc.perform(MockMvcRequestBuilders.post("/v1/diff/"+id+"/right")
           .content(content)
           .contentType(MediaType.APPLICATION_JSON)
           .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(content().string(equalTo("Received id="+id+" and body="+content)));
    }
}