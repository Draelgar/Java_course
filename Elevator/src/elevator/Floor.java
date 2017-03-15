package elevator;

import java.util.LinkedList;
import java.util.Queue;

/** Represents the floor of a building. 
 * @author Gustaf Peter Hultgren
 * @version 1.0 **/
public class Floor {
	/** A queue of people who wants to use the elevator. **/
	private Queue<Person> mQueue;
	/** A queue with new persons that should wait until they call the elevator. **/
	private Queue<Person> mNewQueue;
	/** A flag to stop the ceaseless spamming! **/
	private boolean mCalledElevator;
	/** The floor number. **/
	private int mFloorNumber;
	
	/** Create a new floor with the given floor number. 
	 * @param floorNumber -The number representing this floor. **/
	public Floor(int floorNumber) {
		mFloorNumber = floorNumber;
		mQueue = new LinkedList<Person>();
		mCalledElevator = false;
		mNewQueue = new LinkedList<Person>();
	}
	
	/** Tell the floor weather it has already called the elevator or not.
	 * @param status -True or false. **/
	public void call(boolean status) {
		mCalledElevator = status;
	}
	
	/** Check weather the floor has called the elevator.
	 * @return True or false. **/
	public boolean hasCalled() {
		return mCalledElevator;
	}
	
	/** Accept a new person onto the floor.
	 * @param person -The person in question. **/
	public void personEnter(Person person) {
		mNewQueue.add(person);
	}
	
	/** Update the floor by moving all new people from the entrance queue to the main queue. **/
	public void update() {
		while(!mNewQueue.isEmpty())
			mQueue.add(mNewQueue.poll());
		
		mCalledElevator = false;
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
