package info.jchein.pwstatus.fixtures.spi;

import info.jchein.pwstatus.spi.IPasswordConstraint;
import info.jchein.pwstatus.spi.Deployed;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
@Deployed
public class FailIfOddConstraint implements IPasswordConstraint {

	@Override
	public List<String> returnErrorStrings(CharSequence pwText) {
		if( (pwText.length() % 2) == 1 ) {
			return Collections.singletonList("Password is just too odd...");
		} else {
			return null;
		}
	}

}
