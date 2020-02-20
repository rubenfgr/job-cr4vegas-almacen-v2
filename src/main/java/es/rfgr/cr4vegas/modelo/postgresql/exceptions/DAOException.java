package es.rfgr.cr4vegas.modelo.postgresql.exceptions;

@SuppressWarnings("serial")
public class DAOException extends Exception {

	protected Throwable throwable;

	public DAOException(String message) {
		super(message);
	}

	public DAOException(String message, Throwable throwable) {
		super(message);
		this.throwable = throwable;
	}

	public Throwable getCause() {
		return throwable;
	}
}
