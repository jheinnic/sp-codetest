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
	private static final long serialVersionUID = -103316631552947193L;
	
	private boolean valid;
    private ArrayList<String> errors;
    
    PasswordValidity() {
    	this.valid = true;
    	this.errors = new ArrayList<String>(0);
    }
    
    @JsonGetter
	public boolean isValid() {
		return valid;
	}
    
    @JsonSetter
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	@JsonGetter
	public List<String> getErrors() {
		if( errors != null ) {
			return new ArrayList<String>(errors);
		} else {
			return Collections.<String> emptyList();
		}
	}
	
	@JsonSetter
	public void setErrors(List<String> errors) {
		this.errors = new ArrayList<String>(errors);
	}
}
