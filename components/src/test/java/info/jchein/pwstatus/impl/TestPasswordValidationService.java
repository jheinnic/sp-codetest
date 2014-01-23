package info.jchein.pwstatus.impl;

import static org.easymock.EasyMock.expect;
import static org.powermock.api.easymock.PowerMock.createNiceMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.mockStaticNice;
import static org.powermock.api.easymock.PowerMock.replayAll;
import static org.powermock.api.easymock.PowerMock.verifyAll;
import info.jchein.lang.AssumptionViolatedException;
import info.jchein.pwstatus.model.PWStatusFactory;
import info.jchein.pwstatus.model.PasswordValidity;
import info.jchein.pwstatus.spi.IPasswordConstraintSpi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.Appender;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.mockpolicies.Slf4jMockPolicy;
import org.powermock.core.classloader.annotations.MockPolicy;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.slf4j.Logger;

@RunWith(PowerMockRunner.class)
@MockPolicy(Slf4jMockPolicy.class)
@SuppressStaticInitializationFor("info.jchein.pwstatus.impl.PasswordValidationService")
@PrepareForTest({
	Appender.class, PWStatusFactory.class, PasswordValidity.class, 
	ProtectedCharSequence.class, AssumptionViolatedException.class
})
public class TestPasswordValidationService extends TestCase {
	// Constants
	private static final char[] PW_INPUT_CHARS = { 'a', 'b', 'c', '1', '2', '3' };
	
    private static final List<String> EMPTY_LIST = Collections.emptyList();

    private static final String ERROR_ONE_STRING = "Error One";
    private static final String ERROR_TWO_STRING = "Second Error";
    private static final List<String> ONE_ERROR = new ArrayList<String>(2);
    private static final List<String> TWO_ERRORS = new ArrayList<String>(2);

    static {
    	ONE_ERROR.add(ERROR_ONE_STRING);
    	TWO_ERRORS.add(ERROR_ONE_STRING);
    	TWO_ERRORS.add(ERROR_TWO_STRING);
    }

    // True Mock objects
    IPasswordConstraintSpi spiOne;
    IPasswordConstraintSpi spiTwo;
    IPasswordConstraintSpi spiThree;

    // Stub Objects
    Logger dummyLogger;
    ProtectedCharSequence dummyCharSequence;
    PasswordValidity dummyReturn;
    AssumptionViolatedException dummyException;
    
    // Subject Under Test!
    PasswordValidationService pwValidationService;
    
    @Before
    public void setUp() {
        dummyLogger = createNiceMock(Logger.class);
        Whitebox.setInternalState(PasswordValidationService.class, "LOG", dummyLogger);
        
        pwValidationService = new PasswordValidationService();
        
        spiOne = createNiceMock(IPasswordConstraintSpi.class);
        spiTwo = createNiceMock(IPasswordConstraintSpi.class);
        spiThree = createNiceMock(IPasswordConstraintSpi.class);
        
        HashSet<IPasswordConstraintSpi> constraintSpis = new HashSet<IPasswordConstraintSpi>(4);
        constraintSpis.add(spiOne);
        constraintSpis.add(spiTwo);
        constraintSpis.add(spiThree);
        Whitebox.setInternalState(pwValidationService, "constraints", constraintSpis);
        
        dummyReturn = createNiceMock(PasswordValidity.class);
        dummyException = createNiceMock(AssumptionViolatedException.class);
        dummyCharSequence = createNiceMock(ProtectedCharSequence.class);
        
        mockStaticNice(PWStatusFactory.class);
        mockStaticNice(ProtectedCharSequence.class);
        expect(
            ProtectedCharSequence.wrap(EasyMock.<char[]>anyObject())
        ).andStubReturn(dummyCharSequence);
    }
    
    @Test
    public void testValidScenario() {
    	expect(
    		spiOne.returnErrorStrings(EasyMock.<CharSequence>anyObject())
    	).andReturn(EMPTY_LIST);
    	expect(
        	spiTwo.returnErrorStrings(EasyMock.<CharSequence>anyObject())
        ).andReturn(EMPTY_LIST);
    	expect(
            spiThree.returnErrorStrings(EasyMock.<CharSequence>anyObject())
        ).andReturn(EMPTY_LIST);
    	expect(
    		PWStatusFactory.createPassedPasswordValidityTest()
    	).andReturn(dummyReturn);
    	
    	dummyCharSequence.destroy();
    	expectLastCall();
    	
    	replayAll();
    	pwValidationService.checkValidity(PW_INPUT_CHARS);
    	verifyAll();
    }
    
