package info.jchein.pwstatus.spi;

import info.jchein.pwstatus.IPasswordValidationService;

import java.util.Collection;

/**
 * Service provider plugin interface for contributors of validation constraint logic to 
 * {@link IPasswordValidationService} beans.
 * 
 * In addition to implementing this interface, an implementor must add the {@link @Deployed}
 * class annotation to indicate their implementation is a candidate for evaluation at runtime.
 * 
 * @author John
 *
 */
public interface IPasswordConstraint {
    /**
     * Workhorse method for password validation.
     * 
     * Evaluate the constraints an implementation of this interface encapsulates and populate a 
     * List for output with a string for each unique rule violation found.  If not rules are
     * violated, either an empty list or a null value may be returned instead.
     * 
     * @param pwText A CharSequence wrapping the password being tested that has a non-functional
     * toString() method to prevent accidental representation by an immutable object type that 
     * cannot be pro-actively zeroed out before the operation completes.
     * @see ProtectedCharSequence
     */
	public Collection<String> returnErrorStrings(CharSequence pwText);
}
