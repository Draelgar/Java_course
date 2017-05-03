package collectionShellPackage;

import java.util.Map;
import java.util.Scanner;

public class UserInterface {
	
	// Get input from the user.
	public static String getUserInput() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		return scanner.nextLine();
	}
	
	// Show the menu.
	public static MenuChoise printMenu() {
		while(true) {
			System.out.println("Select what to do:\n-Assign a new array (assign)\n-Remove duplicates from the array(duplicates)\n" 
							+ "-Remove duplicates using a set (set)\n-Add a value at the end of the array (add)\n" 
							+ "-Remove a value from the array (remove)\n-Combine an array of keys and an array of strings into a map (combine)\n" 
							+ "-Exit the application. (exit)");
			
			String response = getUserInput();
			if(response.equalsIgnoreCase("assign"))
				return MenuChoise.ASSIGN;
			else if(response.equalsIgnoreCase("duplicates"))
				return MenuChoise.DUPLICATES;
			else if(response.equalsIgnoreCase("set"))
				return MenuChoise.SET;
			else if(response.equalsIgnoreCase("add"))
				return MenuChoise.ADD;
			else if(response.equalsIgnoreCase("remove"))
				return MenuChoise.REMOVE;
			else if(response.equalsIgnoreCase("combine"))
				return MenuChoise.COMBINE;
			else if(response.equalsIgnoreCase("exit"))
				return MenuChoise.EXIT;
			
			System.out.println("The given option does not exist, please try again.\n");
		}
	}
	
	public static String[] getStrings() {
		System.out.println("Assign the array of strings you want on the form \"a,b,c,d,........,n\"");
		
		String response = getUserInput().trim();
		
		String strings[] = response.split(",");
		
		return strings;
	}
	
	// Get a single integer value.
	public static int getInt() {
		String response = getUserInput().trim();
		
		try {
			return Integer.parseInt(response);
		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
		}
		
		return 0;
	}
	
	// Get a new array.
	public static int[] getInts() {		
		String response = getUserInput().trim();
		
		String strings[] = response.split(",");
		int array[] = new int[strings.length];
		
		try {
			for(int i = 0; i < strings.length; i++)
				array[i] = Integer.parseInt(strings[i]);
		} catch(NumberFormatException e) {
			System.out.println(e.getMessage());
		}
		
		return array;
	}
	
	// Print the string to the console.
	public static void println(String string) {
		System.out.println(string);
	}
	
	// Print the contents of an array.
	public static void printArray(int[] array) {
		for(int i : array){
			System.out.println(i);
		}
	}
	
	// print the contents of a map.
	public static void printMap(Map<Integer, String> map, int[] keys) {
		for(int key : keys)
			System.out.println(key + ", " + map.get(key));
	}
}
