package info.jchein.pwstatus.model;

import java.util.ArrayList;

public final class PasswordModelFactory {
	public static Password createPassword(final String contentString) {
		return new Password(contentString);
	}
	
	public static PasswordValidity createPasswordValidity(ArrayList<String> errorsFound) {
		PasswordValidity retVal = new PasswordValidity();
		retVal.setValid(errorsFound==null);
		retVal.setErrors(errorsFound);
		
		return retVal;
	}

	private PasswordModelFactory() { }
}
