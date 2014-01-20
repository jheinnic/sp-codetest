package info.jchein.pwstatus.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Value class encapsulating a string used as a password
 * 
 * @author John
 */
@JsonDeserialize( converter=StringToPasswordConverter.class )
@JsonSerialize( using=PasswordSerializer.class )
public class Password {
    private final String contentString;
    
    public Password( final String contentString ) {
    	this.contentString = contentString;
    }

    // @JsonGetter
	public String getContentString() {
		return contentString;
	}
}
