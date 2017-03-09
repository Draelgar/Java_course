package mainPackage;

import java.util.Scanner;

public class UserInterface {
	private static Scanner mScanner = new Scanner(System.in);
	
	public static ProgramMode showMenu() {		
		println("What do you wish to do?\n"
				+ "-See current bookings (current)\n"
				+ "-See available time (available)\n "
				+ "-Book a new time (book)\n"
				+ "-Load appointments from txt a file (load)\n"
				+ "-Save appointments to a txt file (save)\n"
				+ "-Exit (exit)\n"
				+ "-Select barber (select)\n"
				+ "You can cancel at any time and go back here by typing (cancel)");
		
		String input = getInput();
		
		if(input.equalsIgnoreCase("current"))
			return ProgramMode.LIST_APPOINTMENTS;
		else if(input.equalsIgnoreCase("available"))
			return ProgramMode.LIST_AVAILABLE_TIME;
		else if(input.equalsIgnoreCase("book"))
			return ProgramMode.BOOK_APPOINTMENT;
		else if(input.equalsIgnoreCase("load"))
			return ProgramMode.LOAD;
		else if(input.equalsIgnoreCase("save"))
			return ProgramMode.SAVE;
		else if(input.equalsIgnoreCase("exit"))
			return ProgramMode.EXIT;
		else if(input.equalsIgnoreCase("select"))
			return ProgramMode.PICK_BARBER;
		else
			return ProgramMode.NONE;
	}
	
	public static void println(String s) {
		System.out.println(s);
	}
	
	public static void print(String s) {
		System.out.print(s);
	}
	
	public static String getInput() {
		return mScanner.nextLine();
	}
}
