package info.jchein.pwstatus.test.integration;

import info.jchein.pwstatus.IPasswordValidationService;
import info.jchein.pwstatus.fixtures.ExpectedMessages;
import info.jchein.pwstatus.model.PasswordValidity;
import info.jchein.pwstatus.model.PasswordValidity.ResultKind;
import info.jchein.pwstatus.spring.PasswordServiceConfiguration;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=PasswordServiceConfiguration.class, loader=AnnotationConfigContextLoader.class)
public class TestPasswordServiceExtension extends TestCase {

	@Autowired
	IPasswordValidationService validationService;
	
	private static final ArrayList<String> expectedFailure = new ArrayList<String>(3);
	static {
		expectedFailure.add(ExpectedMessages.MAX_LENGTH_ERROR);
		expectedFailure.add(ExpectedMessages.CHAR_SET_ERROR);
		expectedFailure.add(ExpectedMessages.MIN_DIGIT_ERROR);
		expectedFailure.add(ExpectedMessages.NO_REPETITION_ERROR);
	}
	
	@Test
	public void testBasicWiring() {
		final PasswordValidity result = 
			validationService.checkValidity(
				"FajFpJFapJFAp".toCharArray()
			);
		assertNotNull(result);
		assertEquals(
			ExpectedMessages.SAMPLE_INVALID_TESTS.get("FajFpJFapJFAp"), result.getErrors()
		);
		assertEquals(
			ResultKind.INVALID, result.getResult()
		);
	}
	
	@Test
	public void testSeveralInvalidScenarios() {
		for( Map.Entry<String, Set<String>> nextTestEntry : ExpectedMessages.SAMPLE_INVALID_TESTS.entrySet() ) {
			final String nextPassword = nextTestEntry.getKey();
			final Set<String> expectedErrorSet = nextTestEntry.getValue();
			final PasswordValidity result = 
				validationService.checkValidity(
					nextPassword.toCharArray()
				);
			System.out.println(nextPassword);
			assertNotNull(result);
			assertEquals(
				expectedErrorSet, result.getErrors()
			);
			assertEquals(
				ResultKind.INVALID, result.getResult()
			);
		}
	}
	
	@Test
	public void testSeveralValidScenarios() {
		for( final String nextPassword : ExpectedMessages.SAMPLE_VALID_TESTS ) {
			final PasswordValidity result = 
				validationService.checkValidity(
					nextPassword.toCharArray()
				);
			System.out.println(nextPassword);
			assertNotNull(result);
			assertEquals(
				0, result.getErrors().size()
			);
			assertEquals(
				ResultKind.VALID, result.getResult()
			);
		}
	}
}
