package info.jchein.pwstatus.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Wire entity for validity return
 * 
 * @author John
 */
public class PasswordValidity implements Serializable {
    /**
	 */
	private static final long serialVersionUID = 2351663131446046724L;
	
	public enum ResultKind {
		/** 
		 * Result code indicating that all password constraint tests were checked and passed.
		 */
		VALID,
		
		/**
		 * Result code indicating that some constraint tests failed to evaluate, but those that
		 * did evaluate returned no errors.  The tests that did not evaluate may or may not have
		 * been intended to find errors.
		 */
		UNKNOWN,
		
		/**
		 * Result code indicating that some constraint tests failed to evaluate, but at least one
		 * of those that did evaluate returned one or more errors.  The tests that did not evaluate
		 * might have found additional errors if they ran as intended.
		 */
		UNKNOWN_INVALID,
		
		/**
		 * Result code indicating that all password constraint tests were checked and one or more tests
		 * produced one or more errors.
		 */
		INVALID;
	}
	
	private ResultKind result;
	
    private List<String> errors;
    
    /**
     * Entity bean constraints require a public no-argument constructor.  The object returned by this 
     * constructor is in the state of a valid result.  Use a factory method to access other valid 
     * permutations of state.
     */
    public PasswordValidity() {
    	this.result = ResultKind.VALID;
    	this.errors = EMPTY_LIST;
    }
    
    @JsonGetter
	public ResultKind getResult() {
		return result;
	}
    
    @JsonSetter
	void setResult(ResultKind result) {
		this.result = result;
	}
	
    /**
     * Immutable empty list singleton--no need to allocate more than one 
     */
    private static final List<String> EMPTY_LIST = Collections.<String> emptyList();
    
    /**
     * Returns an unmodifiable list of error message strings.  This should only be set when 
     * getResult() returns ResultKind.UNKNOWN_INVALID or ResultKind.INVALID.
     * 
     * @return
     */
	@JsonGetter
	public List<String> getErrors() {
		return errors;
	}
	
	@JsonSetter
	void setErrors(List<String> errors) {
		if( errors == null || errors.size() == 0 ) {
			this.errors = EMPTY_LIST;
		} else {
			this.errors = 
				Collections.unmodifiableList( 
					new ArrayList<String>(errors)
				);
		}
	}
}
