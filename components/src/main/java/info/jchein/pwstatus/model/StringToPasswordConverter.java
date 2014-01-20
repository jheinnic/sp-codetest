package info.jchein.pwstatus.model;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class StringToPasswordConverter implements Converter<String, Password>,
		com.fasterxml.jackson.databind.util.Converter<String, Password> {

	@Override
	public Password convert(String value) {
		return new Password(value);
	}

	@Override
	public JavaType getInputType(TypeFactory typeFactory) {
		return typeFactory.constructType(String.class);
	}

	@Override
	public JavaType getOutputType(TypeFactory typeFactory) {
		return typeFactory.constructType(Password.class);
	}
}
