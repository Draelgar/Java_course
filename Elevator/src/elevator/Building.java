package elevator;

import java.time.LocalTime;
import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/** This class represents a building with multiple floors and an elevator.
 * @author Gustaf Peter Hultgren
 * @version 1.0.2 **/
public class Building {
	/** The floors of the building **/
	private Floor mFloors[];
	/** The elevator of this building- **/
	private Elevator mElevator;
	/** A random number generator. **/
	private Random mRandom;
	/** The one and only instance of this object. **/
	private static Building mInstance = null;
	private ReadWriteLock mLock;
	
	/** Get the a reference instance of this object. 
	 * @return A reference of the instance of this object.**/
	public static Building getSingleton() {
		if(mInstance == null)
			mInstance = new Building(8);
			
		return mInstance;
	}
	
	/** Get and initialize the a reference instance of this object, or create a new one using the floorCount variable.
	 * @param floorCount -The amount of floors any new instance of this class should have. 
	 * @return A reference of the instance of this object. **/
	public static Building getSingleton(int floorCount) {
		if(mInstance == null)
			mInstance = new Building(floorCount);
		else
			mInstance.init(floorCount);
		
		return mInstance;
	}
	
	/** Create a new building with the given amount of floors.
	 * @param floorCount -The amount of floors of this building, exclusive floor 0. **/
	private Building(int floorCount) {
		mFloors = new Floor[floorCount + 1];
		for(int i = 0; i <= floorCount; i++)
			mFloors[i] = new Floor(i);
		
		mElevator = new Elevator(floorCount);
		mRandom = new Random();
		mLock = new ReentrantReadWriteLock();
	}
	
	/** Initialize a new building with the given amount of floors.
	 * @param floorCount -The amount of floors of this building, exclusive floor 0. **/
	private void init(int floorCount) {
		mFloors = new Floor[floorCount + 1];
		for(int i = 0; i <= mFloors.length; i++)
			mFloors[i] = new Floor(i);
		
		mElevator = new Elevator(floorCount);
		mRandom = new Random();
	}
	
	/** Add a new person to floor 0 that wants to use the elevator. 
	 * @param name -The name of the new person. **/
	public void addPerson(String name) {
		int destination = mRandom.nextInt(mFloors.length);
		while(destination == 0)
			destination = mRandom.nextInt(mFloors.length);
		
		synchronized(mLock.writeLock()) {
			mFloors[0].personEnter(new Person(name, destination));
		}
		
		UserInterface.getSingleton().print(name + " entered the building and wants to go to floor " + destination);
	}
	
	/** Add a new person to his/her desired floor and give him/her a new destination. 
	 * @param person -The person to add. **/
	public void addPerson(Person person) {
		int floor = person.getDestination();
		
		int destination = mRandom.nextInt(mFloors.length);
		while(destination == floor)
			destination = mRandom.nextInt(mFloors.length);
		
		person.setDestination(destination);
		
		synchronized(mLock.writeLock()) {
			mFloors[floor].personEnter(person);
		}
	}
	
	/** Ad a new person by the given name on the assigned floor. The person is given a random destination. 
	 * @param name -The name of the person.
	 * @param floor -The floor of to go to. **/
	public void addPerson(String name, int floor) {
		int destination = mRandom.nextInt(mFloors.length);
		while(destination == floor)
			destination = mRandom.nextInt(mFloors.length);
		
		synchronized(mLock.writeLock()) {
			mFloors[floor].personEnter(new Person(name, destination));
		}
		
		UserInterface.getSingleton().print(name + " entered floor " + floor + " and wants to go to floor " + destination);
	}
	
	/** Run the building thread loop. **/
	public void run() {
		for(Floor floor : mFloors)
			floor.update();
		
		int start = LocalTime.now().toSecondOfDay();
		
		// Start the elevator thread.
		mElevator.start();
		int destination = -1;
		
		while(true) {
			// Loop through each floor.
			for(Floor floor : mFloors) {
				// Check the destination of the first person in line.
				destination = floor.getPersonDestination();
				if(destination >= 0) {
					// Is the elevator not on this floor?
					if(mElevator.getCurrentFloor() != floor.getFloorNumber()) {
						if(!floor.hasCalled()) { // Make sure this floor has not yet called the elevator.
							// Call the elevator.
							mElevator.callElevator(floor.getFloorNumber());
							floor.call(true);
						}
					}
					else {
						floor.call(false);
						// Are the elevator doors closed?
						if(!mElevator.isOpen())
							mElevator.openDoor(); // Open the elevator doors so the passengers can get in.
						
						// Unless the elevator is already full, add as many people from the line as possible.
						int room = mElevator.hasRoom();
						synchronized(mLock.writeLock()) {
							for(int i = 0; i < room; i++) {
								synchronized(mLock.writeLock()) {
									Person person = floor.personExit();
									if(person != null)
										mElevator.addPerson(person);
								}
							}
						}
					}
				}
				// Update the floors every 2 seconds.
				if(start + 5 < LocalTime.now().toSecondOfDay()) {
					floor.update();
					start = LocalTime.now().toSecondOfDay();
				}
			}
		}
		
	}
	
	/** The main entry point of this program. 
	 * @param args -Any program arguments supplied at execution. **/
	public static void main(String[] args) {
		Thread consoleThread = new Thread(UserInterface.getSingleton());
		consoleThread.start();
		
		Building building = Building.getSingleton(8);
		
		building.addPerson("Lars");
		building.addPerson("Peter");
		building.addPerson("Erick");
		building.addPerson("Torsten");
		building.addPerson("Eva");
		building.addPerson("Maria");
		building.addPerson("Lisa");
		building.addPerson("Berit");
		building.addPerson("Sara");
		
		building.run();
	}
}
