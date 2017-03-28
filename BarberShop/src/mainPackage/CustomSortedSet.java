package mainPackage;

/** A custom made sorted set class.
 * @author Gustaf Peter Hultgren
 * @version 1.0 **/
public class CustomSortedSet<T extends Comparable<T>> {
	private CustomSetNode<T> mRoot;
	private int mSize;
	
	/** Create a new custom sorted set. **/
	public CustomSortedSet() {
		mRoot = null;
		mSize = 0;
	}
	
	/** Add a new item to the set. If the item already exist within the set, it will be discarded. 
	 * After being added, the set will be in order. 
	 * @return True if the item was added. Else False. **/
	public boolean add(T t) {
		if(mRoot == null)
			mRoot = new CustomSetNode<T>(t);
		else {
			CustomSetNode<T> node = mRoot;
			CustomSetNode<T> parent = null;
			
			while(node != null) {
				int result = node.getData().compareTo(t);
				if(result == 0) {
					// node and t are equal.
					return false;
				}
				else if(result == -1) {
					// t comes before node.
					CustomSetNode<T> child = new CustomSetNode<T>(t);
					child.setNode(node);
					if(parent != null)
						parent.setNode(child);
					else
						mRoot = child;
					
					mSize++;
					return true;
				}
				
				parent = node;
				node = node.getChild();
			}
			
			// Add to end of list!
			parent.setNode(new CustomSetNode<T>(t));
			
		}
		
		mSize++;
		return true;
	}
	
	/** Get the item at the selected index within the set.
	 * @param index -The index to look for.
	 * @return The desired item or null if the index is outside the array. **/
	public T get(int index) {
		if(index < mSize) {
			CustomSetNode<T> node = mRoot;
			for(int i = 1; i <= index; i++) {
				node = node.getChild();
			}
			
			return node.getData();
		}
		else
			return null;
	}
}
