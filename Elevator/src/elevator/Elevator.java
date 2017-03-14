package elevator;

import java.util.PriorityQueue;

/** This class represents an elevator and will run on its own thread.
 * @author Gustaf Peter Hultgren
 * @version 1.0.2 **/
public class Elevator extends Thread {
	/** The number of the current floor. **/
	private int mCurrentFloor;
	/** The total amount of floors, excluding floor 0. **/
	private int mFloorCount;
	/** Elevator door status. **/
	private boolean mDoorOpen;
	/** Queue of destinations. **/
	PriorityQueue<PriorityInteger> mPriorityQueue;
	/** A list of passengers onboard the elevator. **/
	PriorityQueue<Person> mPassengers;
	/** A direction flag. **/
	private boolean mUp;
	
	// TODO add a lock while this elevator is moving, and add all external calls to a separate list during this time.
	
	/** Create a new elevator.
	 * @param floorCount -The total amount of floors covered by this elevator, base floor not included. **/
	public Elevator(int floorCount) {
		mFloorCount = floorCount;
		mCurrentFloor = 0;
		mDoorOpen = false;
		mPriorityQueue = new PriorityQueue<PriorityInteger>(new PriorityComparatorSequential<PriorityInteger>());
		mPassengers = new PriorityQueue<Person>();
		mUp = true;
	}
	
	/** Run the thread. **/
	public void run() {
		System.out.println("Elevator is initialized");
		
		while(true) {
			if(mPriorityQueue.size() > 0) {
				// Are we on a requested floor?
				if(mPriorityQueue.peek().getIntegerValue() == mCurrentFloor) {
					openDoor();
					try {
						// TODO add all passengers that want off to the selected floor.
						sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					closeDoor();
				}
				else if(mUp) {
					if(isOpen())
						closeDoor();
					
					moveUp(); // Move up one floor.
				}
				else {
					if(isOpen())
						closeDoor();
					
					moveDown(); // Move down one floor.
				}
			}
			
			System.out.println("Elevator is idle");
			
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/** Close the elevator doors. **/
	public void closeDoor() {
		mDoorOpen = false;
		System.out.println("Opening doors");
	}
	
	/** Open the elevator doors. **/
	public void openDoor() {
		mDoorOpen = true;
		System.out.println("Closing doors");
	}
	
	/** Check weather the elevator doors are open.
	 * @return True if the doors are open, else false. **/
	public boolean isOpen() {
		return mDoorOpen;
	}
	
	/** Get the total amount of floors, base floor (floor 0) excluded. 
	 * @return The integer value representing the amount of floors. **/
	public int getFloorCount() {
		return mFloorCount;
	}
	
	/** Get the current floor number. 
	 * @return The integer number representing the current floor. **/
	public int getCurrentFloor() {
		return mCurrentFloor;
	}
	
	/** Move the elevator up 1 floor. **/
	private void moveUp() {
		if(mCurrentFloor < mFloorCount)
			mCurrentFloor++;
		else if(mCurrentFloor == mFloorCount)
			changeDirection();
		
		System.out.println("The elevator is now on floor " + mCurrentFloor + " and is moving up");
	}
	
	/** Move the elevator down 1 floor.**/
	private void moveDown() {
		if(mCurrentFloor > 0)
			mCurrentFloor--;
		else if(mCurrentFloor == 0)
			changeDirection();
		
		System.out.println("The elevator is now on floor " + mCurrentFloor + " and is moving down");
	}
	
	/** Externally call the elevator from a floor.
	 * @param floor -The floor from which the elevator was called. **/
	public void callElevator(int floor) {
		// Set the priority.
		int priority;
		
		if((mUp && floor > mCurrentFloor) || (!mUp && floor < mCurrentFloor))
			priority = 1; // Calls to floors along the current rout is priority 1.
		else {
			priority = 2; // Calls to floors already passed is priority 2.
			
			if(mPriorityQueue.size() < 1)
				changeDirection();
		}
		
		// Add the call to the priority queue.
		mPriorityQueue.add(new PriorityInteger(floor, priority));
		System.out.println("Elevator called to floor " + floor);
	}
	
	/** Internal call to the elevator to go to the selected floor.
	 * @param floor -The floor to which the elevator should go. **/
	private void pickFloor(int floor) {
		// Set the priority.
		int priority;
		
		if((mUp && floor > mCurrentFloor) || (!mUp && floor < mCurrentFloor))
			priority = 1; // Calls to floors along the current rout is priority 1.
		else {
			priority = 2; // Calls to floors already passed is priority 2.
			
			if(mPriorityQueue.size() < 1)
				changeDirection();
		}
		
		// Add selected value to the queue.
		mPriorityQueue.add(new PriorityInteger(floor, priority));
		System.out.println("Elevator floor selected " + floor);
	}
	
	/** Add a new passenger if there's room enough.
	 * @param person -The new passenger.
	 * @return True if there was room, false if the elevator is full. **/
	public boolean addPerson(Person person) {
		if(mPassengers.size() < 8) {
			mPassengers.add(person);
			pickFloor(person.getDestination());
			
			System.out.println(person.getName() + " entered the elevator and wants to go to floor " 
								+ person.getDestination());
			
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/** Removes the next person in queue.
	 * @return The person next in line. **/
	public Person removePerson() {
		return mPassengers.poll();
	}
	
	/** Change the current direction of the elevator. **/
	private void changeDirection() {
		// Update the new priority values.
		PriorityQueue<PriorityInteger> tempQueue = new PriorityQueue<PriorityInteger>();
		
		for(int i = 0; i < mPriorityQueue.size(); i++) {
			PriorityInteger value = mPriorityQueue.poll();
			if(value.getPriority() == 2)
				value.setPriority(1);
			
			tempQueue.add(value);
		}
		
		// Change the comparator.
		if(mUp)
			mPriorityQueue = new PriorityQueue<PriorityInteger>(new PriorityComparatorReverse<PriorityInteger>());
		else
			mPriorityQueue = new PriorityQueue<PriorityInteger>(new PriorityComparatorSequential<PriorityInteger>());
		
		for(int i = 0; i < tempQueue.size(); i++) {
			mPriorityQueue.add(tempQueue.poll());
		}
		
		mUp = !mUp;
	}
	
	/** Check weather the elevator still has some room for more passengers.
	 * 	@return The number of passengers that still can fit in the elevator. **/
	public int hasRoom() {
		return 8 - mPassengers.size();
	}
	
	/** Check the destination of the first passenger in line. 
	 * @return The floor number that this passenger is headed to, or -1 if there's no passengers. **/
	public int getDestination() {
		Person person = mPassengers.peek();
		if(person != null)
			return person.getDestination();
		else
			return -1;
	}
}
