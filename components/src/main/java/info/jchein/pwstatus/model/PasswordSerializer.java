package info.jchein.pwstatus.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class PasswordSerializer extends JsonSerializer<Password> {

	@Override
	public void serialize(Password value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		provider.setAttribute("content", value);
	}
}
