package info.jchein.pwstatus.spi.impl;

import info.jchein.pwstatus.spi.IPasswordConstraint;
import info.jchein.pwstatus.spi.Deployed;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
@Deployed
public class NoRepeatConstraint implements IPasswordConstraint {
	private static final String SEQ_REP_REGEX = "(...*)\\1";
	private static final Pattern SEQ_REP_PATTERN = Pattern.compile(SEQ_REP_REGEX);

	private static final List<String> SEQ_REP_ERROR =
		Collections.singletonList("Passwords may not repeat any subsequence of two or more characters.");	
			
	@Override
	public List<String> returnErrorStrings(CharSequence pwText) {
		final Matcher seqRep_matcher = SEQ_REP_PATTERN.matcher(pwText);
		final List<String> returnValueList;
		
		if( seqRep_matcher.find() ) { 
			returnValueList = SEQ_REP_ERROR;
		} else {
			returnValueList = null;
		}
		
		return returnValueList;
	}
}
