package creditCardFiles;

import java.time.LocalDate;

public class Fee extends Transaction {
	
	private FeeType feeType;
	
	public Fee (FeeType feeType, double amount, LocalDate date) {
		
		super(TransactionType.FEE, amount, date);
		this.feeType = feeType;
	}

	public FeeType getFeeType() {
		
		return feeType;
	}

	@Override
	public String toString() {
		
		return super.toString() + "\nFee type: " + feeType;
	}
}
