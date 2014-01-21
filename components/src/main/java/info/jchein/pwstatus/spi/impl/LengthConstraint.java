package info.jchein.pwstatus.spi.impl;

import info.jchein.pwstatus.spi.IPasswordConstraintSpi;
import info.jchein.pwstatus.spi.Deployed;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
@Deployed
public class LengthConstraint implements IPasswordConstraintSpi {
	private static final int MIN_LEN = 5;
	private static final int MAX_LEN = 12;
	
	private static final List<String> MIN_LEN_ERROR =
		Collections.singletonList("Passwords must be at least five characters long.");
	private static final List<String> MAX_LEN_ERROR =
		Collections.singletonList("Passwords may be at most twelve characters long.");
				
	@Override
	public List<String> returnErrorStrings(CharSequence pwText) {
		final int pwLen = pwText.length();
		final List<String> returnValueList;
		
		if( pwLen < MIN_LEN ) {
			returnValueList = MIN_LEN_ERROR;
		} else if( pwLen > MAX_LEN ) {
			returnValueList = MAX_LEN_ERROR;
		} else {
			returnValueList = null;
		}
		
		return returnValueList;
	}
}
