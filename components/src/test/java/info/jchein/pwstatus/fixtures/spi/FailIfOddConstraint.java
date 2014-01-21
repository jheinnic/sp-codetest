package info.jchein.pwstatus.fixtures.spi;

import info.jchein.pwstatus.spi.IPasswordConstraintSpi;
import info.jchein.pwstatus.spi.PasswordConstraint;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
@PasswordConstraint
public class FailIfOddConstraint implements IPasswordConstraintSpi {

	@Override
	public List<String> returnErrorStrings(CharSequence pwText) {
		if( (pwText.length() % 2) == 1 ) {
			return Collections.singletonList("Password is just too odd...");
		} else {
			return null;
		}
	}

}
