/**
 * 
 */
package aquarium;

/**
 * @author Gustaf Peter Hultgren **/
public abstract class FishBase implements Fish{
	protected FishType mType;
	protected String mNickName;
	protected double mWeight;
	protected double mLength;
	protected int mAge;
	protected boolean mMale;
	protected FishClass mClass;
	
	/** Create a new fish.
	 * @param type -The type of fish.
	 * @param name -The nick name of the shark.
	 * @param weight -The weight in kilograms.
	 * @param length -The length in meters.
	 * @param age -The age in years.
	 * @param male -Weather or not this shark is a male. 
	 * @param classification -The classification of this fish. **/
	public FishBase(FishType type, String name, double weight, double length, 
				int age, boolean male, FishClass classification) {
		mType =type;
		mNickName = name;
		mWeight = weight;
		mLength = length;
		mAge = age;
		mMale = male;
		mClass = classification;
	}
	
	/** Get the nick name of the fish. **/
	public String name() {
		return mNickName;
	}
	/** Rename this fish. **/
	public void rename(String newName) {
		mNickName = newName;
	}
	/** Is this fish a male? **/
	public boolean isMale() {
		return mMale;
	}
	/** Get the age if the fish. **/
	public int age() {
		return mAge;
	}
	/** Make the fish older. **/
	public void addAge(int additionalYears) {
		mAge += additionalYears;
	}
	/** Get the type of fish. **/
	public FishType fishType() {
		return mType;
	}
	/** Get the classification of this fish. **/
	public FishClass fishClass() {
		return mClass;
	}
	/** Get the weight of this fish in kilograms. **/
	public double weight() {
		return mWeight;
	}
	/** Add a few kilograms to the weight of this fish. **/
	public void addWeight(double additionalKilograms) {
		mWeight += additionalKilograms;
	}
	/** Get the length of this fish in meters. **/
	public double length() {
		return mLength;
	}
	/** Add some meters to this fish's length. **/
	public void addLength(double additionalMeters) {
		mLength += additionalMeters;
	}
}
