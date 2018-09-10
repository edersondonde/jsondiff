package br.com.edonde.jsondiff.converter;

import java.util.Base64;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Test class for {@link Base64Serializer}
 */
@RunWith(MockitoJUnitRunner.class)
public class Base64SerializerTest {

    @Spy
    private JsonGenerator gen;

    @Mock
    private SerializerProvider serializers;

    /**
     * Given a base String<br>
     * When I request to serialize it<br>
     * Then the encoded string shall be written in the target object
     *
     * @throws Exception if an exception on serialization happens.
     */
    @Test
    public void testSerializeStringToBase64 () throws Exception {
        String value = "Test String";
        String expectedValueEncoded = Base64.getEncoder().encodeToString(value.getBytes("UTF-8"));

        Base64Serializer serializer = new Base64Serializer();
        serializer.serialize(value, gen, serializers);

        Mockito.verify(gen).writeStartObject();
        Mockito.verify(gen).writeString(expectedValueEncoded);
        Mockito.verify(gen).writeEndObject();
    }

}
