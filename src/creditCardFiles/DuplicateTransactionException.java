package creditCardFiles;

public class DuplicateTransactionException extends RuntimeException {

	public DuplicateTransactionException() {
		
		super("Duplicate Transaction Exception");
	}
	
	public DuplicateTransactionException(String message) {
		
		super(message);
	}
}
