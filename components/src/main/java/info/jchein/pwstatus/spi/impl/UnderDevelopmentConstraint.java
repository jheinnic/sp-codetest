package info.jchein.pwstatus.spi.impl;

import info.jchein.pwstatus.spi.IPasswordConstraintSpi;

import java.util.List;

/**
 * A nonsense implementation of the password constraint SPI to illustrate 
 * how omitting the {@link @Deployed} annotation removes a constraint class 
 * from active consideration.
 */
public class UnderDevelopmentConstraint implements IPasswordConstraintSpi {

	@Override
	@SuppressWarnings("null")
	/**
	 * If you evaluate this, it will throw a NPE.  
	 * 
	 * TODO: Eventually we gotta fix that...  Do not Deploy this until then.
	 */
	public List<String> returnErrorStrings(CharSequence pwText) {
		final List<String> returnValueList = null;
		final String[] messageSource = { "Error1", "Error2" };
		for( final String nextError : messageSource ) {
			returnValueList.add(nextError);
		}
		
		return returnValueList;
	}

}