    @Test
    public void testInvalidScenario() {
    	final Capture<List<String>> mergedErrorListSpy = 
    		new Capture<List<String>>();
    	
    	expect(
    		spiOne.returnErrorStrings(EasyMock.<CharSequence>anyObject())
    	).andReturn(TWO_ERRORS);
    	expect(
        	spiTwo.returnErrorStrings(EasyMock.<CharSequence>anyObject())
        ).andReturn(EMPTY_LIST);
    	expect(
            spiThree.returnErrorStrings(EasyMock.<CharSequence>anyObject())
        ).andReturn(ONE_ERROR);
    	
    	expect(
    		PWStatusFactory.createFailedPasswordValidityTest(
    			EasyMock.capture(mergedErrorListSpy)
    		)
    	).andReturn(dummyReturn);
    	
    	dummyCharSequence.destroy();
    	expectLastCall();
    	
    	replayAll();
    	PasswordValidity validity = pwValidationService.checkValidity(PW_INPUT_CHARS);
    	verifyAll();
    	
    	// Assert that the object returned came from the factory method call
    	assertSame(dummyReturn, validity);
    	verifyMergedErrorList(
    		mergedErrorListSpy.getValue()
    	);
    }
    
    @Test
    public void testPartialValidScenario() {
    	expect(
    		spiOne.returnErrorStrings(EasyMock.<CharSequence>anyObject())
    	).andReturn(EMPTY_LIST);

    	expect(
        	spiTwo.returnErrorStrings(EasyMock.<CharSequence>anyObject())
        ).andThrow(dummyException);
    	dummyLogger.warn(EasyMock.anyString());
    	expectLastCall();
    	
    	expect(
            spiThree.returnErrorStrings(EasyMock.<CharSequence>anyObject())
        ).andReturn(EMPTY_LIST);
    	expect(
    		PWStatusFactory.createIncompletePasswordValidityTest()
    	).andReturn(null);
    	
    	dummyCharSequence.destroy();
    	expectLastCall();
    	
    	replayAll();
    	pwValidationService.checkValidity(PW_INPUT_CHARS);
    	verifyAll();
    }
    
    @Test
    public void testInvalidExceptionScenario() {
    	final Capture<List<String>> mergedErrorListSpy = 
    		new Capture<List<String>>();
    	
    	expect(
    		spiOne.returnErrorStrings(EasyMock.<CharSequence>anyObject())
    	).andReturn(TWO_ERRORS);
    	
    	expect(
        	spiTwo.returnErrorStrings(EasyMock.<CharSequence>anyObject())
        ).andThrow(dummyException);
    	dummyLogger.warn(EasyMock.anyString());
    	expectLastCall();

    	expect(
            spiThree.returnErrorStrings(EasyMock.<CharSequence>anyObject())
        ).andReturn(ONE_ERROR);
    	expect(
    		PWStatusFactory.createIncompletePasswordValidityTest(
    			EasyMock.capture(mergedErrorListSpy)
    		)
    	).andReturn(dummyReturn);
    	
    	dummyCharSequence.destroy();
    	expectLastCall();
    	
       	// Assert that the object returned came from the factory method call
    	replayAll();
    	PasswordValidity validity = pwValidationService.checkValidity(PW_INPUT_CHARS);
    	verifyAll();
    	
    	// Assert the error string list passed into the factory had the right composition
    	// of error message Strings, given the programmed SPI behavior..
    	assertSame(dummyReturn, validity);
    	verifyMergedErrorList(
    		mergedErrorListSpy.getValue()
    	);
    }
    
    @Test
    public void testCharArrayWrapping() {
    	expect(
    		ProtectedCharSequence.wrap(PW_INPUT_CHARS)
    	).andReturn(dummyCharSequence);

    	expect(
    		spiOne.returnErrorStrings(dummyCharSequence)
    	).andReturn(EMPTY_LIST);
    	expect(
        	spiTwo.returnErrorStrings(dummyCharSequence)
        ).andReturn(EMPTY_LIST);
    	expect(
        	spiThree.returnErrorStrings(dummyCharSequence)
        ).andReturn(EMPTY_LIST);
    	
    	dummyCharSequence.destroy();
    	expectLastCall();

    	replayAll();
    	pwValidationService.checkValidity(PW_INPUT_CHARS);
    	verifyAll();
    }
    
    @Test(/*expected=AssumptionViolatedException.class*/)
    public void testInternalError() {
    	final UnsupportedOperationException hypotheticalError = 
    		new UnsupportedOperationException();
    	
    	expect(
    		PWStatusFactory.createPassedPasswordValidityTest()
    	).andThrow(hypotheticalError);
    	
    	dummyLogger.error(EasyMock.anyString(), EasyMock.eq(hypotheticalError));
    	expectLastCall();
    	
    	replayAll();
    	try {
    		pwValidationService.checkValidity(PW_INPUT_CHARS);
    		fail();
    	} catch( AssumptionViolatedException e ) {
    		// The code path through this block is the only one that doesn't end the test
    		// in a failed state.
    	} catch( Throwable e ) {
    		e.printStackTrace();
    		fail(e.getMessage());
    	}
    	verifyAll();
    }

	private void verifyMergedErrorList(List<String> mergedErrorList ) {
		// Assert the error string list passed into the factory had the right composition
    	// of error message Strings, given the programmed SPI behavior..
    	assertEquals(3, mergedErrorList.size());
		assertEquals(true, mergedErrorList.remove(ERROR_ONE_STRING));
		assertEquals(true, mergedErrorList.remove(ERROR_ONE_STRING));
		assertEquals(true, mergedErrorList.remove(ERROR_TWO_STRING));
		assertTrue(mergedErrorList.isEmpty());
	}
}
