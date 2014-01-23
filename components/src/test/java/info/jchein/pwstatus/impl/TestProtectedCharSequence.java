package info.jchein.pwstatus.impl;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class TestProtectedCharSequence extends TestCase {
	private static final char[] TEST_CHAR_ARRAY = { 'a', '1', 'b', 'c', '2', '3', 'x', 'y', 'z' };
	
	// Subject Under Test
	ProtectedCharSequence testSubject;
	
	@Before
	@Override
	protected void setUp() {
		testSubject = ProtectedCharSequence.wrap(TEST_CHAR_ARRAY);
	}

	@Test(/*expected = IllegalArgumentException.class*/)
    public void testNullWrapInput() {
		try {
			ProtectedCharSequence.wrap(null);
			fail();
		} catch( IllegalArgumentException e ) {
			// This is the exception we expect to see.  Good, we passed!
		} catch( Throwable e ) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testLength() {
		assertEquals(9, testSubject.length());
	}
	
	@Test
	public void testNegativeIndexCharAt() {
		try {
			testSubject.charAt(-1);
			fail();
		} catch( IndexOutOfBoundsException e ) {
			// This is the exception we expect to see.  Good, we passed!
		} catch( Throwable e ) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testNegativeIndexSubSequence() {
		try {
			testSubject.subSequence(-1, 4);
			fail();
		} catch( IndexOutOfBoundsException e ) {
			// This is the exception we expect to see.  Good, we passed!
		} catch( Throwable e ) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testOverLargeIndexCharAt() {
		try {
			testSubject.charAt(9);
			fail();
		} catch( IndexOutOfBoundsException e ) {
			// This is the exception we expect to see.  Good, we passed!
		} catch( Throwable e ) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testOverLargeIndexSubSequence() {
		try {
			testSubject.subSequence(0, 9);
			fail();
		} catch( IndexOutOfBoundsException e ) {
			// This is the exception we expect to see.  Good, we passed!
		} catch( Throwable e ) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testDoubleOutOfBoundsSubSequence() {
		try {
			testSubject.subSequence(-1, 9);
			fail();
		} catch( IndexOutOfBoundsException e ) {
			// This is the exception we expect to see.  Good, we passed!
		} catch( Throwable e ) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testNegativeRangeSubselect() {
		try {
			testSubject.subSequence(5, 2);
			fail();
		} catch( IndexOutOfBoundsException e ) {
			// This is the exception we expect to see.  Good, we passed!
		} catch( Throwable e ) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testSubselectAccuracy() {
		CharSequence subSequence = testSubject.subSequence(2, 6);
		assertEquals(4,  subSequence.length());
		assertEquals('b', subSequence.charAt(0));
		assertEquals('3', subSequence.charAt(3));

		try {
			subSequence.charAt(-1);
			fail();
		} catch( IndexOutOfBoundsException e ) {
			// This is the exception we expect to see.  Good, we passed!
		} catch( Throwable e ) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		try {
			subSequence.charAt(5);
			fail();
		} catch( IndexOutOfBoundsException e ) {
			// This is the exception we expect to see.  Good, we passed!
		} catch( Throwable e ) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testNestedSubselectAccuracy() {
		CharSequence subSequence = testSubject.subSequence(1, 7);
		CharSequence subSubSequence = subSequence.subSequence(1, 5);
		
		assertEquals(4,  subSubSequence.length());
		assertEquals('b', subSubSequence.charAt(0));
		assertEquals('3', subSubSequence.charAt(3));

		assertEquals(6,  subSequence.length());
		assertEquals('1', subSequence.charAt(0));
		assertEquals('2', subSequence.charAt(3));

		try {
			subSubSequence.charAt(-1);
			fail();
		} catch( IndexOutOfBoundsException e ) {
			// This is the exception we expect to see.  Good, we passed!
		} catch( Throwable e ) {
			e.printStackTrace();
			fail(e.getMessage());
		}

		try {
			subSubSequence.charAt(5);
			fail();
		} catch( IndexOutOfBoundsException e ) {
			// This is the exception we expect to see.  Good, we passed!
		} catch( Throwable e ) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testIndexedGetAccuracy() {
		assertEquals('a', testSubject.charAt(0));
		assertEquals('1', testSubject.charAt(1));
		assertEquals('b', testSubject.charAt(2));
		assertEquals('c', testSubject.charAt(3));
		assertEquals('2', testSubject.charAt(4));
		assertEquals('3', testSubject.charAt(5));
		assertEquals('x', testSubject.charAt(6));
		assertEquals('y', testSubject.charAt(7));
		assertEquals('z', testSubject.charAt(8));
	}
	
	@Test
	public void testToStringRejected() {
		try {
			testSubject.toString();
			fail();
		} catch( UnsupportedOperationException e ) {
			// This is the exception we expect to see.  Good, we passed!
		} catch( Throwable e ) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testSubstringToStringRejected() {
		CharSequence subSequence = testSubject.subSequence(1, 7);
		
		try {
			subSequence.toString();
			fail();
		} catch( UnsupportedOperationException e ) {
			// This is the exception we expect to see.  Good, we passed!
		} catch( Throwable e ) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testNestedSubstringToStringRejected() {
		CharSequence subSequence = testSubject.subSequence(1, 7);
		CharSequence subSubSequence = subSequence.subSequence(1, 5);
		
		try {
			subSubSequence.toString();
			fail();
		} catch( UnsupportedOperationException e ) {
			// This is the exception we expect to see.  Good, we passed!
		} catch( Throwable e ) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
