package info.jchein.pwstatus.fixtures.spi;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import info.jchein.pwstatus.spi.IPasswordConstraintSpi;
import info.jchein.pwstatus.spi.PasswordConstraint;

@Component
@PasswordConstraint
public class NeverFailConstraint implements IPasswordConstraintSpi {

	@Override
	public List<String> returnErrorStrings(String pwText) {
		return null;
	}

}
