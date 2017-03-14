package elevator;

import java.util.Random;

/** This class represents a building with multiple floors and an elevator.
 * @author Gustaf Peter Hultgren
 * @version 1.0.1 **/
public class Building {
	/** The floors of the building **/
	private Floor mFloors[];
	/** The elevator of this building- **/
	private Elevator mElevator;
	/** A random number generator. **/
	private Random mRandom;
	
	/** Create a new building with the given amount of floors.
	 * @param floorCount -The amount of floors of this building, exclusive floor 0. **/
	public Building(int floorCount) {
		mFloors = new Floor[floorCount + 1];
		for(int i = 0; i <= floorCount; i++)
			mFloors[i] = new Floor(i);
		
		mElevator = new Elevator(floorCount);
		mRandom = new Random();
	}
	
	/** Add a new person to floor 0 that wants to use the elevator. 
	 * @param name -The name of the new person. **/
	public void addPerson(String name) {
		int destination = mRandom.nextInt(mFloors.length) + 1;
		mFloors[0].personEnter(new Person(name, destination));
	}
	
	/** Run the building thread loop. **/
	public void run() {
		mElevator.start();
		int destination = -1;
		
		while(true) {
			for(Floor floor : mFloors) {
				destination = floor.getPersonDestination();
				if(destination >= 0) {
					// TODO:
					// - call elevator to floor.getFloorNumber();
					// - Load all people (max 8) onto the elevator once the doors open.
				}
			}
		}
		
	}
}
