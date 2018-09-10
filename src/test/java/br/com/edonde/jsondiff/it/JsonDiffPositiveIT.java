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
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.edonde.jsondiff.model.Diff;
import br.com.edonde.jsondiff.model.DiffElement.DiffResult;
import br.com.edonde.jsondiff.model.Request;
import br.com.edonde.jsondiff.model.Response;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JsonDiffPositiveIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testDiffWithEqualJsons() throws Exception {
        Random random = new SecureRandom();
        String id = String.valueOf(random.nextInt(1000));

        Encoder encoder = Base64.getEncoder();

        String inputJson = "{\"name1\":\"value1\",\"name2\":\"value2\",\"object2\":{\"name3\":\"value3\"}}";
        String encoded = encoder.encodeToString(inputJson.getBytes("UTF-8"));
        Request request = new Request(inputJson);

        ResponseEntity<String> responseLeftEntity =
                restTemplate.postForEntity("/v1/diff/"+id+"/left", request, String.class);
        String responseLeft = responseLeftEntity.getBody();
        assertThat(responseLeft).contains(id);
        assertThat(responseLeft).contains(encoded);

        ResponseEntity<String> responseRightEntity =
                restTemplate.postForEntity("/v1/diff/"+id+"/right", request, String.class);
        String responseRight = responseRightEntity.getBody();
        assertThat(responseRight).contains(id);
        assertThat(responseRight).contains(encoded);

        ResponseEntity<Response> responseDiffEntity =
                restTemplate.getForEntity("/v1/diff/"+id, Response.class);
        Response responseDiff = responseDiffEntity.getBody();
        assertThat(responseDiff.getId()).isEqualTo(id);
        assertThat(responseDiff.getInputLeft()).isEqualTo(encoded);
        assertThat(responseDiff.getInputRight()).isEqualTo(encoded);
        assertThat(responseDiff.getDiffResult()).isEqualTo(DiffResult.EQUAL);
        assertThat(responseDiff.getDiffs()).isEmpty();
    }

    @Test
    public void testDiffWithDifferentSizesJsons() throws Exception {
        Random random = new SecureRandom();
        String id = String.valueOf(random.nextInt(1000));

        Encoder encoder = Base64.getEncoder();

        String inputLeftJson = "{\"name1\":\"value1\",\"name2\":\"value2\",\"object2\":{\"name3\":\"value3\"}}";
        String encodedLeft = encoder.encodeToString(inputLeftJson.getBytes("UTF-8"));
        Request requestLeft = new Request(inputLeftJson);

        String inputRightJson = "{\"name1\":\"value1\",\"name2\":\"value2\",\"object2\":{\"name3\":\"value3\",\"name4\":\"value4\"}}";
        String encodedRight = encoder.encodeToString(inputRightJson.getBytes("UTF-8"));
        Request requestRight = new Request(inputRightJson);

        ResponseEntity<String> responseLeftEntity =
                restTemplate.postForEntity("/v1/diff/"+id+"/left", requestLeft, String.class);
        String responseLeft = responseLeftEntity.getBody();
        assertThat(responseLeft).contains(id);
        assertThat(responseLeft).contains(encodedLeft);

        ResponseEntity<String> responseRightEntity =
                restTemplate.postForEntity("/v1/diff/"+id+"/right", requestRight, String.class);
        String responseRight = responseRightEntity.getBody();
        assertThat(responseRight).contains(id);
        assertThat(responseRight).contains(encodedRight);

        ResponseEntity<Response> responseDiffEntity =
                restTemplate.getForEntity("/v1/diff/"+id, Response.class);
        Response responseDiff = responseDiffEntity.getBody();
        assertThat(responseDiff.getId()).isEqualTo(id);
        assertThat(responseDiff.getInputLeft()).isEqualTo(encodedLeft);
        assertThat(responseDiff.getInputRight()).isEqualTo(encodedRight);
        assertThat(responseDiff.getDiffResult()).isEqualTo(DiffResult.DIFFERENT_SIZES);
        assertThat(responseDiff.getDiffs()).isEmpty();
    }

    @Test
    public void testDiffWithDifferentContentJsons() throws Exception {
        Random random = new SecureRandom();
        String id = String.valueOf(random.nextInt(1000));

        Encoder encoder = Base64.getEncoder();

        String inputLeftJson = "{\"name1\":\"value1\",\"name2\":\"value2\",\"object2\":{\"name3\":\"value3\"}}";
        String encodedLeft = encoder.encodeToString(inputLeftJson.getBytes("UTF-8"));
        Request requestLeft = new Request(inputLeftJson);

        String inputRightJson = "{\"name1\":\"value1\",\"attr2\":\"worth2\",\"object2\":{\"name3\":\"value3\"}}";
        String encodedRight = encoder.encodeToString(inputRightJson.getBytes("UTF-8"));
        Request requestRight = new Request(inputRightJson);

        ResponseEntity<String> responseLeftEntity =
                restTemplate.postForEntity("/v1/diff/"+id+"/left", requestLeft, String.class);
        String responseLeft = responseLeftEntity.getBody();
        assertThat(responseLeft).contains(id);
        assertThat(responseLeft).contains(encodedLeft);

        ResponseEntity<String> responseRightEntity =
                restTemplate.postForEntity("/v1/diff/"+id+"/right", requestRight, String.class);
        String responseRight = responseRightEntity.getBody();
        assertThat(responseRight).contains(id);
        assertThat(responseRight).contains(encodedRight);

        ResponseEntity<Response> responseDiffEntity =
                restTemplate.getForEntity("/v1/diff/"+id, Response.class);
        Response responseDiff = responseDiffEntity.getBody();
        assertThat(responseDiff.getId()).isEqualTo(id);
        assertThat(responseDiff.getInputLeft()).isEqualTo(encodedLeft);
        assertThat(responseDiff.getInputRight()).isEqualTo(encodedRight);
        assertThat(responseDiff.getDiffResult()).isEqualTo(DiffResult.DIFFERENT_CONTENTS);
        assertThat(responseDiff.getDiffs()).hasSize(2);

        Diff diff1 = responseDiff.getDiffs().get(0);
        assertThat(diff1.getOffset()).isEqualTo(19);
        assertThat(diff1.getLength()).isEqualTo(4);

        Diff diff2 = responseDiff.getDiffs().get(1);
        assertThat(diff2.getOffset()).isEqualTo(27);
        assertThat(diff2.getLength()).isEqualTo(5);
    }
}