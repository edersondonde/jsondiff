package br.com.edonde.jsondiff.converter;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.util.Base64Utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import br.com.edonde.jsondiff.exceptions.Base64DeserializationException;

/**
 * Base64 deserializer for String
 */
public class Base64Deserializer extends StdDeserializer<String>{

    private static final long serialVersionUID = 1L;

    public Base64Deserializer() {
        this(null);
    }

    protected Base64Deserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public String deserialize(JsonParser parser, DeserializationContext context)
            throws IOException, JsonProcessingException {
        try {
            String value = parser.getValueAsString();
            return new String(Base64Utils.decodeFromString(value), Charset.forName("UTF-8"));
        } catch (Exception e) {
            throw new Base64DeserializationException("Error decoding inputString");
        }
    }

}
