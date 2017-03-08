package shopPackage;

public class Shop {
	ShoppingCart mCart = new ShoppingCart();
	ItemStorage mWarehouse = new ItemStorage();
	
	// Default constructor, loads some dummy data to the shop for testing.
	public Shop() {
		
		long articleNumber = 1735;
		double price = 12.50;
		Item item = new Item(articleNumber, price, "This is a randomly generated item.");
		item.add(24);
		
		for(int i = 0; i < 25; i++) {
			mWarehouse.add(item);
			articleNumber += 500;
			price += 7.75;
			item = new Item(articleNumber, price, "This is a randomly generated item. #" + i);
			item.add(24);
		}
	}
	
	// Add a single item to the store.
	public void addItemToWarehouse(Item item) {
		Item currentItem = mWarehouse.find(item);
		
		// Try to add a new item, if it already exists, increment it's counter.
		if(currentItem != null) {
			currentItem.add(item.getCount());
		}
		else {
			mWarehouse.add(item);
		}
	}
	
	// Remove a single item from the store.
	public boolean removeItemFromWarehouse(Item item) {
		Item currentItem = mWarehouse.find(item);
		int count = item.getCount();
		
		if(currentItem != null) {				
			if(currentItem.getCount() < count) {
				count = currentItem.getCount();
				item.subtract(item.getCount() - count);
			}
			
			currentItem.subtract(count);
			
			if(currentItem.getCount() <= 0)
				mWarehouse.remove(item);
			
			return true;
		}
		
		return false;
	}
	
	// Add a single item to the cart.
	public void addItemToCart(Item item) {
		// Try to add a new item, if it already exists, increment it's counter.
		Item newItem = new Item(item.getArticleNumber(), item.getPrice(), item.getDescription());
		
		if(removeItemFromWarehouse(newItem)) // Attempt to remove the item from the warehouse
			if(!mCart.add(newItem)) // Attempt to add the item to the cart.
				mCart.find(newItem).add(1); // Increment the amount of already existing item.
		
	}
	
	// Remove a single item to the cart.
	public void removeItemFromCart(Item item) {
		Item currentItem = mCart.find(item);
		
		if(currentItem != null) {
			currentItem.subtract(1);
			
			if(currentItem.getCount() <= 0)
				mCart.remove(item);
			
			addItemToWarehouse(item);
		}
	}
	
	// Checkout all items currently within the cart.
	public void checkoutCart() {
		mCart.getItems().clear();
	}
	
	// Search for an item within the store via its article number.
	public Item find(long articleNumber) {
		Item item = null;
		
		try {
			item = mWarehouse.find(new Item(articleNumber, 0.0f, ""));
			if(item != null) {
				UserInterface.println(item.toString());
				item = item.clone();
			}
		} catch(CloneNotSupportedException e) {
			UserInterface.println(e.getMessage());
		}
		
		return item;
	}
	
	// Search for an item within the cart via its article number.
	public Item findInCart(long articleNumber) {
		Item item = null;
		
		try {
			item = mCart.find(new Item(articleNumber, 0.0f, ""));
			if(item != null) {
				UserInterface.println(item.toString());
				item = item.clone();
			}
		} catch(CloneNotSupportedException e) {
			UserInterface.println(e.getMessage());
		}
		
		return item;
	}
	
	// List the contents of the store.
	public String listStoreContents() {
		return mWarehouse.toString();
	}
	
	// List the contents of the cart.
	public String listCartContents() {
		return mCart.toString();
	}
	
	// Get the sum of the prices of all the items within the cart.
	public double getPrice() {
		return mCart.getPrice();
	}
	
	public static void main(String[] args) {
		Shop shop = new Shop();
		AppMode mode = AppMode.NONE;
		Item item = null;
		long articleNumber;
		
		// Print a welcome text.
		UserInterface.println("Welcome to this shop.\n");
		
		// Main application loop.
		while(mode != AppMode.EXIT) {
			mode = UserInterface.showMainMenu();
			
			switch(mode){
				case ADD: { // Add active item to the cart.
					if(item != null)
						shop.addItemToCart(item);
					else
						UserInterface.println("No item selected, please use the search option to pick one.\n");
					
					break;
				}
				case REMOVE: { // Remove active item from the cart.
					if(item != null)
						shop.removeItemFromCart(item);
					else
						UserInterface.println("No item selected, please use the search option to pick one.\n");
					
					break;
				}
				case INSPECT: { // Inspect the current contents of the cart.
					UserInterface.println(shop.listCartContents());
					UserInterface.println("------------------------\n" + shop.getPrice() + " kr\n\n");
					break;
				}
				case SEARCH: { // Search for an item within the store.
					UserInterface.println("What article number do you wish to search for?\n");
					articleNumber = UserInterface.getUserInputLong();
					
					item = shop.find(articleNumber);
					
					if(item == null)
						UserInterface.println("There are no items with that article number within the shop.\n");
					
					break;
				}
				case CHECKOUT: { // Checkout the contents of the cart.
					UserInterface.println(shop.listCartContents());
					UserInterface.println("------------------------\n" + shop.getPrice() + " kr\n\n");
					shop.checkoutCart();
					break;
				}
				case BROWSE: { // Browse the shop.
					UserInterface.println(shop.listStoreContents());
					break;
				}
				case EXIT: { // Exit the application.
					return;
				}
				case CART: { // Search for an item within the cart.
					UserInterface.println("What article number do you wish to search for?\n");
					articleNumber = UserInterface.getUserInputLong();
					
					item = shop.findInCart(articleNumber);
					
					if(item == null)
						UserInterface.println("There are no items with that article number within the cart.\n");
					
					break;
				}
				default: { // Unknown command.
					UserInterface.println("Unknown command, please try again.\n");
					break;
				}
			}
		}
	}
}
