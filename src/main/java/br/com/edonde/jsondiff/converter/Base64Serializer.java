package br.com.edonde.jsondiff.converter;

import java.io.IOException;

import org.springframework.util.Base64Utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Base64 Encoder for string
 */
public class Base64Serializer extends StdSerializer<String>{

    private static final long serialVersionUID = 1L;

    public Base64Serializer() {
        this(null);
    }

    protected Base64Serializer(Class<String> t) {
        super(t);
    }

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String valueEncoded = Base64Utils.encodeToString(value.getBytes("UTF-8"));
        gen.writeStartObject();
        gen.writeString(valueEncoded);
        gen.writeEndObject();
    }

}
