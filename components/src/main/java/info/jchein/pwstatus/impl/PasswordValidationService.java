package info.jchein.pwstatus.impl;

import info.jchein.lang.AssumptionViolatedException;
import info.jchein.pwstatus.IPasswordValidationService;
import info.jchein.pwstatus.model.PWStatusFactory;
import info.jchein.pwstatus.model.PasswordValidity;
import info.jchein.pwstatus.spi.IPasswordConstraintSpi;
import info.jchein.pwstatus.spi.Deployed;

import java.util.ArrayList;
import java.util.List;
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
    Set<IPasswordConstraintSpi> constraints;

	@Override
	public PasswordValidity checkValidity(char[] pwContent) {
		if (pwContent == null) {
			throw new IllegalArgumentException();
		}
		
		boolean allTestsEvaluated = true;
		final PasswordValidity resultMessage;
		
		// Allocate for the maximum number of errors in the built-in case to avoid array re-sizing
		// and leave a little room for worst case scenario messages from other plugins.  It's just a
		// default size, no harm in being too few or too many.
		final ArrayList<String> errorsFound = new ArrayList<String>(8);
		
		// Wrap the character array and use a try block to make sure we clean up afterwards, even 
		// in the event of the unexpected.
		final ProtectedCharSequence protPwContent = ProtectedCharSequence.wrap(pwContent);
		try {
			for (final IPasswordConstraintSpi constraint : constraints) {
				try {
					final List<String> nextErrorList = constraint.returnErrorStrings(protPwContent);
					if (nextErrorList != null) {
						errorsFound.addAll(nextErrorList);
					}
				} catch (Throwable e) {
					allTestsEvaluated = false;
					LOG.warn("Unexpected exception thrown by %s", constraint.getClass().getName());
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
