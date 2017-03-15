package elevator;

/** This class represents a parson.
 * @author Gustaf Peter Hultgren
 * @version 1.1 **/
public class Person implements PriorityData<Person> {
	/** The name of the person. **/
	private String mName;
	/** The destination of the person. **/
	private int mDestination;
	
	/** Create a new person with a destination floor. 
	 * @param name -The name of the person.
	 * @param floor -The destination floor. **/
	public Person(String name, int floor) {
		mName = name;
		mDestination = floor;
	}
	
	/** Get the destination of this person.
	 * @return The destination floor of this person. **/
	public int getDestination() {
		return mDestination;
	}
	
	/** Set the destination of this person.
	 * @param destination -The destination floor of this person. **/
	public void setDestination(int destination) {
		mDestination = destination;
	}
	
	/** Get the name of this person.
	 * @return The name of this person. **/
	public String getName() {
		return mName;
	}

	/** Compare to another person.
	 * @param o -The object to compare to.
	 * @return 0 if both persons are equally important,
	 *  1 if this person goes before person o,
	 *  -1 if person o goes before this person **/
	@Override
	public int compareTo(Person o) {
		
		if(o.getDestination() == mDestination)
			return 0;
		else if(mDestination < o.getDestination())
			return 1;
		else
			return -1;
		
	}

	/** Return the destination. **/
	@Override
	public int getData() {
		return mDestination;
	}

	/** Return priority 1. **/
	@Override
	public int getPriority() {
		return 1;
	}
}
