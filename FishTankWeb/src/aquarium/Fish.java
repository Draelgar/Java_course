package aquarium;
/**
 * 
 */


/** This interface is a simple container for information about a fish.
 * @author Gustaf Peter Hultgren 
 * @version 1.0 **/
public interface Fish {
	/** Get the nick name of the fish. **/
	public String name();
	/** Rename this fish. **/
	public void rename(String newName);
	/** Is this fish a male? **/
	public boolean isMale();
	/** Get the age if the fish. **/
	public int age();
	/** Make the fish older. **/
	public void addAge(int additionalYears);
	/** Get the type of fish. **/
	public FishType fishType();
	/** Get the classification of this fish. **/
	public FishClass fishClass();
	/** Get the weight of this fish in kilograms. **/
	public double weight();
	/** Add a few kilograms to the weight of this fish. **/
	public void addWeight(double additionalKilograms);
	/** Get the length of this fish in meters. **/
	public double length();
	/** Add some meters to this fish's length. **/
	public void addLength(double additionalMeters);
	/** Calculates an index unique to this setup of variables. **/
	public int calculateIndex();
}
