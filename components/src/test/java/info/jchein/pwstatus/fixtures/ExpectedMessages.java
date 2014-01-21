package info.jchein.pwstatus.fixtures;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class ExpectedMessages {
    public static final String MAX_LENGTH_ERROR = "Passwords may be at most twelve characters long.";
    public static final String MIN_LENGTH_ERROR = "Passwords must be at least five characters long.";
    public static final String CHAR_SET_ERROR = "Passwords may only use lowercase letters and numeric digits.";
    public static final String MIN_ALPHA_ERROR = "Passwords must include at least one lowercase letter.";
    public static final String MIN_DIGIT_ERROR = "Passwords must include at least one numeric digit.";
    public static final String NO_REPETITION_ERROR = "Passwords may not repeat any subsequence of two or more characters.";

    public static final String INVALID_SAMPLE_ONE_PWSTR;
    public static final char[] INVALID_SAMPLE_ONE_CHARS;
    public static final Set<String> INVALID_SAMPLE_ONE_ERRS;

    public static final String INVALID_SAMPLE_TWO_PWSTR;
    public static final char[] INVALID_SAMPLE_TWO_CHARS;
    public static final Set<String> INVALID_SAMPLE_TWO_ERRS;

    public static final String INVALID_SAMPLE_THREE_PWSTR;
    public static final char[] INVALID_SAMPLE_THREE_CHARS;
    public static final Set<String> INVALID_SAMPLE_THREE_ERRS;

    public static final String INVALID_SAMPLE_FOUR_PWSTR;
    public static final char[] INVALID_SAMPLE_FOUR_CHARS;
    public static final Set<String> INVALID_SAMPLE_FOUR_ERRS;

    public static final String INVALID_SAMPLE_FIVE_PWSTR;
    public static final char[] INVALID_SAMPLE_FIVE_CHARS;
    public static final Set<String> INVALID_SAMPLE_FIVE_ERRS;

    public static final String INVALID_SAMPLE_SIX_PWSTR;
    public static final char[] INVALID_SAMPLE_SIX_CHARS;
    public static final Set<String> INVALID_SAMPLE_SIX_ERRS;

    public static final String INVALID_SAMPLE_SEVEN_PWSTR;
    public static final char[] INVALID_SAMPLE_SEVEN_CHARS;
    public static final Set<String> INVALID_SAMPLE_SEVEN_ERRS;

    public static final String INVALID_SAMPLE_EIGHT_PWSTR;
    public static final char[] INVALID_SAMPLE_EIGHT_CHARS;
    public static final Set<String> INVALID_SAMPLE_EIGHT_ERRS;

    public static final String INVALID_SAMPLE_NINE_PWSTR;
    public static final char[] INVALID_SAMPLE_NINE_CHARS;
    public static final Set<String> INVALID_SAMPLE_NINE_ERRS;

    public static final String INVALID_SAMPLE_TEN_PWSTR;
    public static final char[] INVALID_SAMPLE_TEN_CHARS;
    public static final Set<String> INVALID_SAMPLE_TEN_ERRS;

    public static final String INVALID_SAMPLE_ELEVEN_PWSTR;
    public static final char[] INVALID_SAMPLE_ELEVEN_CHARS; 
    public static final Set<String> INVALID_SAMPLE_ELEVEN_ERRS;

    static {
    	HashSet<String> messages;
    	
    	messages = new HashSet<String>(10);
    	messages.add(MAX_LENGTH_ERROR);
    	messages.add(CHAR_SET_ERROR);
    	messages.add(MIN_DIGIT_ERROR);
		messages.add(NO_REPETITION_ERROR);
		INVALID_SAMPLE_ONE_PWSTR = "FajFpJFapJFap";
		INVALID_SAMPLE_ONE_CHARS = INVALID_SAMPLE_ONE_PWSTR.toCharArray();
		INVALID_SAMPLE_ONE_ERRS = Collections.unmodifiableSet(messages);
		
		messages = new HashSet<String>(4);
		messages.add(CHAR_SET_ERROR);
		INVALID_SAMPLE_TWO_PWSTR = "ajf3@1jdn";
		INVALID_SAMPLE_TWO_CHARS = INVALID_SAMPLE_TWO_PWSTR.toCharArray();
		INVALID_SAMPLE_TWO_ERRS = Collections.unmodifiableSet(messages);
		
		messages = new HashSet<String>(4);
		messages.add(NO_REPETITION_ERROR);
		INVALID_SAMPLE_THREE_PWSTR = "aaaaaa2";
		INVALID_SAMPLE_THREE_CHARS = INVALID_SAMPLE_THREE_PWSTR.toCharArray();
		INVALID_SAMPLE_THREE_ERRS = Collections.unmodifiableSet(messages);
		
		messages = new HashSet<String>(4);
    	messages.add(MIN_LENGTH_ERROR);
		messages.add(NO_REPETITION_ERROR);
		INVALID_SAMPLE_FOUR_PWSTR = "r2r2";
		INVALID_SAMPLE_FOUR_CHARS = INVALID_SAMPLE_FOUR_PWSTR.toCharArray();
		INVALID_SAMPLE_FOUR_ERRS = Collections.unmodifiableSet(messages);
		
		messages = new HashSet<String>(4);
    	messages.add(MIN_LENGTH_ERROR);
		INVALID_SAMPLE_FIVE_PWSTR = "r2d2";
		INVALID_SAMPLE_FIVE_CHARS = INVALID_SAMPLE_FIVE_PWSTR.toCharArray();
		INVALID_SAMPLE_FIVE_ERRS = Collections.unmodifiableSet(messages);
		
		messages = new HashSet<String>(4);
    	messages.add(MIN_LENGTH_ERROR);
		INVALID_SAMPLE_SIX_PWSTR = "4u4i";
		INVALID_SAMPLE_SIX_CHARS = INVALID_SAMPLE_SIX_PWSTR.toCharArray();
		INVALID_SAMPLE_SIX_ERRS = Collections.unmodifiableSet(messages);		
		
		messages = new HashSet<String>(4);
    	messages.add(MAX_LENGTH_ERROR);
		messages.add(CHAR_SET_ERROR);
    	messages.add(MIN_DIGIT_ERROR);
    	messages.add(MIN_ALPHA_ERROR);
		messages.add(NO_REPETITION_ERROR);
		INVALID_SAMPLE_SEVEN_PWSTR = "##=@=#--#==##=@=#--#=#=#|#==";
		INVALID_SAMPLE_SEVEN_CHARS = INVALID_SAMPLE_SEVEN_PWSTR.toCharArray();
		INVALID_SAMPLE_SEVEN_ERRS = Collections.unmodifiableSet(messages);		
		
		messages = new HashSet<String>(4);
    	messages.add(MAX_LENGTH_ERROR);
		messages.add(NO_REPETITION_ERROR);
		INVALID_SAMPLE_EIGHT_PWSTR = "o501j501j502o";
		INVALID_SAMPLE_EIGHT_CHARS = INVALID_SAMPLE_EIGHT_PWSTR.toCharArray();
		INVALID_SAMPLE_EIGHT_ERRS = Collections.unmodifiableSet(messages);
		
		messages = new HashSet<String>(4);
		messages.add(NO_REPETITION_ERROR);
		INVALID_SAMPLE_NINE_PWSTR = "501j501j";
		INVALID_SAMPLE_NINE_CHARS = INVALID_SAMPLE_NINE_PWSTR.toCharArray();
		INVALID_SAMPLE_NINE_ERRS = Collections.unmodifiableSet(messages);
		
		messages = new HashSet<String>(4);
		messages.add(CHAR_SET_ERROR);
		messages.add(NO_REPETITION_ERROR);
		INVALID_SAMPLE_TEN_PWSTR = "j5@1j5@1";
		INVALID_SAMPLE_TEN_CHARS = INVALID_SAMPLE_TEN_PWSTR.toCharArray();
		INVALID_SAMPLE_TEN_ERRS = Collections.unmodifiableSet(messages);
		
		messages = new HashSet<String>(4);
 		messages.add(CHAR_SET_ERROR);
    	messages.add(MIN_DIGIT_ERROR);
    	INVALID_SAMPLE_ELEVEN_PWSTR = "dkafjskWdj";
		INVALID_SAMPLE_ELEVEN_CHARS = INVALID_SAMPLE_ELEVEN_PWSTR.toCharArray();
    	INVALID_SAMPLE_ELEVEN_ERRS = Collections.unmodifiableSet(messages);
    }
    
    public static final String VALID_SAMPLE_ONE_PWSTR = "aaa2bbb2";
	public static final String VALID_SAMPLE_TWO_PWSTR = "a23a2ba23a";
	public static final String VALID_SAMPLE_THREE_PWSTR = "302jfja";
	public static final String VALID_SAMPLE_FOUR_PWSTR = "kfej3321";
	public static final String VALID_SAMPLE_FIVE_PWSTR = "12t45";
	public static final String VALID_SAMPLE_SIX_PWSTR = "twelve123456";
	public static final String VALID_SAMPLE_SEVEN_PWSTR = "t1w2e3l4v5e6";
	public static final String VALID_SAMPLE_EIGHT_PWSTR = "1t2w3e4l5v6e";
	public static final String VALID_SAMPLE_NINE_PWSTR = "aaab4b222";
	public static final String VALID_SAMPLE_TEN_PWSTR = "501j501";
    
    public static final char[] VALID_SAMPLE_ONE_CHARS = 
    	VALID_SAMPLE_ONE_PWSTR.toCharArray();
	public static final char[] VALID_SAMPLE_TWO_CHARS = 
		VALID_SAMPLE_TWO_PWSTR.toCharArray();
	public static final char[] VALID_SAMPLE_THREE_CHARS = 
		VALID_SAMPLE_THREE_PWSTR.toCharArray();
	public static final char[] VALID_SAMPLE_FOUR_CHARS = 
		VALID_SAMPLE_FOUR_PWSTR.toCharArray();
	public static final char[] VALID_SAMPLE_FIVE_CHARS = 
		VALID_SAMPLE_FIVE_PWSTR.toCharArray();
	public static final char[] VALID_SAMPLE_SIX_CHARS = 
		VALID_SAMPLE_SIX_PWSTR.toCharArray();
	public static final char[] VALID_SAMPLE_SEVEN_CHARS = 
		VALID_SAMPLE_SEVEN_PWSTR.toCharArray();
	public static final char[] VALID_SAMPLE_EIGHT_CHARS = 
		VALID_SAMPLE_EIGHT_PWSTR.toCharArray();
	public static final char[] VALID_SAMPLE_NINE_CHARS = 
		VALID_SAMPLE_NINE_PWSTR.toCharArray();
	public static final char[] VALID_SAMPLE_TEN_CHARS = 
		VALID_SAMPLE_TEN_PWSTR.toCharArray();
}

