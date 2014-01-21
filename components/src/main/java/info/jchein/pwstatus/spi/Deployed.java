package info.jchein.pwstatus.spi;

import info.jchein.pwstatus.IPasswordValidationService;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Class annotation intended for IPasswordConstraintSpi implementors to mark themselves as 
 * production ready and eligible for dependency injection to the {@link IPasswordValidationService}
 * service bean for any context they're declared within.
 * 
 * @author John
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Qualifier
public @interface Deployed {
}
