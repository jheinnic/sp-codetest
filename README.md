Function
========
    The password validation component provides a facility for 
evaluating whether a candidate password conforms to a customizable
set of acceptance criteria.  Inputs that violate any criteria are
flagged and a summary list of rule violations is returned.

    The implementation takes steps to discourage holding a user's 
password in memory as a immutable String, making it possible to remove
the input from memory before returning rather than needing to wait on
garbage collection followed by new allocation to overwrite its
content, leaving sensitive input far less vulnerable to memory mapping
attacks.

Requirements
============

Spring 4.0.0
------------

    The password validation component depends on Spring Core and Context
packages from the 4.0.0 release.  Artifacts are provided to enable reuse
either through declarative XML-file configuration or imperative-style Java
@Configuration beans.

Jackson 2.1
-----------

    To simplify its reuse as a RESTful service,  
info.jchein.pwval.model.PWVerifyResult, a data entity bean that
encapsulates the resultof a call into the validator's main API hook, is
annotated for JSON serialization/deserialization with Jackson 2.1.

Maven
-----

    If you use Maven for a build system, the password validation component's
release depot is blahfakeblah.artifactory.com.  The following project dependency will add the Password Validation Service's implementation to your project's classpath:

    <dependency>
        <groupId>info.jchein.stormpath.codetest</groupId>
        <artifactId>components</artifactId>
        <version>0.1.0</version>
    </dependency>


Usage
=====

	The Password Validation Service can be instantiated within an application context defined either by an XML file or a Spring @Configuration annotated Java class.  Both variations lean on component scanning to locate and autowire the
Password Validator Service Bean with the collection of all available Password Constraint Provider Beans.

	In the examples that follow, the Service Bean is located in package info.jchein.pwstatus.impl and the constraint provider beans are located in info.jchein.pwstatus.spi.impl.  This separation means that consumers can either provide additional packages with providers with extent the built-in constraint set or replace the org.jchein.pwstatus.spi.impl package any number of alternate package names to replace the built-in constraints with a different set.

@Configuration Method
---------------------

	The configuration class uses a @ComponentScan annotation to help Spring discover and wire the password validation service component:

package info.jchein.pwstatus.fixtures.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ 
    "info.jchein.pwstatus.spi.impl", "info.jchein.pwstatus.impl"
})
public class PasswordServiceConfiguration { }


Context File Method
-------------------

	The context file also uses component scanning, but makes the equivalent declaration within an XML context file rather than a Java Class Annotation:

<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-2.5.xsd"
    default-lazy-init="true" >
    
    <context:component-scan base-package="info.jchein.pwstatus.spi.impl info.jchein.pwstatus.impl" />
</beans>


Invoking
--------
    The code snippet below shows how the PasswordValidationService can be used to define a routine that returns without event if called with a compliant password, but throws an Exception built from a list of error messages if called with a non-compliant password.
    
public class MyPasswordChecker {

    @Autowired
    private IPasswordValidationService validationService;

    public void checkPassword(char[] pwChars) {
        final PasswordValidity result = 
            validationService.checkValidity(pwChars);
        if (result.getResult() != ResultKind.VALID) {
            throw new BadPasswordException(result.getErrors());
        }
    }
}


Extending
---------
    The final code snippet below illustrates a custom password constraint that enforces a password length between 5 and 12 characters.  The key observations to make are the location of the interface the constraint developer implements and the two class annotations required for the implementation to be eligible for auto-wiring through context scanning.

    Two notable details related to return values and exceptions are worth highlighting by way of deliberate discussion here.

    Regarding return values, the Password Validation Service will interpret an empty Collection return value the same way as a null return value.  In both cases, the return value expresses that no rule encapsulated by the constraint provider class just invoked were violated.

    Regarding exceptions, if any registered constraint providers throw an unchecked exception when called, the service provider still calls the remaining providers and collects error messages, and the getResult() method of its return model changes.  If none of the constraint providers that returned without an Exception generate error messages, the return value changes from VALID to UNKNOWN.  Otherwise, the return value changes from INVALID to UNKNOWN_INVALID.


package info.jchein.pwstatus.spi.impl;

import info.jchein.pwstatus.spi.IPasswordConstraintSpi;
import info.jchein.pwstatus.spi.Deployed;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;
import info.jchein.pwstatus.spi.Deployed;
import info.jchein.pwstatus.spi.IPasswordConstraint;

@Component
@Deployed
public class LengthConstraint implements IPasswordConstraint {
    private static final int MIN_LEN = 5;
    private static final int MAX_LEN = 12;
    
    private static final List<String> MIN_LEN_ERROR =
        Collections.singletonList(
            "Passwords must be at least five characters long.");

    private static final List<String> MAX_LEN_ERROR =
        Collections.singletonList(
            "Passwords may be at most twelve characters long.");
                
    @Override
    public List<String> returnErrorStrings(CharSequence pwText) {
        final int pwLen = pwText.length();
        
        if( pwLen < MIN_LEN ) {
            return MIN_LEN_ERROR;
        } else if( pwLen > MAX_LEN ) {
            return MAX_LEN_ERROR;
        }
        
        // Null is equivalent to empty collection as a return value here.
        return null;
    }
}

