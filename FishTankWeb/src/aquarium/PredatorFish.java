/**
 * 
 */
package aquarium;

/**
 * @author Gustaf Peter Hultgren **/
public abstract class PredatorFish extends FishBase	{
	/** Create a new predator fish.
	 * @param type -The type of fish.
	 * @param name -The nick name of the shark.
	 * @param weight -The weight in kilograms.
	 * @param length -The length in meters.
	 * @param age -The age in years.
	 * @param male -Weather or not this shark is a male. **/
	public PredatorFish(FishType type, String name, double weight, double length, 
			int age, boolean male) {
		super(type, name, weight, length, age, male, FishClass.PREDATOR);
	}
}
