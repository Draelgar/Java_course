/**
 * 
 */
package aquarium;

import java.util.ArrayList;

/**
 * @author Gustaf Peter Hultgren **/
public class Aquarium {
	private ArrayList<Fish> mFishes;
	
	public Aquarium() {
		mFishes = new ArrayList<Fish>();
	}
	
	public void addFish(Fish fish) {
		mFishes.add(fish);
	}
	
	public void printFish() {
		for(Fish fish : mFishes)
			System.out.println(fish.name());
	}
	
	public static void main(String args[]) {
		Aquarium fishtank = new Aquarium();
		fishtank.addFish(new Shark("Sharky", 5.3, 1.5, 6, true));
		fishtank.addFish(new Eel("Sparky", 5.3, 1.5, 6, false));
		fishtank.printFish();
	}
}
