package creditCardFiles;

import java.time.LocalDate;

public class Purchase extends Transaction
{
	private PurchaseType purchaseType;
	private Vendor vendor;
	
	public Purchase(PurchaseType purchaseType, double amount, LocalDate date, Vendor vendor) {
		
		super(TransactionType.PURCHASE, amount, date);
		this.purchaseType = purchaseType;
		this.vendor = vendor;
	}

	public PurchaseType getPurchaseType() {
		
		return purchaseType;
	}

	@Override
	public String toString() {
		
		return super.toString() 
				+ "\nVendor:" + vendor.toString() 
				+"\nPurchase type: " + purchaseType;
	}
	

}
