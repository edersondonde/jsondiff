package br.com.edonde.jsondiff.converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Base64;
import java.util.Base64.Encoder;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;

/**
 * Test class for {@link Base64Deserializer}
 */
@RunWith(MockitoJUnitRunner.class)
public class Base64DeserializerTest {

    @Mock
    private JsonParser parser;

    @Mock
    private DeserializationContext context;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Given a valid encoded base64 String<br>
     * When I receive it<br>
     * Then I should return the original decoded String
     *
     * @throws Exception if and exception on decoding happens
     */
    @Test
    public void testDecodeValidBase64String() throws Exception {
        String originalString = "Test String";
        Encoder encoder = Base64.getEncoder();
        String encodedString = encoder.encodeToString(originalString.getBytes("UTF-8"));
        when(parser.getValueAsString()).thenReturn(encodedString);

        Base64Deserializer base64Decoder = new Base64Deserializer();

        String resultString = base64Decoder.deserialize(parser, context);
        assertThat(resultString).isEqualTo(originalString);
    }

    /**
     * Given an invalid encoded base64 String<br>
     * When I receive it<br>
     * Then I should throw an IllegalArgumentException
     *
     * @throws Exception if and exception on decoding happens
     */
    @Test
    public void testDecodeInvalidBase64String() throws Exception {
        String invalidBase64String = "Test String";
        when(parser.getValueAsString()).thenReturn(invalidBase64String);

        thrown.expect(IllegalArgumentException.class);

        Base64Deserializer base64Decoder = new Base64Deserializer();
        base64Decoder.deserialize(parser, context);
    }

}
