package hello.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
// @ImportResource
// @ComponentScan
@PropertySource( { "classpath:rest.properties", "classpath:web.properties" } )
public class SampleConfiguration {
	@Bean
	public static PropertySourcesPlaceholderConfigurer properties() {
	    return new PropertySourcesPlaceholderConfigurer();
	}
}
