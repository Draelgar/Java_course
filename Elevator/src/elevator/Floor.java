package elevator;

import java.util.LinkedList;
import java.util.Queue;

/** Represents the floor of a building. 
 * @author Gustaf Peter Hultgren
 * @version 1.0 **/
public class Floor {
	/** A queue of people who wants to use the elevator. **/
	private Queue<Person> mQueue;
	/** The floor number. **/
	private int mFloorNumber;
	
	/** Create a new floor with the given floor number. 
	 * @param floorNumber -The number representing this floor. **/
	public Floor(int floorNumber) {
		mFloorNumber = floorNumber;
		mQueue = new LinkedList<Person>();
	}
	
	/** Accept a new person onto the floor.
	 * @param person -The person in question. **/
	public void personEnter(Person person) {
		mQueue.add(person);
	}
	
	/** Let the next person in line leave the floor. 
	 * @return The first person in line. **/
	public Person personExit() {
		return mQueue.poll();
	}
	
	/** Check where the first person in line is headed to.
	 * @return The floor number for the destination or -1 if there are no people on this floor. **/
	public int getPersonDestination() {
		Person person = mQueue.peek();
		
		if(person != null)
			return person.getDestination();
		else
			return -1;
	}
	
	/** Get the number associated with this floor.
	 * @return The floor number. **/
	public int getFloorNumber() {
		return mFloorNumber;
	}
}
