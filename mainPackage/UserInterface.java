package mainPackage;

import java.util.Scanner;

/** This class is for using the console for input and output.
 * @author Gustaf Peter Hultgren
 * @version 1.0 **/
public class UserInterface {
	/** The scanner for obtaining user input. **/
	private Scanner mScanner = null;
	/** The one and only instance of this object. **/
	private static UserInterface mInstance= null;
	
	/** Hidden constructor. **/
	private UserInterface() {
		mScanner = new Scanner(System.in);
	}
	
	/** Get the one and only instance of this class.
	 * @return A reference to the instance. **/
	public static UserInterface getSingleton() {
		if(mInstance == null)
			mInstance = new UserInterface();
		
		return mInstance;
	}
	
	/** Display the menu and ask for input.
	 * @return The selected option in the form of a ProgramMode enumeration object. **/
	public ProgramMode showMenu() {
		println("What do you wish to do?\n"
				+ "-See current bookings (current)\n"
				+ "-See available time (available)\n"
				+ "-Book a new time (book)\n"
				+ "-Cancel an appointment (unbook)\n"
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
		else if(input.equalsIgnoreCase("unbook"))
			return ProgramMode.CANCEL_APPOINTMENT;
		else
			return ProgramMode.NONE;
	}
	
	/** Display the menu for periods and ask for the user to select.
	 * @return The selected time period. **/
	public TimeMode getTimeMode() {
		println("For which period do you wish to display data?\n"
				+ "-Today (day)\n"
				+ "-This week (week)\n"
				+ "-This month (month)\n"
				+ "-This year (year)\n"
				+ "-A year from now (ahead)\n");
		
		String input = getInput();
		if(input.equalsIgnoreCase("day"))
			return TimeMode.DAY;
		else if(input.equalsIgnoreCase("week"))
			return TimeMode.WEEK;
		else if(input.equalsIgnoreCase("month"))
			return TimeMode.MONTH;
		else if(input.equalsIgnoreCase("year"))
			return TimeMode.YEAR;
		else if(input.equalsIgnoreCase("ahead"))
			return TimeMode.YEAR_AHEAD;
		else
			return TimeMode.NONE;
	}
	
	/** Print the string to the console and add a new line. 
	 * @param -The string to print. **/
	public void println(String s) {	
		System.out.println(s);
	}
	
	/** Print the string to the console. 
	 * @param -The string to print. **/
	public void print(String s) {
		System.out.print(s);
	}
	
	/** Get one line of user input.
	 * @return A string given by the user. **/
	public String getInput() {
		return mScanner.nextLine();
	}
	
	/** Close the scanner resource. **/
	public void close() {
		mScanner.close();
	}
}
