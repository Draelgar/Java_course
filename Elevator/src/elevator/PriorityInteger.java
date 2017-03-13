package elevator;

/** A container containing an integer value with a priority value. **/
public class PriorityInteger implements PriorityData<PriorityInteger>{
	private int mInteger;
	private int mPriority;
	
	/** Create a new prioritized integer value.
	 * @param integer -The integer value itself.
	 * @param priority -An estimate about the importance of the value. **/
	PriorityInteger(int integer, int priority) {
		mInteger = integer;
		mPriority = priority;
	}
	
	/** Compare the priority of two PriorityInteger objects. 
	 * @param o -The object to compare to.
	 * @return 0 if both are equal, 1 if this goes before o, -1 if o goes before this.**/
	@Override
	public int compareTo(PriorityInteger o) {
		if(mPriority == o.getPriority()) {
			if(mInteger == o.getIntegerValue())
				return 0;
			else if(mInteger < o.getIntegerValue())
				return 1;
			else
				return 0;
		}
		else if(mPriority < o.getPriority())
			return 1;
		else
			return -1;
	}
	
	/** Get the integer value.
	 * @return The integer value. **/
	public int getIntegerValue() {
		return mInteger;
	}
	
	/** Get the priority of this value.
	 * @return The priority level of this object. **/
	@Override
	public int getPriority() {
		return mPriority;
	}
	
	/** Set the priority of this object.
	 * @param priority -The new priority value. **/
	public void setPriority(int priority) {
		mPriority = priority;
	}

	/** Get the data. 
	 * @return An Integer containing the data. **/
	@Override
	public int getData() {
		return mInteger;
	}

}
