package info.jchein.pwstatus.fixtures;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class ExpectedMessages {
    public static final String MAX_LENGTH_ERROR = "Passwords may be at most twelve characters long.";
    public static final String MIN_LENGTH_ERROR = "Passwords must be at least five characters long.";
    public static final String CHAR_SET_ERROR = "Passwords may only use lowercase letters and numeric digits.";
    public static final String MIN_ALPHA_ERROR = "Passwords must include at least one lowercase letter.";
    public static final String MIN_DIGIT_ERROR = "Passwords must include at least one numeric digit.";
    public static final String NO_REPETITION_ERROR = "Passwords may not repeat any subsequence of two or more characters";

    public static final Map<String, Set<String>> SAMPLE_INVALID_TESTS;
    public static final Set<String> SAMPLE_VALID_TESTS;

    static {
    	HashSet<String> messages;
    	HashMap<String, Set<String>> invalidTests = new HashMap<String, Set<String>>();
    	HashSet<String> validTests = new HashSet<String>();

    	messages = new HashSet<String>(10);
    	messages.add(MAX_LENGTH_ERROR);
    	messages.add(CHAR_SET_ERROR);
    	messages.add(MIN_DIGIT_ERROR);
		messages.add(NO_REPETITION_ERROR);
		invalidTests.put("FajFpJFapJFAp", Collections.unmodifiableSet(messages));
		
		messages.clear();
		messages.add(CHAR_SET_ERROR);
		invalidTests.put("ajf3@1jdn", Collections.unmodifiableSet(messages));
		
		messages.clear();
		messages.add(NO_REPETITION_ERROR);
		invalidTests.put("aaaaaa2", Collections.unmodifiableSet(messages));
		
		messages.clear();
    	messages.add(MIN_LENGTH_ERROR);
		messages.add(NO_REPETITION_ERROR);
		invalidTests.put("r2r2", Collections.unmodifiableSet(messages));
		
		messages.clear();
    	messages.add(MIN_LENGTH_ERROR);
		invalidTests.put("r2d2", Collections.unmodifiableSet(messages));
		
		messages.clear();
    	messages.add(MIN_LENGTH_ERROR);
		invalidTests.put("4u4i", Collections.unmodifiableSet(messages));		
		
		messages.clear();
    	messages.add(MAX_LENGTH_ERROR);
		messages.add(CHAR_SET_ERROR);
    	messages.add(MIN_DIGIT_ERROR);
    	messages.add(MIN_ALPHA_ERROR);
		messages.add(NO_REPETITION_ERROR);
		invalidTests.put("##=@=#--#==##=@=#--#=#=#|#==", Collections.unmodifiableSet(messages));		
		
		messages.clear();
    	messages.add(MAX_LENGTH_ERROR);
		messages.add(NO_REPETITION_ERROR);
		invalidTests.put("o501j501j502o", Collections.unmodifiableSet(messages));
		
		messages.clear();
		messages.add(NO_REPETITION_ERROR);
		invalidTests.put("501j501j", Collections.unmodifiableSet(messages));
		
		messages.clear();
		messages.add(CHAR_SET_ERROR);
		messages.add(NO_REPETITION_ERROR);
		invalidTests.put("j5@1j5@1", Collections.unmodifiableSet(messages));
		
		messages.clear();
 		messages.add(CHAR_SET_ERROR);
    	messages.add(MIN_DIGIT_ERROR);
    	invalidTests.put("dkafjskWdj", Collections.unmodifiableSet(messages));

		// TODO: One more invalid
		
		validTests.add("aaa2bbb2");
		validTests.add("a23a2ba23a");
		validTests.add("302jfja");
		validTests.add("kfej3321");
		validTests.add("12t45");
		validTests.add("twelve123456");
		validTests.add("t1w2e3l4v5e6");
		validTests.add("1t2w3e4l5v6e");
		validTests.add("aaab4b222");
		validTests.add("501j501");
		
		SAMPLE_INVALID_TESTS = Collections.unmodifiableMap(invalidTests);
		SAMPLE_VALID_TESTS = Collections.unmodifiableSet(validTests);
    }
}

