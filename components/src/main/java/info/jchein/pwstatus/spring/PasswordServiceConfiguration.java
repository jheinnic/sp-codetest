package info.jchein.pwstatus.spring;

import info.jchein.pwstatus.model.StringToPasswordConverter;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan( { "info.jchein.pwstatus.impl", "info.jchein.pwstatus.fixtures.spi" } )
public class PasswordServiceConfiguration {
	Environment environment;
    
	@Required
	@Autowired
    public void setEnvironment( Environment environment ) {
		this.environment = environment;
	}
	
	/*
	@Bean
	public IPasswordValidationService getPasswordValidationService() {
		return new PasswordValidationService();
	}
	*/
	
	@Bean
	public ConversionServiceFactoryBean getConverterService() {
		ConversionServiceFactoryBean retVal = 
			new ConversionServiceFactoryBean();
		HashSet<Converter<?,?>> converters = new HashSet<Converter<?,?>>(4);
		converters.add(
			new StringToPasswordConverter()
		);
		// converters.add(
		// 	new PasswordToStringConverter()
		// );
		
		retVal.setConverters(converters);
		return retVal;
		
	}
}
