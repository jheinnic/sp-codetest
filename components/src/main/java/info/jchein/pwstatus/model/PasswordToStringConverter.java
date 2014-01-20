package info.jchein.pwstatus.model;

import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class PasswordToStringConverter implements Converter<Password, String>,
		com.fasterxml.jackson.databind.util.Converter<Password, String> {

	@Override
	public String convert(Password value) {
		return value.getContentString();
	}

	@Override
	public JavaType getInputType(TypeFactory typeFactory) {
		return typeFactory.constructType(Password.class);
	}

	@Override
	public JavaType getOutputType(TypeFactory typeFactory) {
		return typeFactory.constructType(String.class);
	}
}
