/**
 * 
 */
package aquarium;

/**
 * @author Gustaf Peter Hultgren **/
public class Eel extends PredatorFish {

	/** Create a new eel.
	* @param name -The nick name.
	 * @param weight -The weight in kilograms.
	 * @param length -The length in meters.
	 * @param age -The age in years.
	 * @param male -Weather or not this eel is a male. **/
	public Eel(String name, double weight, double length, int age, boolean male) {
		super(FishType.EEL, name, weight, length, age, male);
		// TODO Auto-generated constructor stub
	}

}
