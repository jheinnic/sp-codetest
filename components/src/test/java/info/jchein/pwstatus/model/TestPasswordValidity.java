package info.jchein.pwstatus.model;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertNotEquals;
import info.jchein.pwstatus.model.PasswordValidity.ResultKind;

import java.awt.image.SampleModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.cert.TrustAnchor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.omg.CORBA.PUBLIC_MEMBER;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.google.common.io.FileBackedOutputStream;
import com.google.common.io.InputSupplier;

public class TestPasswordValidity extends TestCase {
	// JSON serialization artifacts that are created at test setUp() time.
	private MappingJsonFactory jsonFactory;
	private FileBackedOutputStream bufferedOutputStream;
	private JsonGenerator jsonGenerator;
	
	// Subject Under Test
	private PasswordValidity testObject;

	private PasswordValidity testDefaultObject;

	private PasswordValidity testPassObject;

	private PasswordValidity testFailObject;
	
	private List<String> onFailErrorsList;
	private List<String> alternateErrorList;
	
	// Expected serialization examples
	private static final String SERIALIZED_FAIL_EXPECTATION = 
			"{\"result\":\"INVALID\",\"errors\":[\"Vote Cody for President!\","+
			"\"A stitch in time saves nine.  Please confirm your time has been stitched.\"," + 
			"\"An apple a day keeps the doctor away.  Apple has dumped core.  No doctor not found.\"," +
			"\"The command could not be completed because the command completed successfully.\"]}";
	private static final String SERIALIZED_PASS_EXPECTATION = 
			"{\"result\":\"VALID\",\"errors\":[]}";
			
	protected void setUp() throws IOException {
		jsonFactory = new MappingJsonFactory();
		bufferedOutputStream = new FileBackedOutputStream(2048);
		jsonGenerator = jsonFactory.createGenerator(bufferedOutputStream);
		
		testDefaultObject = new PasswordValidity();
		
		testPassObject = new PasswordValidity();
		testPassObject.setResult(ResultKind.VALID);
		testPassObject.setErrors(null);
	
		onFailErrorsList = new ArrayList<String>(4);
		onFailErrorsList.add("Vote Cody for President!");
		onFailErrorsList.add("A stitch in time saves nine.  Please confirm your time has been stitched.");
		onFailErrorsList.add("An apple a day keeps the doctor away.  Apple has dumped core.  No doctor not found.");
		onFailErrorsList.add("The command could not be completed because the command completed successfully.");
		
		alternateErrorList = new ArrayList<String>(2);
		alternateErrorList.add("We must have done a lot better this time since half the errors are gone!");
		alternateErrorList.add("Faulty deductive reasoning deduced.");

		testFailObject = new PasswordValidity();
		testFailObject.setResult(ResultKind.INVALID);
		testFailObject.setErrors(onFailErrorsList);
	}
	
