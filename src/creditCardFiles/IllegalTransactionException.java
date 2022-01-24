package creditCardFiles;

public class IllegalTransactionException extends RuntimeException {

	public IllegalTransactionException() {
		
		super("Illegal Transaction Exception");
	}
	
	public IllegalTransactionException(String message) {
		
		super(message);
	}
}
