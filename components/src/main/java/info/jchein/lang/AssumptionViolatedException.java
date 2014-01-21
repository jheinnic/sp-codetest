package info.jchein.lang;

/**
 * Something happened that we did not expect.  Can't tell you much more about it, because it wasn't
 * expected.
 * 
 * @author John
 */
public class AssumptionViolatedException extends RuntimeException {
	/**
	 */
	private static final long serialVersionUID = -3453748033233265416L;

	public AssumptionViolatedException( final String msg ) {
		super(msg);
	}
	
	public AssumptionViolatedException( final Throwable e ) {
		super(e);
	}
	
	public AssumptionViolatedException( final String msg, final Throwable e ) {
		super(msg, e);
	}
}
