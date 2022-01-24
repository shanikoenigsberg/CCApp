package creditCardFiles;

import java.util.Comparator;

public class PurchaseAmountComparator implements Comparator<Purchase>{

	@Override
	public int compare(Purchase p1, Purchase p2) {
		//will make the the priority queue in descending order
		if(p1.getAmount() < p2.getAmount()) {
			return 1;
		}
		else if(p1.getAmount() > p2.getAmount()) {
			return -1;
		}
		return 0;
	}
	
	

}
