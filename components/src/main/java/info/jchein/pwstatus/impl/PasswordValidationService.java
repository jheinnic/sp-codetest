package info.jchein.pwstatus.impl;

import info.jchein.pwstatus.IPasswordValidationService;
import info.jchein.pwstatus.model.Password;
import info.jchein.pwstatus.model.PasswordModelFactory;
import info.jchein.pwstatus.model.PasswordValidity;
import info.jchein.pwstatus.spi.IPasswordConstraintSpi;
import info.jchein.pwstatus.spi.PasswordConstraint;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidationService implements IPasswordValidationService {
    @Autowired
    @PasswordConstraint
    Set<IPasswordConstraintSpi> constraints;

	@Override
	public PasswordValidity checkValidity(Password pwString) {
		final String contentString = pwString.getContentString();
		ArrayList<String> errorsFound = new ArrayList<String>();
		for( IPasswordConstraintSpi constraint : constraints ) {
			final List<String> nextErrorList = constraint.returnErrorStrings(contentString);
			if( nextErrorList != null ) {
				if( errorsFound == null ) {
					errorsFound = new ArrayList<String>(nextErrorList);
				} else {
					errorsFound.addAll(nextErrorList);
				}
			}
		}
		
		return PasswordModelFactory.createPasswordValidity(errorsFound);
	}
    
    
}
