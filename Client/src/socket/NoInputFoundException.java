package socket;

public class NoInputFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoInputFoundException(String msg, Throwable e) {
		super(msg,e);
	}

	public NoInputFoundException(String msg) {
		super(msg);
	}

}

