package info.jchein.pwstatus.fixtures.scenariodata;

import static info.jchein.pwstatus.fixtures.scenariodata.ExpectedMessages.BuiltInErrorKind.INVALID_CHAR;
import static info.jchein.pwstatus.fixtures.scenariodata.ExpectedMessages.BuiltInErrorKind.LC_ALPHA_REQD;
import static info.jchein.pwstatus.fixtures.scenariodata.ExpectedMessages.BuiltInErrorKind.MAX_LENGTH;
import static info.jchein.pwstatus.fixtures.scenariodata.ExpectedMessages.BuiltInErrorKind.MIN_LENGTH;
import static info.jchein.pwstatus.fixtures.scenariodata.ExpectedMessages.BuiltInErrorKind.NO_REPETITION;
import static info.jchein.pwstatus.fixtures.scenariodata.ExpectedMessages.BuiltInErrorKind.NUMBER_REQD;
import info.jchein.pwstatus.model.PasswordValidity.ResultKind;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

public final class ExpectedMessages {
	public static List<PasswordScenario> getFixtureData() {
		return PUBLIC_FIXTURE_DATA_LIST;
	}
	
	public static EnumSet<BuiltInErrorKind> toEnumSet(
		Collection<String> errorStrings
	) throws AdditionalErrorsException {
		EnumSet<BuiltInErrorKind> retVal = EnumSet.noneOf(BuiltInErrorKind.class);
		if (errorStrings == null || errorStrings.isEmpty()) {
			return retVal;
		}
		
		ArrayList<String> additionalErrors = new ArrayList<String>(); 
		for (final String nextMsg : errorStrings) {
			final BuiltInErrorKind errorKind = MSG_TO_ENUM_MAP.get(nextMsg);
			if (errorKind == null || retVal.add(errorKind) == false) {
				additionalErrors.add(nextMsg);
			}
		}
		
		if (additionalErrors.isEmpty() == false) {
			throw new AdditionalErrorsException(additionalErrors, retVal);
		}
		
		return retVal;
	}

	public enum BuiltInErrorKind {
		MAX_LENGTH(MAX_LENGTH_ERROR),
		MIN_LENGTH(MIN_LENGTH_ERROR),
		INVALID_CHAR(INVALID_CHAR_ERROR),
		LC_ALPHA_REQD(LC_ALPHA_REQD_ERROR),
		NUMBER_REQD(NUMBER_REQD_ERROR),
		NO_REPETITION(NO_REPETITION_ERROR); 
		
		private final String errorMessage;
		
		private BuiltInErrorKind(final String errorMessage) {
			this.errorMessage = errorMessage;
			MSG_TO_ENUM_MAP.put(errorMessage, this);
		}

		public String getErrorMessage() {
			return errorMessage;
		}
	}
	
    public static class AdditionalErrorsException extends Exception {
    	/**
		 */
		private static final long serialVersionUID = 4370688132017117274L;
		
		private final List<String> additionalStrings;
    	private final EnumSet<BuiltInErrorKind> knownErrors;
    	
    	AdditionalErrorsException( 
    		List<String> additionalStrings, EnumSet<BuiltInErrorKind> knownErrors
    	) {
    		super("Error collection included some non-built-in and/or duplicate error strings");
    		this.additionalStrings = additionalStrings;
    		this.knownErrors = knownErrors;
    	}

		public List<String> getAdditionalStrings() {
			return additionalStrings;
		}

		public EnumSet<BuiltInErrorKind> getKnownErrors() {
			return knownErrors;
		}
    }
    
    public static final class PasswordScenario {
    	private final String passwordString;
    	private final EnumSet<BuiltInErrorKind> expectedErrors;
    	
    	PasswordScenario(String passwordString, BuiltInErrorKind... expectedErrors ) {
    		this.passwordString = passwordString;
    		switch(expectedErrors.length) {
    			case 0:
    				this.expectedErrors = EnumSet.noneOf(BuiltInErrorKind.class);
    				break;
    			case 1:
    				this.expectedErrors = EnumSet.of(expectedErrors[0]);
    				break;
    			case 2:
    				this.expectedErrors = 
						EnumSet.of(expectedErrors[0], expectedErrors[1]);
    				break;
    			case 3:
    				this.expectedErrors = 
						EnumSet.of(expectedErrors[0], expectedErrors[1], expectedErrors[2]);
    				break;
    			case 4:
    				this.expectedErrors = 
						EnumSet.of( expectedErrors[0], expectedErrors[1], 
							expectedErrors[2], expectedErrors[3] );
    				break;
    			case 5:
    				this.expectedErrors = 
						EnumSet.of( expectedErrors[0], expectedErrors[1], expectedErrors[2], 
							expectedErrors[3], expectedErrors[4] );
    				break;
    			case 6:    	
    				this.expectedErrors = 
    					EnumSet.of( expectedErrors[0], expectedErrors[1], expectedErrors[2], 
    						expectedErrors[3], expectedErrors[4], expectedErrors[5] );
    				break;
    			default:
        			throw new IllegalArgumentException("Too many errors--remove duplicates!");
			}
    	}

