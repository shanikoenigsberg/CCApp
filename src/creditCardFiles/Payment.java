package creditCardFiles;

import java.time.LocalDate;

public class Payment extends Transaction {
	
	private PaymentType paymentType;
	private BankAccount account;

	public Payment(PaymentType type, BankAccount account, double amount, LocalDate date) {
		
		super(TransactionType.PAYMENT, amount, date);
		this.paymentType = type;
		this.account = account;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public BankAccount getAccount() {
		return account;
	}

	@Override
	public String toString() {
		
		return super.toString() + "\nPayment type: " + paymentType;
	}
}
