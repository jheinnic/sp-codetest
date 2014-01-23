package info.jchein.pwstatus.test.integration;

import info.jchein.pwstatus.IPasswordValidationService;
import info.jchein.pwstatus.fixtures.scenariodata.ExpectedMessages;
import info.jchein.pwstatus.fixtures.scenariodata.ExpectedMessages.AdditionalErrorsException;
import info.jchein.pwstatus.fixtures.scenariodata.ExpectedMessages.BuiltInErrorKind;
import info.jchein.pwstatus.fixtures.scenariodata.ExpectedMessages.PasswordScenario;
import info.jchein.pwstatus.model.PasswordValidity;

import java.util.EnumSet;
import java.util.Iterator;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractIntegrationTestCase extends TestCase {

	// Subject under test from Spring context
	@Autowired
	private IPasswordValidationService validationService;
	
	// Iteration through scenario meta-data.  Each test case extracts is associated with one
	// this collection, but performs the same test logic.  Each instance of scenario meta-data 
	// includes both input to the password validation service and a description of expected
	// results.
	private static final Iterator<PasswordScenario> SCENARIO_ITER = 
		ExpectedMessages.getFixtureData().iterator();

	private static PasswordScenario SCENARIO_META_01 = 
		SCENARIO_ITER.next();
	@Test
	public void testScenario01() {
		runScenario(SCENARIO_META_01);
	}

	private static PasswordScenario SCENARIO_META_02 = 
		SCENARIO_ITER.next();
	@Test
	public void testScenario02() {
		runScenario(SCENARIO_META_02);
	}

	private static PasswordScenario SCENARIO_META_03 = 
		SCENARIO_ITER.next();
	@Test
	public void testScenario03() {
		runScenario(SCENARIO_META_03);
	}

	private static PasswordScenario SCENARIO_META_04 = 
		SCENARIO_ITER.next();
	@Test
	public void testScenario04() {
		runScenario(SCENARIO_META_04);
	}

	private static PasswordScenario SCENARIO_META_05 = 
		SCENARIO_ITER.next();
	@Test
	public void testScenario05() {
		runScenario(SCENARIO_META_05);
	}

	private static PasswordScenario SCENARIO_META_06 = 
		SCENARIO_ITER.next();
	@Test
	public void testScenario06() {
		runScenario(SCENARIO_META_06);
	}

	private static PasswordScenario SCENARIO_META_07 = 
		SCENARIO_ITER.next();
	@Test
	public void testScenario07() {
		runScenario(SCENARIO_META_07);
	}

	private static PasswordScenario SCENARIO_META_08 = 
		SCENARIO_ITER.next();
	@Test
	public void testScenario08() {
		runScenario(SCENARIO_META_08);
	}

	private static PasswordScenario SCENARIO_META_09 = 
		SCENARIO_ITER.next();
	@Test
	public void testScenario09() {
		runScenario(SCENARIO_META_09);
	}

	private static PasswordScenario SCENARIO_META_10 = 
		SCENARIO_ITER.next();
	@Test
	public void testScenario10() {
		runScenario(SCENARIO_META_10);
	}

	private static PasswordScenario SCENARIO_META_11 = 
		SCENARIO_ITER.next();
	@Test
	public void testScenario11() {
		runScenario(SCENARIO_META_11);
	}

	private static PasswordScenario SCENARIO_META_12 = 
		SCENARIO_ITER.next();
	@Test
	public void testScenario12() {
		runScenario(SCENARIO_META_12);
	}

	private static PasswordScenario SCENARIO_META_13 = 
		SCENARIO_ITER.next();
	@Test
	public void testScenario13() {
		runScenario(SCENARIO_META_13);
	}

	private static PasswordScenario SCENARIO_META_14 = 
		SCENARIO_ITER.next();
	@Test
	public void testScenario14() {
		runScenario(SCENARIO_META_14);
	}

	private static PasswordScenario SCENARIO_META_15 = 
		SCENARIO_ITER.next();
	@Test
	public void testScenario15() {
		runScenario(SCENARIO_META_15);
	}

	private static PasswordScenario SCENARIO_META_16 = 
		SCENARIO_ITER.next();
	@Test
	public void testScenario16() {
		runScenario(SCENARIO_META_16);
	}

	private static PasswordScenario SCENARIO_META_17 = 
		SCENARIO_ITER.next();
	@Test
	public void testScenario17() {
		runScenario(SCENARIO_META_17);
	}

	private static PasswordScenario SCENARIO_META_18 = 
		SCENARIO_ITER.next();
	@Test
	public void testScenario18() {
		runScenario(SCENARIO_META_18);
	}

	private static PasswordScenario SCENARIO_META_19 = 
		SCENARIO_ITER.next();
	@Test
	public void testScenario19() {
		runScenario(SCENARIO_META_19);
	}

	private static PasswordScenario SCENARIO_META_20 = 
		SCENARIO_ITER.next();
	@Test
	public void testScenario20() {
		runScenario(SCENARIO_META_20);
	}

	private static PasswordScenario SCENARIO_META_21 = 
		SCENARIO_ITER.next();
	@Test
	public void testScenario21() {
		runScenario(SCENARIO_META_21);
	}

	/**
	 * Run the most recently dequeued test scenario.
	 */
	private void runScenario(PasswordScenario fixtureScenario) {
		final PasswordValidity result = 
			validationService.checkValidity(
				fixtureScenario.getPasswordChars()
			);
		assertNotNull(result);
		
		EnumSet<BuiltInErrorKind> actualErrors = null;
		try {
			actualErrors = ExpectedMessages.toEnumSet(
				result.getErrors()
			);
		} catch (AdditionalErrorsException e) {
			fail("Expected validation of " +
				 fixtureScenario.getPasswordString() +
				 " to yield: " +
				 fixtureScenario.getExpectedErrors() +
				 "Recognized these known errors in result: " +
				 e.getKnownErrors().toString() +
				 "\nAdditional unrecognized and/or duplicate errors were unexpected: " +
				 e.getAdditionalStrings().toString()
			);
		}
		assertEquals(
			fixtureScenario.getExpectedErrors(), actualErrors
		);
		
		assertEquals(
			fixtureScenario.getExpectedValidity(),
			result.getResult()
		);
	}
}