		public char[] getPasswordChars() {
			return passwordString.toCharArray();
		}
		
		public EnumSet<BuiltInErrorKind> getExpectedErrors() {
			return EnumSet.copyOf(this.expectedErrors);
		}

		public Object getExpectedValidity() {
			return(this.expectedErrors.isEmpty() ? ResultKind.VALID : ResultKind.INVALID);
		}

		public String getPasswordString() {
			return this.passwordString;
		}
    };

	private static final HashMap<String,BuiltInErrorKind> MSG_TO_ENUM_MAP = 
		new HashMap<String, BuiltInErrorKind>(12);
	private static final ArrayList<PasswordScenario> PRIVATE_FIXTURE_DATA_LIST =
		new ArrayList<ExpectedMessages.PasswordScenario>(21); 
	private static final List<PasswordScenario> PUBLIC_FIXTURE_DATA_LIST =
		Collections.unmodifiableList(PRIVATE_FIXTURE_DATA_LIST);
		
	private static final String MAX_LENGTH_ERROR = "Passwords may be at most twelve characters long.";
	private static final String MIN_LENGTH_ERROR = "Passwords must be at least five characters long.";
	private static final String INVALID_CHAR_ERROR = "Passwords may only use lowercase letters and numeric digits.";
	private static final String LC_ALPHA_REQD_ERROR = "Passwords must include at least one lowercase letter.";
	private static final String NUMBER_REQD_ERROR = "Passwords must include at least one numeric digit.";
    private static final String NO_REPETITION_ERROR = "Passwords may not repeat any subsequence of two or more characters.";

	static {
		PRIVATE_FIXTURE_DATA_LIST.add(new PasswordScenario("FajFpJFapJFap",
			MAX_LENGTH, INVALID_CHAR, NUMBER_REQD, NO_REPETITION));

		PRIVATE_FIXTURE_DATA_LIST.add(new PasswordScenario("ajf3@1jdn",
			INVALID_CHAR));

		PRIVATE_FIXTURE_DATA_LIST.add(new PasswordScenario("aaaaaa2",
			NO_REPETITION));

		PRIVATE_FIXTURE_DATA_LIST.add(new PasswordScenario("r2r2", MIN_LENGTH,
			NO_REPETITION));

		PRIVATE_FIXTURE_DATA_LIST.add(new PasswordScenario("r2d2", MIN_LENGTH));

		PRIVATE_FIXTURE_DATA_LIST.add(new PasswordScenario("4u4i", MIN_LENGTH));

		PRIVATE_FIXTURE_DATA_LIST.add(new PasswordScenario(
			"##=@=#--#==##=@=#--#=#=#|#==", MAX_LENGTH, INVALID_CHAR,
			NUMBER_REQD, LC_ALPHA_REQD, NO_REPETITION));

		PRIVATE_FIXTURE_DATA_LIST.add(new PasswordScenario("o501j501j502o",
			MAX_LENGTH, NO_REPETITION));

		PRIVATE_FIXTURE_DATA_LIST.add(new PasswordScenario("501j501j",
			NO_REPETITION));

		PRIVATE_FIXTURE_DATA_LIST.add(new PasswordScenario("j5@1j5@1",
			INVALID_CHAR, NO_REPETITION));

		PRIVATE_FIXTURE_DATA_LIST.add(new PasswordScenario("dkafjskWdj",
			INVALID_CHAR, NUMBER_REQD));

		PRIVATE_FIXTURE_DATA_LIST.add(new PasswordScenario("aaa2bbb2"));
		PRIVATE_FIXTURE_DATA_LIST.add(new PasswordScenario("a23a2ba23a"));
		PRIVATE_FIXTURE_DATA_LIST.add(new PasswordScenario("302jfja"));
		PRIVATE_FIXTURE_DATA_LIST.add(new PasswordScenario("kfej3321"));
		PRIVATE_FIXTURE_DATA_LIST.add(new PasswordScenario("12t45"));
		PRIVATE_FIXTURE_DATA_LIST.add(new PasswordScenario("twelve123456"));
		PRIVATE_FIXTURE_DATA_LIST.add(new PasswordScenario("t1w2e3l4v5e6"));
		PRIVATE_FIXTURE_DATA_LIST.add(new PasswordScenario("1t2w3e4l5v6e"));
		PRIVATE_FIXTURE_DATA_LIST.add(new PasswordScenario("aaab4b222"));
		PRIVATE_FIXTURE_DATA_LIST.add(new PasswordScenario("501j501"));
	}
}

