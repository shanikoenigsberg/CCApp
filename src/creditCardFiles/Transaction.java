package creditCardFiles;
import java.time.LocalDate;
import java.util.Objects;

public class Transaction {
	
	private long transactionID;
	
	static long lastTransactionID = 1000000000;
	
	private LocalDate transactionDate;
	private TransactionType transactionType;
	
	private double amount;
	
	public Transaction(TransactionType type, double amount, LocalDate date) {
		
		if (lastTransactionID ==0) {
			throw new IllegalTransactionException("Exceeded maximum transaction amount");
		}
		
		transactionID = lastTransactionID--;
		
		if(date.isAfter(LocalDate.now())) {
			throw new IllegalTransactionException("Transaction date cannot be in the future.");
		}
		
		this.transactionDate = date;
		
		this.transactionType = type;
		
		if(amount < 0) {
			throw new IllegalTransactionException("Invalid transaction amount");
		}
		
		this.amount = amount;
	}
	
	public double getAmount() {
		
		return this.amount;
	}

	public long getTransactionID() {
		return transactionID;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false; 
			}
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		Transaction other = (Transaction) obj;
		
		return transactionID == other.transactionID;
	}

	@Override
	public String toString() {
		return "Transaction Type: " + transactionType  + "\n------------\nID: " + transactionID + "\nDate: " + transactionDate
				+ "\nAmount: " + amount;
	}

	
	

}
