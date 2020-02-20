package es.rfgr.cr4vegas.modelo.postgresql.exceptions;

@SuppressWarnings("serial")
public class ConexionException extends Exception {

	protected Throwable throwable;

	public ConexionException(String message) {
		super(message);
	}

	public ConexionException(String message, Throwable throwable) {
		super(message);
		this.throwable = throwable;
	}

	public Throwable getCause() {
		return throwable;
	}
}
