package info.jchein.pwstatus.test.integration;

import info.jchein.pwstatus.IPasswordValidationService;
import info.jchein.pwstatus.fixtures.ExpectedMessages;
import info.jchein.pwstatus.model.PasswordValidity;
import info.jchein.pwstatus.model.PasswordValidity.ResultKind;
import info.jchein.pwstatus.spring.PasswordServiceConfiguration;

import java.util.HashSet;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class TestPasswordServiceProviderInjection extends TestCase {

	@Configuration
	@Import( PasswordServiceConfiguration.class )
	@ComponentScan( "info.jchein.pwstatus.fixtures.spi" )
	public static class CustomConfiguration {
		
	}
	
	@Autowired
	IPasswordValidationService validationService;
	
	private static final HashSet<String> expectedFailureOne = new HashSet<String>(3);
	static {
		expectedFailureOne.add( "This is bad" );
		expectedFailureOne.add( "This is very bad" );
		expectedFailureOne.add( "This is very very bad" );
	}
	
	private static final HashSet<String> expectedFailureTwo = new HashSet<String>(3);
	static {
		expectedFailureTwo.add( "This is bad" );
		expectedFailureTwo.add( "This is very bad" );
		expectedFailureTwo.add( "This is very very bad" );
		expectedFailureTwo.add( ExpectedMessages.CHAR_SET_ERROR );
		expectedFailureTwo.add( ExpectedMessages.MAX_LENGTH_ERROR );
		expectedFailureTwo.add( ExpectedMessages.NO_REPETITION_ERROR );
	}
	
	@Test
	public void testExtensibility() {
		PasswordValidity result = 
			validationService.checkValidity(
				"abc123".toCharArray()
			);
		assertNotNull(result);
		assertEquals(expectedFailureOne, new HashSet<String>(result.getErrors()));
		assertEquals(ResultKind.INVALID,result.getResult());
	}
	
	@Test
	public void testRetention() {
		PasswordValidity result = 
			validationService.checkValidity(
				"abc12@c12@abc12@c12@".toCharArray()
			);
		assertNotNull(result);
		assertEquals(expectedFailureTwo, new HashSet<String>(result.getErrors()));
		assertEquals(ResultKind.INVALID,result.getResult());
	}
}
