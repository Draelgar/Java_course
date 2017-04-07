/**
 * 
 */
package aquarium;

/**
 * @author Gustaf Peter Hultgren 
 * @Version 1.0 **/
public class Bass extends FishBase {

	/** Create a new Bass.
	 * @param name
	 * @param weight
	 * @param length
	 * @param age
	 * @param male
	 */
	public Bass(String name, double weight, double length, int age, boolean male) {
		super(FishType.BASS, name, weight, length, age, male, FishClass.HERBIVORE);
	}

}
