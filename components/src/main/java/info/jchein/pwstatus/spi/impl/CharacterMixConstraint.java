package info.jchein.pwstatus.spi.impl;

import info.jchein.pwstatus.spi.IPasswordConstraint;
import info.jchein.pwstatus.spi.Deployed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
@Deployed
public class CharacterMixConstraint implements IPasswordConstraint {
	private static final String CHARSET_REGEX = "[^a-z0-9]";
	private static final Pattern CHARSET_PATTERN = Pattern.compile(CHARSET_REGEX);

	private static final String MIN_DIGITS_REGEX = "\\d";
	private static final Pattern MIN_DIGITS_PATTERN = Pattern.compile(MIN_DIGITS_REGEX);

	private static final String MIN_ALPHA_REGEX = "\\p{Lower}";
	private static final Pattern MIN_ALPHA_PATTERN = Pattern.compile(MIN_ALPHA_REGEX);

	// TODO: Localization?
	private static final List<String> CHARSET_ERROR =
		Collections.singletonList("Passwords may only use lowercase letters and numeric digits.");
	private static final List<String> MIN_DIGITS_ERROR =
		Collections.singletonList("Passwords must include at least one numeric digit.");
	private static final List<String> MIN_ALPHA_ERROR =
		Collections.singletonList("Passwords must include at least one lowercase letter.");
	
	private static final List<String> CHARSET_MIN_DIGITS_ERRORS;
	private static final List<String> CHARSET_MIN_ALPHA_ERRORS;
	private static final List<String> MIN_DIGITS_MIN_ALPHA_ERRORS;
	private static final List<String> ALL_ERRORS;
	
	static {
		List<String> temp;
		
		temp = new ArrayList<String>(2);
		temp.addAll(CHARSET_ERROR);
		temp.addAll(MIN_DIGITS_ERROR);
		CHARSET_MIN_DIGITS_ERRORS = Collections.unmodifiableList(temp);
				
		temp = new ArrayList<String>(2);
		temp.addAll(CHARSET_ERROR);
		temp.addAll(MIN_ALPHA_ERROR);
		CHARSET_MIN_ALPHA_ERRORS = Collections.unmodifiableList(temp);
		
		temp = new ArrayList<String>(2);
		temp.addAll(MIN_DIGITS_ERROR);
		temp.addAll(MIN_ALPHA_ERROR);
		MIN_DIGITS_MIN_ALPHA_ERRORS = Collections.unmodifiableList(temp);
		
		temp = new ArrayList<String>(3);
		temp.addAll(CHARSET_ERROR);
		temp.addAll(MIN_DIGITS_ERROR);
		temp.addAll(MIN_ALPHA_ERROR);
		ALL_ERRORS = Collections.unmodifiableList(temp);
	}
	
			
	@Override
	public List<String> returnErrorStrings(CharSequence pwText) {
		final Matcher charSet_matcher = CHARSET_PATTERN.matcher(pwText);
		final Matcher minDigit_matcher = MIN_DIGITS_PATTERN.matcher(pwText);
		final Matcher minAlpha_matcher = MIN_ALPHA_PATTERN.matcher(pwText);
		final List<String> returnValueList;
		
		if( charSet_matcher.find() == false) { 
			if( minDigit_matcher.find() == true) {
				if( minAlpha_matcher.find() == true) {
					// No errors!
					returnValueList = null;
				} else {
					// One error--MIN_ALPHA
					returnValueList = MIN_ALPHA_ERROR;
				}
			} else {
				if( minAlpha_matcher.find() == true) {
					// One error--MIN_DIGITS
					returnValueList = MIN_DIGITS_ERROR;
				} else {
					// Two errors--MIN_DIGITS and MIN_ALPHA
					returnValueList = MIN_DIGITS_MIN_ALPHA_ERRORS;
				}
			}
		} else {
			if( minDigit_matcher.find() == true) {
				if( minAlpha_matcher.find() == true) {
					// One error--MIN_CHARSET!
					returnValueList = CHARSET_ERROR;
				} else {
					// Two errors--MIN_CHARSET and MIN_ALPHA
					returnValueList = CHARSET_MIN_ALPHA_ERRORS;
				}
			} else {
				if( minAlpha_matcher.find() == true) {
					// Two errors--MIN_CHARSET and MIN_DIGITS
					returnValueList = CHARSET_MIN_DIGITS_ERRORS;
				} else {
					// All three errors
					returnValueList = ALL_ERRORS;
				}
			}
		}
		
		return returnValueList;
	}
}