	@Test
	public void testResultMutator() {
		testDefaultObject.setResult(ResultKind.UNKNOWN);
		assertEquals(ResultKind.UNKNOWN, testDefaultObject.getResult());
		testDefaultObject.setResult(ResultKind.VALID);
		assertEquals(ResultKind.VALID, testDefaultObject.getResult());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFaultyInputHandling() {
		try {
			testDefaultObject.setResult(null);
			fail();
		} catch( IllegalArgumentException e ) {
		} catch( Throwable e ) {
			fail();
		}
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testReturnErrorListIsolation() {
		final List<String> changeList = testFailObject.getErrors();
		try {
			changeList.add( "You've lost your muchiness." );
			fail();
		} catch( UnsupportedOperationException e ) {
		} catch( Throwable e ) {
			fail();
		}
	}
	
	@Test
	public void testInputErrorListIsolation() {
		final List<String> copyOfOriginalErrorList = new ArrayList<String>(onFailErrorsList);
		final List<String> preChangeList = testFailObject.getErrors();
		onFailErrorsList.add("Happy birthday!");
		final List<String> postChangeList = testFailObject.getErrors();

		// Modifying the last list used to set does not modify either any previously retrieved 
		// result or any subsequently retrieved result.
		assertNotEquals(copyOfOriginalErrorList, onFailErrorsList);
		assertEquals(copyOfOriginalErrorList, preChangeList);
		assertEquals(copyOfOriginalErrorList, postChangeList);
		
		assertNotSame(onFailErrorsList, preChangeList);
		assertNotSame(onFailErrorsList, postChangeList);
	}
	
	@Test
	public void testModelUpdateIsolation() {
		// Changing the model's list does not affect any previously received result and does
		// actually change the return value.
		final List<String> preChangeList = testFailObject.getErrors();
		testFailObject.setErrors(alternateErrorList);
		final List<String> postChangeList = testFailObject.getErrors(); 

		assertEquals(onFailErrorsList, preChangeList);
		assertNotEquals(alternateErrorList, preChangeList);
		
		assertNotEquals(onFailErrorsList, postChangeList);
		assertEquals(alternateErrorList, postChangeList);
		
		assertNotSame(postChangeList, preChangeList);
	}
	
	@Test
	public void testEmptyErrorSemantics() {
		// Default is empty
		List<Object> emptyList = Collections.emptyList();
		assertEquals( emptyList, testDefaultObject.getErrors() );
		
		// Non-empty lists cause the getter to return non-empty.
		testDefaultObject.setErrors(onFailErrorsList);
		assertEquals( onFailErrorsList, testDefaultObject.getErrors() );
		assertNotEquals( emptyList, testDefaultObject.getErrors() );
		
		// Empty list is a valid input to setter
		testDefaultObject.setErrors(Collections.<String>emptyList());
		assertEquals( emptyList, testDefaultObject.getErrors() );
		
		// As-is null
		testDefaultObject.setErrors(onFailErrorsList);
		assertNotEquals( emptyList, testDefaultObject.getErrors() );
		testDefaultObject.setErrors(null);
		assertEquals( emptyList, testDefaultObject.getErrors() );		
	}

	@Test
	public void testPassToJSON() throws JsonProcessingException, IOException {
		jsonGenerator.writeObject(testPassObject);
		
		final String contentBuffer = doSerialize();
		
		assertEquals(SERIALIZED_PASS_EXPECTATION, contentBuffer);
	}
	
	@Test
	public void testFailToJSON() throws JsonProcessingException, IOException {
		jsonGenerator.writeObject(testFailObject);
		
		final String contentBuffer = doSerialize();
		
		assertEquals(SERIALIZED_FAIL_EXPECTATION, contentBuffer);
	}
	
	
	@Test
	public void testPassFromJSON() throws JsonProcessingException, IOException {
		final PasswordValidity returnObject = doParse(SERIALIZED_PASS_EXPECTATION);
		assertNotNull(returnObject);
		assertEquals(ResultKind.VALID, returnObject.getResult());
		assertEquals(Collections.emptyList(), returnObject.getErrors());
	}
	
	
	@Test
	public void testFailFromJSON() throws JsonProcessingException, IOException {
		final PasswordValidity returnObject = doParse(SERIALIZED_FAIL_EXPECTATION);
		assertNotNull(returnObject);
		assertEquals(ResultKind.INVALID, returnObject.getResult());
		assertEquals(onFailErrorsList, returnObject.getErrors());
	}

	private String doSerialize() throws IOException {
		final InputSupplier<InputStream> readBackSupplier = bufferedOutputStream.getSupplier();
		final InputStream inputStream = readBackSupplier.getInput();
		final InputStreamReader streamReader = new InputStreamReader(inputStream);
		final BufferedReader returnReader = new BufferedReader(streamReader, 2048);
		final String contentBuffer = returnReader.readLine();
		return contentBuffer;
	}

	private PasswordValidity doParse(final String jsonString) throws IOException, JsonProcessingException {
		final JsonParser jsonParser = jsonFactory.createParser(jsonString);
		final Iterator<PasswordValidity> returnIterator = 
			jsonParser.readValuesAs(PasswordValidity.class);
		assertTrue(returnIterator.hasNext());
		
		return returnIterator.next();
	}
}
