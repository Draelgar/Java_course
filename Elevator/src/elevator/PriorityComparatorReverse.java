package elevator;

import java.util.Comparator;

/** Compares objects in reversed order. 
 * @author Gustaf Peter Hultgren
 * @version 1.0 **/
public class PriorityComparatorReverse<T extends PriorityData<T>> implements Comparator<T> {
	/** Order the priorities sequentially, but the values reversed. 
	 * @param o1 -Object 1.
	 * @param 02 -Object 2.
	 * @return Reversed result as the objects' own compareTo methods.**/
	@Override
	public int compare(T o1, T o2) {
		if(o1.getPriority() == o2.getPriority()) {
			if(o1.getData() == o2.getData())
				return 0;
			else if(o1.getData() > o2.getData())
				return -1;
			else
				return 1;
		}
		else if(o1.getPriority() < o2.getPriority())
			return -1;
		else
			return 1;
		
	}
}
