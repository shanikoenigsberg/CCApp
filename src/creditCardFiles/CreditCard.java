package creditCardFiles;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Objects;
import java.util.PriorityQueue;

public class CreditCard {

	private String creditCardID;
	private LocalDate issueDate;
	private LocalDate expirationDate;
	private String issueCompany;
	private CreditCardType creditCardType;
	private CreditCardStatus status;
	private double creditCardLimit;
	private double currentBalance;
	private double availCredit;
	private ArrayList<Transaction> transactions;
	private int purchaseModCount;
	private int paymentModCount;

	public CreditCard(String creditCardID, LocalDate issueDate, LocalDate expirationDate, String issueCompany,
			CreditCardType creditCardType, double creditCardLimit) {

		this.creditCardID = creditCardID;

		this.issueDate = issueDate;

		if (expirationDate.isBefore(issueDate)) {
			throw new CreditCardException("Invalid Expiration Date.");
		}

		this.expirationDate = expirationDate;

		this.issueCompany = issueCompany;

		this.creditCardType = creditCardType;

		// all cards start off active- why would someone put in card thats not active
		this.status = CreditCardStatus.ACTIVE;

		this.creditCardLimit = creditCardLimit;

		this.currentBalance = 0;

		this.availCredit = creditCardLimit;

		this.transactions = new ArrayList<Transaction>();
		
		this.purchaseModCount = 0;;
		
		this.paymentModCount = 0;
		
	}

	public void addPurchase(Purchase purchase) {

		if (!transactions.contains(purchase)) {

			if (currentBalance + purchase.getAmount() <= creditCardLimit) {

				currentBalance += purchase.getAmount();
				availCredit -= purchase.getAmount();
				
				transactions.add(purchase);
				
				purchaseModCount++;
				
			} else {
				throw new IllegalTransactionException("Purchase amount exceeds credit limit");
			}

			

		}

		else {
			throw new DuplicateTransactionException("Purchase is already recorded");
		}
	}

	public void addPayment(Payment payment) {

		if (!transactions.contains(payment)) {

			// allow all payments to be made, even those that make the balance negative
			currentBalance -= payment.getAmount();
			availCredit += payment.getAmount();

			transactions.add(payment);
			
			paymentModCount++;
		} else {

			throw new DuplicateTransactionException("Payment is already recorded");
		}

	}

	public void addFee(Fee fee) {

		if (!transactions.contains(fee)) {

			// allow fees even to be added even if will exceed the credit limit

			currentBalance += fee.getAmount();
			availCredit -= fee.getAmount();

			transactions.add(fee);
		} else {

			throw new DuplicateTransactionException("Fee is already recorded");
		}

	}

	public double getCurrentBalance() {

		return currentBalance;
	}

	public double getAvailCredit() {

		return availCredit;
	}

	public Purchase getLargestPurchase() {

		if (!transactions.isEmpty()) {
			PriorityQueue<Purchase> purchases = new PriorityQueue<Purchase>(new PurchaseAmountComparator());
			for (Transaction t : transactions) {

				if (t.getClass() == Purchase.class) {

					purchases.add((Purchase) t);
				}
			}

			return purchases.peek();
		}

		return null;
	}

	public double getTotalFees() {

		double totalFees = 0;
		if (!transactions.isEmpty()) {

			for (Transaction t : transactions) {

				if (t.getClass() == Fee.class) {

					Fee currFee = (Fee) t;
					totalFees += currFee.getAmount();
				}
			}
		}

		return totalFees;
	}

	public Purchase getMostRecentPurchase() {
		
		Iterator<Purchase> pIterator = purchaseIterator();
		Purchase mostRecentPurchase = null;
		Purchase currPurchase;

		
		if(pIterator.hasNext()) {
			mostRecentPurchase = pIterator.next();
		}
		while(pIterator.hasNext()) {
			currPurchase = pIterator.next();
			if(currPurchase.getTransactionDate().isAfter(mostRecentPurchase.getTransactionDate())) {
				mostRecentPurchase = currPurchase;
			}
		}
		return mostRecentPurchase;
	}

	public Payment getMostRecentPayment() {

		Iterator<Payment> pIterator = paymentIterator();
		Payment mostRecentPayment = null;
		Payment currPayment;

		
		if(pIterator.hasNext()) {
			mostRecentPayment = pIterator.next();
		}
		while(pIterator.hasNext()) {
			currPayment = pIterator.next();
			if(currPayment.getTransactionDate().isAfter(mostRecentPayment.getTransactionDate())) {
				mostRecentPayment = currPayment;
			}
		}
		return mostRecentPayment;
	}

	public CreditCardStatus getStatus() {
		return status;
	}

	public void setStatus(CreditCardStatus status) {
		this.status = status;
	}

	public String getCreditCardID() {
		return creditCardID;
	}

	public LocalDate getIssueDate() {
		return issueDate;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public String getIssueCompany() {
		return issueCompany;
	}

	public CreditCardType getCreditCardType() {
		return creditCardType;
	}

	public double getCreditCardLimit() {
		return creditCardLimit;
	}

	public ArrayList<Transaction> getTransactions() {
		ArrayList<Transaction> copy = new ArrayList<Transaction>();
		copy.addAll(transactions);
		return copy;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreditCard other = (CreditCard) obj;
		return Objects.equals(creditCardID, other.creditCardID);
	}

	public Iterator<Purchase> purchaseIterator() {
		return new PurchaseIterator();
	}

	public Iterator<Payment> paymentIterator() {
		return new PaymentIterator();
	}

	class PurchaseIterator implements Iterator<Purchase> {

		private ArrayList<Purchase> purchases;
		private int index;
		private int expectedPurchCount;

		public PurchaseIterator() {
			purchases = new ArrayList<Purchase>();
			Iterator<Transaction> iter = transactions.iterator();
			while (iter.hasNext()) {
				Transaction p = iter.next();
				if (p.getClass().equals(Purchase.class)) {
					purchases.add((Purchase) p);
				}
			}

			if (purchases.size() > 0) {
				index = 0;
			} else {
				index = -1;
			}
			
			expectedPurchCount = purchaseModCount;

		}

		@Override
		public boolean hasNext() {
			if (index >= 0 && index < purchases.size()) {
				return true;
			}
			return false;

		}

		@Override
		public Purchase next() {
			if(expectedPurchCount != purchaseModCount) {
				throw new ConcurrentModificationException();
			}
			if (hasNext()) {
				Purchase currPurchase = purchases.get(index);
				index++;
				return currPurchase;
			}
			return null;

		}

	}

	class PaymentIterator implements Iterator<Payment> {

		private ArrayList<Payment> payment;
		private int expectedPayCount;
		private int index;

		public PaymentIterator() {
			payment = new ArrayList<Payment>();
			Iterator<Transaction> iter = transactions.iterator();
			while (iter.hasNext()) {
				Transaction p = iter.next();
				if (p.getClass().equals(Payment.class)) {
					payment.add((Payment) p);
				}
			}

			if (payment.size() > 0) {
				index = 0;
			} else {
				index = -1;
			}
			
			expectedPayCount = paymentModCount;

		}

		@Override
		public boolean hasNext() {
			if (index >= 0 && index < payment.size()) {
				return true;
			}
			return false;

		}

		@Override
		public Payment next() {
			if(expectedPayCount != paymentModCount) {
				throw new ConcurrentModificationException();
			}
			if (hasNext()) {
				Payment currPayment = payment.get(index);
				index++;
				return currPayment;
			}
			return null;

		}

	}

}