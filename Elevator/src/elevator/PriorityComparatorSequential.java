package elevator;

import java.util.Comparator;

/** Compares objects in sequential order. 
 * @author Gustaf Peter Hultgren
 * @version 1.0 **/
public class PriorityComparatorSequential<T extends Comparable<T>> implements Comparator<T> {

	/** Order the objects in sequential order.  
	 * @param o1 -Object 1.
	 * @param 02 -Object 2.
	 * @return Same result as the objects' own compareTo methods.**/
	@Override
	public int compare(T o1, T o2) {
		return o1.compareTo(o2);
	}

}
