package info.jchein.pwstatus.test.integration;

import info.jchein.pwstatus.IPasswordValidationService;
import info.jchein.pwstatus.model.Password;
import info.jchein.pwstatus.model.PasswordModelFactory;
import info.jchein.pwstatus.model.PasswordValidity;
import info.jchein.pwstatus.spring.PasswordServiceConfiguration;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=PasswordServiceConfiguration.class, loader=AnnotationConfigContextLoader.class)
public class TestPasswordServiceProviderInjection extends TestCase {

	@Autowired
	IPasswordValidationService validationService;
	
	private static final ArrayList<String> expectedFailure = new ArrayList<String>(3);
	static {
		expectedFailure.add( "This is bad" );
		expectedFailure.add( "This is very bad" );
		expectedFailure.add( "This is very very bad" );
	}
	
	@Test
	public void testBasicWiring() {
		Password testPassword = PasswordModelFactory.createPassword("abc123");
		PasswordValidity result = validationService.checkValidity(testPassword);
		assertNotNull(result);
		assertEquals(expectedFailure, result.getErrors());
		assertFalse(result.isValid());
	}
}
