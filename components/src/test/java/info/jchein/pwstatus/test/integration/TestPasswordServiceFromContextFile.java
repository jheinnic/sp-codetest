package info.jchein.pwstatus.test.integration;

import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations="classpath*:pwval-service.xml")
public class TestPasswordServiceFromContextFile extends AbstractIntegrationTestCase {
}
