package info.jchein.pwstatus.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan( { "info.jchein.pwstatus.spi.impl", "info.jchein.pwstatus.impl" } )
public class PasswordServiceConfiguration {
}
