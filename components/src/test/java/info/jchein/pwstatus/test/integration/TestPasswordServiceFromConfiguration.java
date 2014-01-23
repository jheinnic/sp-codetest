package info.jchein.pwstatus.test.integration;

import info.jchein.pwstatus.fixtures.spring.PasswordServiceConfiguration;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@ContextConfiguration(classes=PasswordServiceConfiguration.class, loader=AnnotationConfigContextLoader.class)
public class TestPasswordServiceFromConfiguration extends AbstractIntegrationTestCase {
}
