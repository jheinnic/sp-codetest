package info.jchein.pwstatus.fixtures.spi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import info.jchein.pwstatus.spi.IPasswordConstraintSpi;
import info.jchein.pwstatus.spi.PasswordConstraint;

@Component
@PasswordConstraint
public class AlwaysFailTwiceConstraint implements IPasswordConstraintSpi {

	@Override
	public List<String> returnErrorStrings(String pwText) {
		final ArrayList<String> retVal = new ArrayList<String>(2);
		retVal.add("This is very bad");
		retVal.add("This is very very bad");
		return retVal;
	}

}
