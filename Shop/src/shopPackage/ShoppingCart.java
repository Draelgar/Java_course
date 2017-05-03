package shopPackage;

public class ShoppingCart extends Storage<Item>{
	
	// Get the total price for the contents of this storage.
	public double getPrice() {
		double sum = 0.0;
		
		for(Item item : mItems) {
			sum += item.getPrice() * item.getCount();
		}
		
		return sum;
	}
}
