package aquarium;

import aquarium.FishBase;
import aquarium.FishClass;
import aquarium.FishType;

/**
 * 
 */

/**
 * @author Gustaf Peter Hultgren 
 * @version 1.0 **/
public class Clownfish extends FishBase {

	/** Create a new Clown fish.
	 * @param type
	 * @param name
	 * @param weight
	 * @param length
	 * @param age
	 * @param male
	 * @param classification
	 */
	public Clownfish(String name, double weight, double length, int age, boolean male) {
		super(FishType.CLOWNFISH, name, weight, length, age, male, FishClass.HERBIVORE);
	}

}
