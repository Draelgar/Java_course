package elevator;

public interface PriorityData<T> extends Comparable<T>{
	public int getData();
	public int getPriority();
}
