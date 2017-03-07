package shopPackage;

import java.util.Iterator;

public class ShoppingCart extends Storage<Item>{
	
	// Get the total price for the contents of this storage.
	public double getPrice() {
		double sum = 0.0;
		Iterator<Item> it = mItems.iterator();
		
		while(it.hasNext()) {
			sum += it.next().getPrice();
		}
		
		return sum;
	}
}
