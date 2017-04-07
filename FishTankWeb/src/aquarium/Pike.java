/**
 * 
 */
package aquarium;

/**
 * @author Gustaf Peter Hultgren 
 * @version 1.0 **/
public class Pike extends FishBase {

	/** Create a new Pike.
	 * @param type
	 * @param name
	 * @param weight
	 * @param length
	 * @param age
	 * @param male
	 * @param classification
	 */
	public Pike(String name, double weight, double length, int age, boolean male) {
		super(FishType.PIKE, name, weight, length, age, male, FishClass.PREDATOR);
	}

}
