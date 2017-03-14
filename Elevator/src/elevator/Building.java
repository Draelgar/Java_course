package elevator;

import java.util.Random;

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
		System.out.println(name + " entered the building and wants to go to floor " + destination);
	}
	
	/** Run the building thread loop. **/
	public void run() {
		mElevator.start();
		int destination = -1;
		
		while(true) {
			for(Floor floor : mFloors) {
				destination = floor.getPersonDestination();
				if(destination >= 0) {
					if(mElevator.getCurrentFloor() != floor.getFloorNumber()) {
						mElevator.callElevator(floor.getFloorNumber());
					}
					else {
						if(!mElevator.isOpen())
							mElevator.openDoor();
						
						int room = mElevator.hasRoom();
						for(int i = 0; i < room; i++) {
							Person person = floor.personExit();
							if(person != null)
								mElevator.addPerson(person);
						}
					}
				}
			}
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/** The main entry point of this program. **/
	public static void main(String[] args) {
		Building building = new Building(8);
		
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
