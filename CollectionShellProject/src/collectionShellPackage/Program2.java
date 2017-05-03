package collectionShellPackage;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Program2<T,S>{	
	public static void main(String[] args) {
		Program2<Integer, String> program = new Program2<Integer,String>();
		
		int value = 0;
		Integer baseArray[] = {1,3,3,4,5};
		String baseStringsArray[] = {"One", "Three", "Four", "Five"};
		
		while(true) {
			switch(UserInterface2.printMenu()) {
				case ASSIGN:
				{
					UserInterface.println("Give the list of ints to use (\"a,b,c,d,........,n\"");
					baseArray = UserInterface2.getInts();
					break;
				}
				case DUPLICATES:
				{
					UserInterface2.printArray(baseArray);
					UserInterface2.println("Duplicates Above-------------No Duplicates Below");				
					baseArray = program.removeDuplicates(Integer.class, baseArray);
					UserInterface2.printArray(baseArray);
					
					break;
				}
				case SET:
				{
					UserInterface2.printArray(baseArray);
					UserInterface2.println("Duplicates Above-------------No Duplicates Below");				
					
					baseArray = program.removeDuplicatesSet(Integer.class, baseArray);
					UserInterface2.printArray(baseArray);
					
					break;
				}
				case ADD:
				{
					UserInterface2.println("Give the integer value to add");
					value = UserInterface.getInt();
					
					UserInterface2.printArray(baseArray);
					UserInterface2.println("Before Above-------------After Below");
					UserInterface2.println("Add " + value + " at the end of the list.");
					baseArray = program.expandableAdd(Integer.class, baseArray, value);
					UserInterface2.printArray(baseArray);
					
					break;
				}
				case REMOVE:
				{
					UserInterface.println("Give the integer value to remove");
					value = UserInterface2.getInt();
					
					UserInterface2.printArray(baseArray);
					UserInterface2.println("Before Above-------------After Below");
					UserInterface2.println("Remove " + value + " from the list.");
					baseArray = program.shrinkingRemove(Integer.class, baseArray, value);
					UserInterface2.printArray(baseArray);
					
					break;
				}
				case COMBINE:
				{	
					UserInterface2.println("Give the list of keys to use (\"a,b,c,d,........,n\"");
					baseArray = UserInterface2.getInts();
					
					UserInterface2.println("Give the list of strings to use (\"a,b,c,d,........,n\"");
					baseStringsArray = UserInterface.getStrings();
					
					UserInterface2.println("Created a map using the given arrays.");				
					Map<Integer, String> map = program.combineArrays(baseArray, baseStringsArray);
					UserInterface2.printMap(map,  baseArray);
					
					break;
				}
				case EXIT:
				{
					// Exit the application.
					return;
				}
				default:
				{
					break;
				}
			}
		}
	}
	
	//This method removes any duplicate values from an inputed array of Integers.
	@SuppressWarnings("unchecked")
	public T[] removeDuplicates(Class<T> genericClass, T[] array){
		T[] outArray = (T[])Array.newInstance(genericClass, 0);
		for(int i = 0; i < array.length; i++){
			T current = array[i];
			boolean exists = false;
			for(int j = 0; j < outArray.length; j++){
				if(current == outArray[j]){
					exists = true;
				}
			}
			if(!exists){
				T[]temp = (T[])Array.newInstance(genericClass, outArray.length + 1);
				System.arraycopy(outArray, 0, temp, 0, outArray.length);
				temp[outArray.length] = current;
				outArray = temp;
			}
		}
		
		return outArray;
	}
	
	//This method removes any duplicate values from an inputed array of integers using a Set
	public T[] removeDuplicatesSet(Class<T> type, T[] array){
		//Use the collection type "Set" to remove the duplicates.
		Set<T> set = new HashSet<T>();
		
		// Add all values from the array to the set, which by its nature will remove duplicates.
		for(int i = 0; i < array.length; i++)
			set.add(array[i]);
		
		// Convert the Set to array.
		@SuppressWarnings("unchecked")
		T[] tempArray = (T[])Array.newInstance(type, 0);
		return set.toArray(tempArray);
	}
	
	public T[] expandableAdd(Class<T> type, T[] targetArray, T value){
		//Use the collection type "List" to do this.
		List<T> list = new LinkedList<T>();
		
		// Add the array to the list.
		for(int i = 0; i < targetArray.length; i++) {
			list.add(targetArray[i]);
		}
		
		// Add the last value.
		list.add(value);
		
		// Convert the List back to Integer array.
		@SuppressWarnings("unchecked")
		T[] array = (T[])Array.newInstance(type, 0);
		return list.toArray(array);
	}
	
	@SuppressWarnings("unchecked")
	public T[] shrinkingRemove(Class<T> type, T[] targetArray, T value){
		//Use the collection type "List" to do this.
		List<T> list = new LinkedList<T>();
		
		// Add the array to the list.
		for(int i = 0; i < targetArray.length; i++) {
			list.add(targetArray[i]);
		}
		
		// Get the list index of the first appearance of the selected value and remove it.
		int index = list.indexOf(value);
		
		if(index >= 0)
			list.remove(index);
		
		T[] array = (T[]) Array.newInstance(type, targetArray.length - 1);
		// Convert the List to Integer array.
		return list.toArray(array);
	}
	
	public Map<T, S> combineArrays(T[] keys, S[] values) {
		assert(keys.length == values.length); // Assume that the arrays are of equal length.
		
		// Initialize the map.
		Map<T, S> map = new HashMap<T, S>();
		
		// Insert all the values of the map.
		for(int i = 0; i < keys.length; i++)
			map.put(keys[i], values[i]);
		
		// Return the map.
		return map;
	}
}
