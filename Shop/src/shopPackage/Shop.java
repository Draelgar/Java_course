package shopPackage;

import java.util.List;

public class Shop {
	ShoppingCart mCart = new ShoppingCart();
	ItemStorage mWarehouse = new ItemStorage();
	
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
		}
	}
	
	public void addItemToWarehouse(Item item) {
		// Try to add a new item, is it already exists, increment it's counter.
		if(!mWarehouse.add(item))
			mWarehouse.find(item).add(item.getCount());
	}
	
	public void removeItemFromWarehouse(Item item) {
		Item currentItem = mWarehouse.find(item);
		
		if(currentItem != null) {
			currentItem.subtract(item.getCount());
			
			if(currentItem.getCount() >= 0)
				mWarehouse.remove(item);
		}
	}
	
	public void addItemToCart(Item item) {
		// Try to add a new item, is it already exists, increment it's counter.
		Item newItem = new Item(item.getArticleNumber(), item.getPrice(), item.getDescription());
		if(!mCart.add(newItem))
			mCart.find(newItem).add(1);
		
		removeItemFromWarehouse(newItem);
	}
	
	public void removeItemFromCart(Item item) {
		Item currentItem = mCart.find(item);
		currentItem.subtract(item.getCount());
		
		if(currentItem.getCount() >= 0)
			mCart.remove(item);
		
		addItemToWarehouse(item);
	}
	
	public void checkoutCart() {
		List<Item> items = mCart.getItems();
		
		for(Item item : items) {
			removeItemFromWarehouse(item);
		}
		
		items.clear();
	}
	
	public Item find(long articleNumber) {
		return mWarehouse.find(new Item(articleNumber, 0.0f, ""));
	}
	
	public Item findInCart(long articleNumber) {
		return mCart.find(new Item(articleNumber, 0.0f, ""));
	}
	
	public String listStoreContents() {
		return mWarehouse.toString();
	}
	
	public String listCartContents() {
		return mCart.toString();
	}
	
	public double getPrice() {
		return mCart.getPrice();
	}
	
	public static void main(String[] args) {
		Shop shop = new Shop();
		AppMode mode = AppMode.NONE;
		Item item = null;
		long articleNumber;
		
		// Print a welcome text.
		UserInterface.println("Welcome to this shop!");
		
		// Main application loop.
		while(mode != AppMode.EXIT) {
			mode = UserInterface.showMainMenu();
			
			switch(mode){
				case ADD: {
					if(item != null)
						shop.addItemToCart(item);
					else
						UserInterface.println("No item selected, please use the search option to pick one!");
					
					break;
				}
				case REMOVE: {
					if(item != null)
						shop.removeItemFromCart(item);
					else
						UserInterface.println("No item selected, please use the search option to pick one!");
					
					break;
				}
				case INSPECT: {
					UserInterface.println(shop.listCartContents());
					UserInterface.println("------------------------\n" + shop.getPrice() + " kr");
					break;
				}
				case SEARCH: {
					UserInterface.println("What article number do you wish to search for?");
					articleNumber = UserInterface.getUserInputLong();
					
					item = shop.find(articleNumber);
					
					if(item != null)
						UserInterface.println(item.toString());
					else
						UserInterface.println("There are no items with that article number within the shop.");
					
					break;
				}
				case CHECKOUT: {
					UserInterface.println(shop.listCartContents());
					UserInterface.println("------------------------\n" + shop.getPrice() + " kr");
					shop.checkoutCart();
					break;
				}
				case BROWSE: {
					UserInterface.println(shop.listStoreContents());
					break;
				}
				case EXIT: {
					return;
				}
				case CART: {
					UserInterface.println("What article number do you wish to search for?");
					articleNumber = UserInterface.getUserInputLong();
					
					item = shop.findInCart(articleNumber);
					
					if(item != null)
						UserInterface.println(item.toString());
					else
						UserInterface.println("There are no items with that article number within the cart.");
					
					break;
				}
				default: {
					break;
				}
			}
		}
	}
}
