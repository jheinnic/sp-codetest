package info.jchein.pwstatus.impl;


/**
 * A decorator class for CharSequence that prevents the decorated CharSequence from being converted
 * to a String.
 * 
 * The purpose of this class is to prevent the creation of an immutable copy of sensitive user data
 * while still providing transient access to its content through the CharSequence interface.  Immutable
 * String objects remain in memory until such time that the garbage collector reclaims them and the 
 * memory they occupied is overwritten by something else.  A best practice for handling sensitive data
 * is to not keep it in memory past the time when its use is elapsed, so character sequences that
 * represent user passwords and other sensitive strings must remain in mutable data structures so it
 * remains possible to overwrite their content once it is no longer needed to service a request.
 * 
 * Since CharSequence.toString() would violate this constraint, this variant obfuscates the actual
 * content it represents when that method is invoked.  If a CharSequence-dependent algorithm depends on
 * a true implementation of CharSequence.toString(), however, it will be incompatible with instances of
 * this decorator class--developers should seek an alternative algorithm if possible rather than not
 * using this class when its safeguards are appropriate.
 * 
 * NOTE: This class cannot prevent a stubborn consumer from reading the content into an orthogonal
 * character array and using that to create an immutable String.  It cannot prevent this from occurring,
 * but since most code that seeks to convert a CharacterSequence to a String will likely just call the
 * toString() method, this class is sufficient to prevent accidental retention of sensitive user data
 * in the common case without being capable of providing an absolute guarantee.
 * 
 * @author John
 *
 */
final class ProtectedCharSequence implements CharSequence {
	/**
	 * Factory method for retrieving a String-preventing CharSequence decorator from an unprotected
	 * CharSequence of any other type.
	 * 
	 * @param sequence
	 * @return A ProtectedCharSequence decorator that has a non-functional toString() method.
	 */
	public static ProtectedCharSequence wrap( final char[] sequence ) {
		if( sequence == null ) { throw new IllegalArgumentException(); }

		return new ProtectedCharSequence( sequence, 0, sequence.length );
	}
	
	private static ProtectedCharSequence wrap( final char[] sequence, final int start, final int end ) {
		return new ProtectedCharSequence( sequence, start, end );
	}
	
	private final char[] sequence;
	private final int begin;
	private final int end;
	
	private ProtectedCharSequence(char[] sequence, int begin, int end) {
		this.sequence = sequence;
		this.begin = begin;
		this.end = end;
	}
	
	@Override
	public int length() {
		return end - begin;
	}

	@Override
	public char charAt(int index) {
		final int adjIndex = getAdjustedIndex(index);
		
		return sequence[adjIndex];
	}

	private int getAdjustedIndex(int index) {
		if( index < 0 ) {
			throw new IndexOutOfBoundsException();
		}
		
		final int adjIndex = index + begin;
		
		if( adjIndex >= end ) {
			throw new IndexOutOfBoundsException();
		}
		
		return adjIndex;
	}

	@Override
	public CharSequence subSequence(int begin, int end) {
		if( end < begin ) { throw new IndexOutOfBoundsException(); }
		
		final int adjBegin = getAdjustedIndex(begin);
		final int adjEnd = getAdjustedIndex(end);
		
		return wrap(sequence, adjBegin, adjEnd);
	}

	@Override
	public String toString() {
		throw 
			new UnsupportedOperationException(
				"Sensitive data must not be transformed to an immutable representation/."
			);
	}
	
	void destroy() {
		if( begin > 0 || end < sequence.length ) {
			throw new UnsupportedOperationException( "Destruction requires the outermost adapter" );
		}
		for( int ii=0; ii<sequence.length; ii++ ) {
			sequence[ii] = Character.MIN_VALUE; 
		}
	}
}
