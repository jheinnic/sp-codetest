package info.jchein.pwstatus.impl;

import info.jchein.lang.AssumptionViolatedException;
import info.jchein.pwstatus.IPasswordValidationService;
import info.jchein.pwstatus.model.PWStatusFactory;
import info.jchein.pwstatus.model.PasswordValidity;
import info.jchein.pwstatus.spi.Deployed;
import info.jchein.pwstatus.spi.IPasswordConstraint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidationService implements IPasswordValidationService {
	private static final Logger LOG = LoggerFactory.getLogger(PasswordValidationService.class);
	
    @Autowired
    @Deployed
    Set<IPasswordConstraint> constraints;

	@Override
	public PasswordValidity checkValidity(char[] pwContent) {
		if (pwContent == null) {
			throw new IllegalArgumentException();
		}
		
		boolean allTestsEvaluated = true;
		final PasswordValidity resultMessage;
		ProtectedCharSequence protPwContent = null;
		
		// Allocate for a reasonable guess at the maximum number of errors to avoid array re-sizing
		// in the best case scenario.  It's just a default size, the array will dynamically grow if 
		// necessary and unused space is truncated away at the end, so no harm being off either way.
		final ArrayList<String> errorsFound = 
			new ArrayList<String>(2 * constraints.size());
		
		try {
			// Wrap the character array and use a try block to make sure we clean up afterwards, even 
			// in the event of the unexpected.
			protPwContent = ProtectedCharSequence.wrap(pwContent);
			
			for (final IPasswordConstraint constraint : constraints) {
				try {
					final Collection<String> nextErrorList = 
						constraint.returnErrorStrings(protPwContent);
					if (nextErrorList != null) {
						errorsFound.addAll(nextErrorList);
					}
				} catch (Throwable e) {
					allTestsEvaluated = false;
					LOG.warn("Unexpected exception thrown by " + constraint.getClass().getName());
				}
			}
			
			if (errorsFound.size() > 0) {
				if (allTestsEvaluated) {
					resultMessage = PWStatusFactory.createFailedPasswordValidityTest(errorsFound);
				} else {
					resultMessage = PWStatusFactory.createIncompletePasswordValidityTest(errorsFound);
				}
			} else if (allTestsEvaluated) {
				resultMessage = PWStatusFactory.createPassedPasswordValidityTest();
			} else {
				resultMessage = PWStatusFactory.createIncompletePasswordValidityTest();
			}
		} catch (Throwable e) { 
			LOG.error("Unexpected exception thrown within base implementation logic!!", e);
			throw new AssumptionViolatedException("Unhandlable internal service error", e);
		} finally {
			protPwContent.destroy();
		}
		
		return resultMessage;
	}
}
