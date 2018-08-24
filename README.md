# Description

The password validation component provides a facility for evaluating whether a candidate password conforms to a customizable set of acceptance criteria.  Inputs that violate any criteria are flagged and a summary list of rule violations is returned.

The implementation takes steps to discourage holding a user's password in memory as a immutable String, making it possible to remove the input from memory before returning rather than needing to wait on garbage collection followed by new allocation to overwrite its content, leaving sensitive input far less vulnerable to memory mapping attacks.

# Requirements

### Spring 4.0.0

The password validation component depends on Spring Core and Context packages from the 4.0.0 release.  Artifacts are provided to enable reuse either through declarative XML-file configuration or imperative-style Java `@Configuration` annotated service beans.

### Jackson 2.1

To ease its reuse as a RESTful service, the entity bean modeling the return value from the Password Validator Service's core routine is proactively annotated for serialization by Jackson 2.1.  The model entity's bean name is `info.jchein.pwstatus.model.PasswordValidity`.

### Maven

The Password Validation Service was built and deployed using Maven 3.0.4.  If you also use Maven for a build system, the password validation component's release depot is `blahfakeblah.artifactory.com` (placeholder for a real depo if I had one to deploy into).  The following project dependency will add the Password Validation Service's implementation to your project's classpath:

```xml
    <dependency>
        <groupId>info.jchein.stormpath.codetest</groupId>
        <artifactId>components</artifactId>
        <version>0.1.0</version>
    </dependency>
```

# Usage

The Password Validation Service can be instantiated within an application context defined either by an XML file or a Spring `@Configuration` annotated Java class.  Both variations lean on component scanning to locate and autowire the Password Validator Service Bean with the collection of all available Password Constraint Provider Beans.  

The Service Bean is namespaced in package `info.jchein.pwstatus.impl`.  The examples that follow also use the built-in Constraint Provider Beans, which are namespaced in `info.jchein.pwstatus.spi.impl`.  Using separate namespaces makes it possible to replace the built-in constraint set by omitting it from component scan without compromising the flexibility of adding new package namespaces for adding java packages with additional custom constraints.

## Spring Context Configuration Methods

### @Configuration Method

The configuration class uses a `@ComponentScan` annotation to help Spring discover and wire the password validation service component:

```java
package info.jchein.pwstatus.fixtures.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({ 
    "info.jchein.pwstatus.spi.impl", "info.jchein.pwstatus.impl"
})
public class PasswordServiceConfiguration { }
```

### Context File Method
The context file also uses component scanning, but makes the equivalent declaration within an XML context file rather than a Java Class Annotation:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.5.xsd
        http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-2.5.xsd"
    default-lazy-init="true" >

    <context:component-scan base-package="info.jchein.pwstatus.spi.impl info.jchein.pwstatus.impl" />
</beans>
```

## Coding Examples

### Service Consumer
The code snippet below shows how `info.jchein.pwstatus.impl.PasswordValidationService` could be used to implement a method that returns if and only if called with a compliant password, and otherwise throws an Exception containing a list of error messages describing the problems that were discovered by the constraint providers.
    
```java
public class MyPasswordChecker {
    @Autowired
    private IPasswordValidationService validationService;

    public void checkPassword(final char[] pwChars) {
        final PasswordValidity result = 
            validationService.checkValidity(pwChars);
        if (result.getResult() != ResultKind.VALID) {
            throw new BadPasswordException(result.getErrors());
        }
    }
}
```

### Constraint Provider
This code snippet illustrates a custom password constraint that enforces a password length between 5 and 12 characters.  The key observations to make are the location of the interface the constraint developer implements and the two class annotations required for the implementation to be eligible for auto-wiring through context scanning.

Two notable details related to return values and exceptions are worth highlighting by way of deliberate discussion here.

* Regarding return values, the Password Validation Service will interpret an empty `java.util.Collection` return value the same way as a null return value.  In both cases, the return value states no violations of any rule(s) encapsulated by the constraint's host class were found.

* Regarding exceptions, if any registered constraint providers throw an unchecked exception when called, the service provider still calls the remaining providers and collects error messages, and the `getResult()` method of its return model changes.  If none of the constraint providers that returned without an Exception generate error messages, the return value changes from `VALID` to `UNKNOWN`.  Otherwise, the return value changes from `INVALID` to `UNKNOWN_INVALID`.

```java
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
```

