package shopPackage;

import java.util.LinkedList;
import java.util.List;

public class Storage<T> {
	List<T> mItems; // The actual list of items.
	
	public Storage() {
		mItems = new LinkedList<T>();
	}
	
	// Add a new item to the list.
	public boolean add(T item) {
		if(mItems.indexOf(item) < 0) {
			mItems.add(item);
			
			return true;
		}
		
		return false;
	}
	
	// Remove the selected item from the list.
	public void remove(T item) {
		mItems.remove(item);
	}
	
	@Override
	public String toString() {
		String list = "";
		
		// Compile the while list into a single string of information.
		for(T item : mItems) {
			list += item.toString();
		}
		
		return list;
	}
	
	// Find an item by its article number.
	public T find(Item item) {
		for(T e : mItems) {
			if(e.equals(item)) {
				return e;
			}
		}
		
		return null;
	}
	
	public List<T> getItems() {
		return mItems;
	}
}
