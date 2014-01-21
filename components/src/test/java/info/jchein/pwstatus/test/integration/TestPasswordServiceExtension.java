package info.jchein.pwstatus.test.integration;

import static info.jchein.pwstatus.fixtures.ExpectedMessages.*;
import info.jchein.pwstatus.IPasswordValidationService;
import info.jchein.pwstatus.model.PasswordValidity;
import info.jchein.pwstatus.model.PasswordValidity.ResultKind;
import info.jchein.pwstatus.spring.PasswordServiceConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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
		expectedFailure.add(MAX_LENGTH_ERROR);
		expectedFailure.add(CHAR_SET_ERROR);
		expectedFailure.add(MIN_DIGIT_ERROR);
		expectedFailure.add(NO_REPETITION_ERROR);
	}
	
	@Test
	public void testBasicWiringInvalidOne() {
		assertExpectedErrors(
			INVALID_SAMPLE_ONE_CHARS, 
			INVALID_SAMPLE_ONE_ERRS
		);
	}
	
	@Test
	public void testBasicWiringInvalidTwo() {
		assertExpectedErrors(
			INVALID_SAMPLE_TWO_CHARS, 
			INVALID_SAMPLE_TWO_ERRS
		);
	}
	
	@Test
	public void testBasicWiringInvalidThree() {
		assertExpectedErrors(
			INVALID_SAMPLE_THREE_CHARS, 
			INVALID_SAMPLE_THREE_ERRS
		);
	}
	
	@Test
	public void testBasicWiringInvalidFour() {
		assertExpectedErrors(
			INVALID_SAMPLE_FOUR_CHARS, 
			INVALID_SAMPLE_FOUR_ERRS
		);
	}
	
	@Test
	public void testBasicWiringInvalidFive() {
		assertExpectedErrors(
			INVALID_SAMPLE_FIVE_CHARS, 
			INVALID_SAMPLE_FIVE_ERRS
		);
	}
	
	@Test
	public void testBasicWiringInvalidSix() {
		assertExpectedErrors(
			INVALID_SAMPLE_SIX_CHARS, 
			INVALID_SAMPLE_SIX_ERRS
		);
	}
	
	@Test
	public void testBasicWiringInvalidSeven() {
		assertExpectedErrors(
			INVALID_SAMPLE_SEVEN_CHARS, 
			INVALID_SAMPLE_SEVEN_ERRS
		);
	}
	
	@Test
	public void testBasicWiringInvalidEight() {
		assertExpectedErrors(
			INVALID_SAMPLE_EIGHT_CHARS, 
			INVALID_SAMPLE_EIGHT_ERRS
		);
	}
	
	@Test
	public void testBasicWiringInvalidNine() {
		assertExpectedErrors(
			INVALID_SAMPLE_NINE_CHARS, 
			INVALID_SAMPLE_NINE_ERRS
		);
	}
	
	@Test
	public void testBasicWiringInvalidTen() {
		assertExpectedErrors(
			INVALID_SAMPLE_TEN_CHARS, 
			INVALID_SAMPLE_TEN_ERRS
		);
	}
	
	@Test
	public void testBasicWiringInvalidEleven() {
		assertExpectedErrors(
			INVALID_SAMPLE_ELEVEN_CHARS, 
			INVALID_SAMPLE_ELEVEN_ERRS
		);
	}
		
	private void assertExpectedErrors(
		final char[] invalidInputPassword, 
		final Set<String> expectedErrors
	) {
		final PasswordValidity result = 
			validationService.checkValidity(invalidInputPassword);
		assertNotNull(result);
		assertEquals(
			expectedErrors, new HashSet<String>(result.getErrors())
		);
		assertEquals(
			ResultKind.INVALID, result.getResult()
		);
	}
	
	@Test
	public void testValidScenarioOne() {
		assertExpectedSuccess(VALID_SAMPLE_ONE_CHARS);
	}
	
	@Test
	public void testValidScenarioTwo() {
		assertExpectedSuccess(VALID_SAMPLE_TWO_CHARS);
	}

	@Test
	public void testValidScenarioThree() {
		assertExpectedSuccess(VALID_SAMPLE_THREE_CHARS);
	}

	@Test
	public void testValidScenarioFour() {
		assertExpectedSuccess(VALID_SAMPLE_FOUR_CHARS);
	}

	@Test
	public void testValidScenarioFive() {
		assertExpectedSuccess(VALID_SAMPLE_FIVE_CHARS);
	}

	@Test
	public void testValidScenarioSix() {
		assertExpectedSuccess(VALID_SAMPLE_SIX_CHARS);
	}

	@Test
	public void testValidScenarioSeven() {
		assertExpectedSuccess(VALID_SAMPLE_SEVEN_CHARS);
	}

	@Test
	public void testValidScenarioEight() {
		assertExpectedSuccess(VALID_SAMPLE_EIGHT_CHARS);
	}

	@Test
	public void testValidScenarioNine() {
		assertExpectedSuccess(VALID_SAMPLE_NINE_CHARS);
	}

	@Test
	public void testValidScenarioTen() {
		assertExpectedSuccess(VALID_SAMPLE_TEN_CHARS);
	}
	
	private void assertExpectedSuccess(final char[] invalidInputPassword) {
		final PasswordValidity result = 
			validationService.checkValidity(invalidInputPassword);
		assertNotNull(result);
		assertEquals(
			Collections.emptySet(), new HashSet<String>(result.getErrors())
		);
		assertEquals(
			ResultKind.VALID, result.getResult()
		);
	}
}
