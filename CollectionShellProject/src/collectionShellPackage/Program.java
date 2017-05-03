package collectionShellPackage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Program{
	public static void main(String[] args) {
		Program program = new Program();
		program.run();
	}

	// Run the application.
	public void run() {
		int value = 0;
		int baseArray[] = {1,3,3,4,5};
		String baseStringsArray[] = {"One", "Three", "Four", "Five"};
		
		while(true) {
			switch(UserInterface.printMenu()) {
				case ASSIGN:
				{
					UserInterface.println("Give the list of ints to use (\"a,b,c,d,........,n\"");
					baseArray = UserInterface.getInts();
					break;
				}
				case DUPLICATES:
				{
					UserInterface.printArray(baseArray);
					UserInterface.println("Duplicates Above-------------No Duplicates Below");				
					baseArray = removeDuplicates(baseArray);
					UserInterface.printArray(baseArray);
					
					break;
				}
				case SET:
				{
					UserInterface.printArray(baseArray);
					UserInterface.println("Duplicates Above-------------No Duplicates Below");				
					baseArray = removeDuplicatesSet(baseArray);
					UserInterface.printArray(baseArray);
					
					break;
				}
				case ADD:
				{
					UserInterface.println("Give the integer value to add");
					value = UserInterface.getInt();
					
					UserInterface.printArray(baseArray);
					UserInterface.println("Before Above-------------After Below");
					UserInterface.println("Add " + value + " at the end of the list.");
					baseArray = expandableAdd(baseArray, value);
					UserInterface.printArray(baseArray);
					
					break;
				}
				case REMOVE:
				{
					UserInterface.println("Give the integer value to remove");
					value = UserInterface.getInt();
					
					UserInterface.printArray(baseArray);
					UserInterface.println("Before Above-------------After Below");
					UserInterface.println("Remove " + value + " from the list.");
					baseArray = shrinkingRemove(baseArray, value);
					UserInterface.printArray(baseArray);
					
					break;
				}
				case COMBINE:
				{	
					UserInterface.println("Give the list of keys to use (\"a,b,c,d,........,n\"");
					baseArray = UserInterface.getInts();
					
					UserInterface.println("Give the list of strings to use (\"a,b,c,d,........,n\"");
					baseStringsArray = UserInterface.getStrings();
					
					UserInterface.println("Created a map using the given arrays.");				
					Map<Integer, String> map = combineArrays(baseArray, baseStringsArray);
					UserInterface.printMap(map,  baseArray);
					
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
	public int[] removeDuplicates(int[] arrayWithDuplicates){
		int[] arrayWithoutDuplicates = new int[0];
		for(int i = 0; i < arrayWithDuplicates.length; i++){
			int current = arrayWithDuplicates[i];
			boolean exists = false;
			for(int j = 0; j < arrayWithoutDuplicates.length; j++){
				if(current == arrayWithoutDuplicates[j]){
					exists = true;
				}
			}
			if(!exists){
				int[]temp = new int[arrayWithoutDuplicates.length + 1];
				System.arraycopy(arrayWithoutDuplicates, 0, temp, 0, arrayWithoutDuplicates.length);
				temp[arrayWithoutDuplicates.length] = current;
				arrayWithoutDuplicates = temp;
			}
		}
		return arrayWithoutDuplicates;
	}
	
	//This method removes any duplicate values from an inputed array of integers using a Set
	public int[] removeDuplicatesSet(int[] arrayWithDuplicates){
		//Use the collection type "Set" to remove the duplicates.
		Set<Integer> set = new HashSet<Integer>();
		
		// Add all values from the array to the set, which by its nature will remove duplicates.
		for(int i = 0; i < arrayWithDuplicates.length; i++)
			set.add(arrayWithDuplicates[i]);
		
		// Convert the Set to Integer array.
		Integer integerArrayWithoutDuplicates[] = new Integer[0];
		integerArrayWithoutDuplicates = set.toArray(integerArrayWithoutDuplicates);
		
		// Convert the Integer array to int array.
		int arrayWithoutDuplicates[] = new int[integerArrayWithoutDuplicates.length];		
		for(int i = 0; i < integerArrayWithoutDuplicates.length; i++)
			arrayWithoutDuplicates[i] = integerArrayWithoutDuplicates[i];
		
		// Return duplicates free int array.
		return arrayWithoutDuplicates;
	}
	
	public int[] expandableAdd(int[] targetArray, int value){
		//Use the collection type "List" to do this.
		List<Integer> list = new LinkedList<Integer>();
		
		// Add the array to the list.
		for(int i = 0; i < targetArray.length; i++) {
			list.add(targetArray[i]);
		}
		
		// Add the last value.
		list.add(value);
		
		// Convert the List to Integer array.
		Integer integerArray[] = new Integer[0];
		integerArray = list.toArray(integerArray);
		
		// Convert the Integer array to int array.
		int expandedArray[] = new int[integerArray.length];		
		for(int i = 0; i < integerArray.length; i++)
			expandedArray[i] = integerArray[i];
		
		return expandedArray;
	}
	
	public int[] shrinkingRemove(int[] targetArray, int value){
		//Use the collection type "List" to do this.
		List<Integer> list = new LinkedList<Integer>();
		
		// Add the array to the list.
		for(int i = 0; i < targetArray.length; i++) {
			list.add(targetArray[i]);
		}
		
		// Get the list index of the first appearance of the selected value and remove it.
		int index = list.indexOf(value);
		
		if(index >= 0)
			list.remove(index);
		
		// Convert the List to Integer array.
		Integer integerArray[] = new Integer[0];
		integerArray = list.toArray(integerArray);
		
		// Convert the Integer array to int array.
		int shrunkArray[] = new int[integerArray.length];		
		for(int i = 0; i < integerArray.length; i++)
			shrunkArray[i] = integerArray[i];
		
		return shrunkArray;
	}
	
	public Map<Integer, String> combineArrays(int[] keys, String [] values){
		assert(keys.length == values.length); // Assume that the arrays are of equal length.
		
		// Initialize the map.
		Map<Integer, String> map = new HashMap<Integer, String>();
		
		// Insert all the values of the map.
		for(int i = 0; i < keys.length; i++)
			map.put(keys[i], values[i]);
		
		// Return the map.
		return map;
	}

}
