package elevator;

import java.util.PriorityQueue;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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
	/** A read & write lock for floor changes. **/
	private ReadWriteLock mFloorLock;
	/** A lock for the passenger queue. **/
	private ReadWriteLock mPassengerLock;
	/** A lock for the integer queue. **/
	private ReadWriteLock mQueueLock;
	/** A lock for the doors. **/
	private ReadWriteLock mDoorLock;
	
	/** Create a new elevator.
	 * @param floorCount -The total amount of floors covered by this elevator, base floor not included. **/
	public Elevator(int floorCount) {
		mFloorLock = new ReentrantReadWriteLock();
		mFloorCount = floorCount;
		mCurrentFloor = 0;
		mDoorOpen = false;
		mPriorityQueue = new PriorityQueue<PriorityInteger>(new PriorityComparatorSequential<PriorityInteger>());
		mPassengers = new PriorityQueue<Person>(new PriorityComparatorSequential<Person>());
		mUp = true;
		mPassengerLock = new ReentrantReadWriteLock();
		mQueueLock = new ReentrantReadWriteLock();
		mDoorLock = new ReentrantReadWriteLock();
	}
	
	/** Run the thread. **/
	public void run() {
		UserInterface.getSingleton().print("Elevator is initialized");
		
		while(true) {
			synchronized(mQueueLock.readLock()) {
				if(!mPriorityQueue.isEmpty()) {
					// Are we on a requested floor?
					if(mPriorityQueue.peek().getIntegerValue() == mCurrentFloor) {
						while(!mPriorityQueue.isEmpty() && mPriorityQueue.peek().getIntegerValue() == mCurrentFloor)
							mPriorityQueue.poll();
						
						openDoor();
			
						ejectPassengers();
						
						try {
							sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
					}
					else {
						if(isOpen())
							closeDoor();
						
						move(); // Move one floor.
					}
				}
				else {
					UserInterface.getSingleton().print("Elevator is idle");
					synchronized(mPassengerLock.readLock()) {
						if(!mPassengers.isEmpty())
							selectFloor(mPassengers.peek().getDestination());
					}
				}
			}
			
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/** Force the passengers to leave the elevator once their floor is reached. **/
	public void ejectPassengers() {
		synchronized(mPassengerLock.writeLock()) {	
			while(!mPassengers.isEmpty()) {
				if(mPassengers.peek().getDestination() == mCurrentFloor) {
					// Evacuate the elevator for everyone that wants to get off.
					UserInterface.getSingleton().print(mPassengers.peek().getName() + " got off at floor " + mCurrentFloor);
					Building.getSingleton().addPerson(mPassengers.poll());
				}
				else
					break;
			}
		}
	}
	
	/** Close the elevator doors. **/
	public void closeDoor() {
		synchronized(mDoorLock.writeLock()) {
			mDoorOpen = false;
			UserInterface.getSingleton().print("Closing doors");
		}

	}
	
	/** Open the elevator doors. **/
	public void openDoor() {
		synchronized(mDoorLock.writeLock()) {
			mDoorOpen = true;
			UserInterface.getSingleton().print("Opening doors");
		}
	}
	
	/** Check weather the elevator doors are open.
	 * @return True if the doors are open, else false. **/
	public boolean isOpen() {
		synchronized(mDoorLock.readLock()) {
			return mDoorOpen;
		}
	}
	
	/** Get the total amount of floors, base floor (floor 0) excluded. 
	 * @return The integer value representing the amount of floors. **/
	public int getFloorCount() {
		synchronized(mFloorLock.readLock()) {
			return mFloorCount;
		}
	}
	
	/** Get the current floor number. 
	 * @return The integer number representing the current floor. **/
	public int getCurrentFloor() {
		synchronized(mFloorLock.readLock()) {
			return mCurrentFloor;
		}
	}
	
	/** Move the elevator 1 floor. **/
	private void move() {
		synchronized(mFloorLock.writeLock()) {
			if(mUp && mCurrentFloor < mFloorCount) {
				mCurrentFloor++;
				synchronized(mQueueLock.readLock()) {
					if(mCurrentFloor == mFloorCount 
							|| (!mPriorityQueue.isEmpty() 
									&& mCurrentFloor > mPriorityQueue.peek().getIntegerValue()))
						changeDirection();
				}
				
				UserInterface.getSingleton().print("The elevator is now on floor " + mCurrentFloor + " and is moving up");
			}
			else if(!mUp && mCurrentFloor > 0) {
				mCurrentFloor--;
				synchronized(mQueueLock.readLock()) {
					if(mCurrentFloor == 0 
							|| (!mPriorityQueue.isEmpty() 
									&& mCurrentFloor < mPriorityQueue.peek().getIntegerValue()))
						changeDirection();
				}
				
				UserInterface.getSingleton().print("The elevator is now on floor " + mCurrentFloor + " and is moving down");
			}
		}
	}
	
	
	/** Externally call the elevator from a floor.
	 * @param floor -The floor from which the elevator was called. **/
	public void callElevator(int floor) {	
			// Set the priority.
			int priority;
			
			synchronized(mFloorLock.readLock()) {
				synchronized(mQueueLock.readLock()) {
					if((mUp && floor > mCurrentFloor && !mPriorityQueue.isEmpty() && floor < mPriorityQueue.peek().getIntegerValue()) 
							|| (!mUp && floor < mCurrentFloor && !mPriorityQueue.isEmpty() && floor > mPriorityQueue.peek().getIntegerValue()))
						priority = 1; // Calls to floors along the current rout is priority 1.
					else {
						priority = 2; // Calls to floors already passed is priority 2.
						
						if(mPriorityQueue.size() < 1)
							changeDirection();
					}
				}
				
				synchronized(mQueueLock.writeLock()) {
					// Add the call to the priority queue.
					mPriorityQueue.add(new PriorityInteger(floor, priority));
					UserInterface.getSingleton().print("Elevator called to floor " + floor);
				}
			}
	}
	
	/** Externally call the elevator from a floor.
	 * @param floor -The floor from which the elevator was called. **/
	private void selectFloor(int floor) {	
			// Set the priority.
			int priority;
			
			synchronized(mFloorLock.readLock()) {
				synchronized(mQueueLock.readLock()) {
					if((mUp && floor > mCurrentFloor) || (!mUp && floor < mCurrentFloor))
						priority = 1; // Calls to floors along the current rout is priority 1.
					else {
						priority = 2; // Calls to floors already passed is priority 2.
						
						if(mPriorityQueue.size() < 1)
							changeDirection();
					}
				}
				
				synchronized(mQueueLock.writeLock()) {
					// Add the call to the priority queue.
					mPriorityQueue.add(new PriorityInteger(floor, priority));
					UserInterface.getSingleton().print("Elevator sent to floor " + floor);
				}
			}

	}
	
	/** Add a new passenger if there's room enough.
	 * @param person -The new passenger.
	 * @return True if there was room, false if the elevator is full. **/
	public boolean addPerson(Person person) {
		synchronized(mPassengerLock.writeLock()) {
			if(mPassengers.size() < 8) {
				mPassengers.add(person);
				selectFloor(person.getDestination());
				
				UserInterface.getSingleton().print(person.getName() + " entered the elevator and wants to go to floor " 
									+ person.getDestination());
				
				return true;
			}
			else
			{
				UserInterface.getSingleton().print("Elevator is full, no more space.");
				return false;
			}
		}
	}
	
	/** Removes the next person in queue.
	 * @return The person next in line. **/
	public Person removePerson() {
		synchronized(mPassengerLock.writeLock()) {
			return mPassengers.poll();
		}
	}
	
	/** Change the current direction of the elevator. **/
	private void changeDirection() {
		synchronized(mPassengerLock.writeLock()) {
			synchronized(mQueueLock.writeLock()) {
				// Update the new priority values.
				PriorityQueue<PriorityInteger> tempIntQueue = new PriorityQueue<PriorityInteger>();
				PriorityQueue<Person> tempPersonQueue = new PriorityQueue<Person>();
				
				while(!mPriorityQueue.isEmpty()) {
					PriorityInteger value = mPriorityQueue.poll();
					if(value.getPriority() == 2)
						value.setPriority(1);
					
					tempIntQueue.add(value);
				}
				
				while(!mPassengers.isEmpty()) {
					tempPersonQueue.add(mPassengers.poll());
				}
				
				// Change the comparator.
				if(mUp) {
					mPriorityQueue = new PriorityQueue<PriorityInteger>(new PriorityComparatorReverse<PriorityInteger>());
					mPassengers = new PriorityQueue<Person>(new PriorityComparatorReverse<Person>());
				}
				else {
					mPriorityQueue = new PriorityQueue<PriorityInteger>(new PriorityComparatorSequential<PriorityInteger>());
					mPassengers = new PriorityQueue<Person>(new PriorityComparatorSequential<Person>());
				}
				
				while(!tempIntQueue.isEmpty()) {
					mPriorityQueue.add(tempIntQueue.poll());
				}
				while(!tempPersonQueue.isEmpty()) {
					mPassengers.add(tempPersonQueue.poll());
				}
			}
		}
		
		synchronized(mFloorLock.writeLock()) {
			mUp = !mUp;
		}
	}
	
	/** Check weather the elevator still has some room for more passengers.
	 * 	@return The number of passengers that still can fit in the elevator. **/
	public int hasRoom() {
		synchronized(mPassengerLock.readLock()) {
			return 8 - mPassengers.size();
		}
	}
	
	/** Check the destination of the first passenger in line. 
	 * @return The floor number that this passenger is headed to, or -1 if there's no passengers. **/
	public int getDestination() {
		synchronized(mPassengerLock.readLock()) {
			Person person = mPassengers.peek();
			if(person != null)
				return person.getDestination();
			else
				return -1;
		}
	}
}
