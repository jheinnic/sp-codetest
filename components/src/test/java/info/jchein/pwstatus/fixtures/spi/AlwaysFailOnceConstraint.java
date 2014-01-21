package info.jchein.pwstatus.fixtures.spi;

import info.jchein.pwstatus.spi.IPasswordConstraintSpi;
import info.jchein.pwstatus.spi.PasswordConstraint;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
@PasswordConstraint
public class AlwaysFailOnceConstraint implements IPasswordConstraintSpi {
	@Override
	public List<String> returnErrorStrings(CharSequence pwText) {
		return Collections.singletonList("This is bad");
	}

}
