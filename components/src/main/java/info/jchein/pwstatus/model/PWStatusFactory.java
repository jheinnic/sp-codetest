package info.jchein.pwstatus.model;

import info.jchein.pwstatus.model.PasswordValidity.ResultKind;

import java.util.List;

public final class PWStatusFactory {
	public static PasswordValidity createPassedPasswordValidityTest() {
		return new PasswordValidity();
	}
	
	public static PasswordValidity createIncompletePasswordValidityTest() {
		PasswordValidity retVal = new PasswordValidity();
		retVal.setResult(ResultKind.UNKNOWN);
		
		return retVal;
	}
	
	public static PasswordValidity createIncompletePasswordValidityTest(List<String> errorsFound) {
		PasswordValidity retVal = new PasswordValidity();
		if( errorsFound == null || errorsFound.size() == 0 ) {
			retVal.setResult(ResultKind.UNKNOWN);
		} else {
			retVal.setResult(ResultKind.UNKNOWN_INVALID);
			retVal.setErrors(errorsFound);
		}
		
		return retVal;
	}
	
	public static PasswordValidity createFailedPasswordValidityTest(List<String> errorsFound) {
		if( errorsFound == null || errorsFound.size() == 0 ) {
			throw new IllegalArgumentException();
		}
		
		final PasswordValidity retVal = new PasswordValidity();
		retVal.setResult(ResultKind.INVALID);
		retVal.setErrors(errorsFound);
		
		return retVal;
	}

	private PWStatusFactory() { }
}
