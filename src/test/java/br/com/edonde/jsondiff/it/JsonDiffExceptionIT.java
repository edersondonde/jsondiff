package br.com.edonde.jsondiff.it;

import static org.assertj.core.api.Assertions.assertThat;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.edonde.jsondiff.model.Request;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JsonDiffExceptionIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testDiffWithInvalidId() throws Exception {
        Random random = new SecureRandom();
        String id = String.valueOf(random.nextInt(1000));

        ResponseEntity<String> responseDiffEntity =
                restTemplate.getForEntity("/v1/diff/"+id, String.class);
        String responseDiff = responseDiffEntity.getBody();
        assertThat(responseDiffEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseDiff).contains("No diff with id "+id+" was found");
    }

    @Test
    public void testDiffWithNotSetLeft() throws Exception {
        Random random = new SecureRandom();
        String id = String.valueOf(random.nextInt(1000));

        Encoder encoder = Base64.getEncoder();

        String inputRightJson = "{\"name1\":\"value1\",\"name2\":\"value2\",\"object2\":{\"name3\":\"value3\",\"name4\":\"value4\"}}";
        String encodedRight = encoder.encodeToString(inputRightJson.getBytes("UTF-8"));
        Request requestRight = new Request(inputRightJson);

        ResponseEntity<String> responseRightEntity =
                restTemplate.postForEntity("/v1/diff/"+id+"/right", requestRight, String.class);
        String responseRight = responseRightEntity.getBody();
        assertThat(responseRight).contains(id);
        assertThat(responseRight).contains(encodedRight);

        ResponseEntity<String> responseDiffEntity =
                restTemplate.getForEntity("/v1/diff/"+id, String.class);
        String responseDiff = responseDiffEntity.getBody();
        assertThat(responseDiffEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseDiff).contains("The left and/or right jsons were not specified");
    }

    @Test
    public void testDiffWithNotSetRight() throws Exception {
        Random random = new SecureRandom();
        String id = String.valueOf(random.nextInt(1000));

        Encoder encoder = Base64.getEncoder();

        String inputLeftJson = "{\"name1\":\"value1\",\"name2\":\"value2\",\"object2\":{\"name3\":\"value3\"}}";
        String encodedLeft = encoder.encodeToString(inputLeftJson.getBytes("UTF-8"));
        Request requestLeft = new Request(inputLeftJson);

        ResponseEntity<String> responseLeftEntity =
                restTemplate.postForEntity("/v1/diff/"+id+"/left", requestLeft, String.class);
        String responseLeft = responseLeftEntity.getBody();
        assertThat(responseLeft).contains(id);
        assertThat(responseLeft).contains(encodedLeft);

        ResponseEntity<String> responseDiffEntity =
                restTemplate.getForEntity("/v1/diff/"+id, String.class);
        String responseDiff = responseDiffEntity.getBody();
        assertThat(responseDiffEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseDiff).contains("The left and/or right jsons were not specified");
    }
}