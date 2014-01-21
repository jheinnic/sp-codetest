package info.jchein.pwstatus.fixtures.spi;

import info.jchein.pwstatus.spi.IPasswordConstraintSpi;
import info.jchein.pwstatus.spi.Deployed;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
@Deployed
public class NeverFailConstraint implements IPasswordConstraintSpi {

	@Override
	public List<String> returnErrorStrings(CharSequence pwText) {
		return null;
	}

}
