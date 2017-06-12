package dbConnection;

public class DALException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DALException(String msg, Throwable e) {
		super(msg,e);
	}

	public DALException(String msg) {
		super(msg);
	}
	
	public DALException(Throwable e) {
		super(e);
	}
}