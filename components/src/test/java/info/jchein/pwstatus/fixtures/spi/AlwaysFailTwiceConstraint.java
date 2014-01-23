package info.jchein.pwstatus.fixtures.spi;

import info.jchein.pwstatus.spi.IPasswordConstraint;
import info.jchein.pwstatus.spi.Deployed;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
@Deployed
public class AlwaysFailTwiceConstraint implements IPasswordConstraint {

	@Override
	public List<String> returnErrorStrings(CharSequence pwText) {
		final ArrayList<String> retVal = new ArrayList<String>(2);
		retVal.add("This is very bad");
		retVal.add("This is very very bad");
		return retVal;
	}

}
