package info.jchein.pwstatus.fixtures.spi;

import info.jchein.pwstatus.spi.IPasswordConstraint;
import info.jchein.pwstatus.spi.Deployed;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
@Deployed
public class AlwaysFailOnceConstraint implements IPasswordConstraint {
	@Override
	public List<String> returnErrorStrings(CharSequence pwText) {
		return Collections.singletonList("This is bad");
	}

}
