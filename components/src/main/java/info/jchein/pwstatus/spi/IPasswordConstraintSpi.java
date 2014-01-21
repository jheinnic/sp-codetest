package info.jchein.pwstatus.spi;

import info.jchein.pwstatus.impl.ProtectedCharSequence;

import java.util.List;


public interface IPasswordConstraintSpi {
    /**
     * @param pwText A CharSequence wrapping the password being tested that has a non-functional
     * toString() method to prevent accidental representation by an immutable object type that 
     * cannot be pro-actively zeroed out before the operation completes.
     * @see ProtectedCharSequence
     */
	List<String> returnErrorStrings(CharSequence pwText);
}
