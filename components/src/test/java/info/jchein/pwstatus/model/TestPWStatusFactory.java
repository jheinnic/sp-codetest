package info.jchein.pwstatus.model;

import info.jchein.pwstatus.model.PasswordValidity.ResultKind;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

public class TestPWStatusFactory extends TestCase {
	
	private ArrayList<String> fullErrorList;
	
	private ArrayList<String> partialErrorList;
	
	private List<String> emptyErrorList; 
	
	public void setUp() {
		fullErrorList = new ArrayList<String>(3);
		fullErrorList.add("Three");
		fullErrorList.add("One");
		fullErrorList.add("Two");

		partialErrorList = new ArrayList<String>(1);
		partialErrorList.add("Three");

		emptyErrorList = Collections.<String>emptyList();
	}
	
    @Test
    public void testValidFactory() {
    	final PasswordValidity output =
    		PWStatusFactory.createPassedPasswordValidityTest();
    	assertEquals(ResultKind.VALID, output.getResult());
    	assertEquals(emptyErrorList, output.getErrors());
    }
	
    @Test
    public void testPartialValidFactory() {
    	final PasswordValidity output = 
    		PWStatusFactory.createIncompletePasswordValidityTest();
    	assertEquals(ResultKind.UNKNOWN, output.getResult());
    	assertEquals(emptyErrorList, output.getErrors());
    }
	
    @Test
    public void testFailureFactory() {
    	final PasswordValidity output = 
    		PWStatusFactory.createFailedPasswordValidityTest(fullErrorList);
    	assertEquals(ResultKind.INVALID, output.getResult());
    	assertEquals(fullErrorList, output.getErrors());
    }
	
    @Test
    public void testPartialFailureFactory() {
    	final PasswordValidity output = 
    		PWStatusFactory.createIncompletePasswordValidityTest(partialErrorList);
    	assertEquals(ResultKind.UNKNOWN_INVALID, output.getResult());
    	assertEquals(partialErrorList, output.getErrors());
    }
}
