package creditCardFiles;

import java.util.Comparator;

public class PaymentDateComparator implements Comparator<Payment>{

	@Override
	public int compare(Payment p1, Payment p2) {
		//want priority queue to work opposite way- descending order
		return -(p1.getTransactionDate().compareTo(p2.getTransactionDate()));
	}
	
	

}
