package creditCardFiles;

public class CreditCardException extends RuntimeException {
	
	public CreditCardException() {
		super("Credit Card Exception");
	}
	
	public CreditCardException(String message) {
		super(message);
	}

}
