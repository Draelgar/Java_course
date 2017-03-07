package shopPackage;

import java.util.Scanner;

public class UserInterface {
	
	// Print the given string and end with line break.
	public static void println(String s) {
		System.out.println(s);
	}
	
	// Get an input string from the user.
	public static String getUserInput() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		return scanner.nextLine();
	}
	
	// Get a long integer from the user.
	public static long getUserInputLong() {
		String s = getUserInput();
		
		while(true) {
			try {
				return Long.parseLong(s);
			} catch(NumberFormatException e) {
				System.out.println(s + " is not a valid integer number, please try again.");
			}
		}
	}
	
	// Display the main menu.
	public static AppMode showMainMenu() {
		while(true) {
			System.out.println("What do you want to do?\n" 
								+ "-Add item to cart (add)\n"
								+ "-Remove item from cart (remove)\n"
								+ "-Inspect cart (inspect)\n"
								+ "-Browse available items (browse)\n"
								+ "-Search for item in the store (search)\n"
								+ "-Search for item in the cart (cart)\n"
								+ "-Check out cart. (checkout)\n"
								+ "-Exit (exit)\n");
			
			String response = getUserInput();
			
			if(response.equalsIgnoreCase("add")) {
				return AppMode.ADD;
			}
			else if(response.equalsIgnoreCase("remove")) {
				return AppMode.REMOVE;
			}
			else if(response.equalsIgnoreCase("inspect")) {
				return AppMode.INSPECT;
			}
			else if(response.equalsIgnoreCase("search")) {
				return AppMode.SEARCH;
			}
			else if(response.equalsIgnoreCase("checkout")) {
				return AppMode.CHECKOUT;
			}
			else if(response.equalsIgnoreCase("browse")) {
				return AppMode.BROWSE;
			}
			else if(response.equalsIgnoreCase("exit")) {
				return AppMode.EXIT;
			}
			else if(response.equalsIgnoreCase("cart")) {
				return AppMode.CART;
			}
		}
	}
	

}
