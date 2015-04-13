package generals;

public class CustomParsingException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final boolean 	abort;

	public CustomParsingException (String message, boolean abort)
	{
	    super(message);

		this.abort = abort;
	}

	public CustomParsingException (String message, boolean abort, Throwable cause)
	{
	    super(message, cause);

		this.abort = abort;
	}

	public boolean aborting()
	{
		return abort;
	}
}