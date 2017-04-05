/**
 * 
 */
package aquarium;

/**
 * @author Gustaf Peter Hultgren **/
public class Shark extends PredatorFish {

	/** Create a new shark.
	 * @param name -The nick name of the shark.
	 * @param weight -The weight in kilograms.
	 * @param length -The length in meters.
	 * @param age -The age in years.
	 * @param male -Weather or not this shark is a male. **/
	public Shark(String name, double weight, double length, int age, boolean male) {
		super(FishType.SHARK, name, weight, length, age, male);
	}

}
