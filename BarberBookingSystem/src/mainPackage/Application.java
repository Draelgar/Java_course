package mainPackage;

import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Application {
	private DisplayMode mDisplayMode; // Determines which bookings that should be displayed.
	private BookKeeper mBookKeeper;
	private String mBarber;
	
	// Default constructor.
	public Application() {		
		mDisplayMode = DisplayMode.Day;
		mBookKeeper = null;
		mBarber = null;
		
	}
	
	// Run the application loop.
	public void run() {
		
		try {
			
			mBookKeeper = new BookKeeper();
			mBookKeeper.init(); // Make sure to initialize the book keeper.
			mBarber = mBookKeeper.getBarber();
			
		} catch (IOException e) {
			UserInterface.println("Warning: " + e.getMessage());
		} catch (DateTimeParseException e) {
			UserInterface.println("Warning: " + e.getMessage());
		} catch (ArrayIndexOutOfBoundsException e) {
			UserInterface.println("Warning: " + e.getMessage());
		} catch (NumberFormatException e) {
			UserInterface.println("Warning: " + e.getMessage());
		}
		
		int programMode = 0;
		ZonedDateTime localZdt = ZonedDateTime.now(); // Convert the local time to a more suitable format.
		
		String time = localZdt.toLocalTime().toString(); // Get the current local time as a string.
		time = time.substring(0, time.length() - 7); // Cut seconds and milliseconds from the end.
		UserInterface.println("Welcome to this barber booking system. " + localZdt.toLocalDate().toString() + ", " + time);
		
		// Start the program loop.
		while(programMode >= 0)
		{
			switch(programMode) {
				case 0:
				{
					programMode = menu(); // Select Task.
					break;
				}
				case 1:
				{
					programMode = bookTime(); // Assign Date & Time to Book.
					break;
				}
				case 2:
				{
					programMode = displayBookings(mDisplayMode); // Display bookings.
					break;
				}
				case 3:
				{
					programMode = selectBarber(); // Select active barber.
					break;
				}
				case 4:
				{
					programMode = showFreeTime(); // Display times available for booking.
					break;
				}
				case 5:
				{
					programMode = saveData(); // Save the data to a file.
					break;
				}
				case 6:
				{
					programMode = loadData(); // Load data from a file.
					break;
				}
				default:
				{
					programMode = -1; // If we reach this point, something went wrong, set mode to -1 to terminate the application!
					break;
				}
			}
		}
		
		try {
			mBookKeeper.save("data/data.txt");
		} catch (IOException e) {
			UserInterface.println("Warning: " + e.getMessage());
		}
	}
	
	private int loadData() {
		boolean running = true;
		
		String path = "";
		
		while(running) {
			UserInterface.println("Type the file path. (some-directory-/filename.txt)");
			
			path = UserInterface.getInput();
			
			if(path.equalsIgnoreCase("cancel"))
				return 0;
			
			try {
				mBookKeeper.load(path);
				UserInterface.println("List of appointments successfully loaded from " + path);
				running = false;
				
			} catch (IOException e) {
				UserInterface.println("Warning, something went wrong: " + e.getMessage() + "\nPlease try again!");
			} catch (DateTimeParseException e) {
				UserInterface.println("Warning, the date time format is wrong: " + e.getMessage() + "\nPlease try again!");
			} catch (ArrayIndexOutOfBoundsException e) {
				UserInterface.println("Warning, too few parameters per line in the file: " + e.getMessage() + "\nPlease try again!");
			} catch (NumberFormatException e) {
				UserInterface.println("Warning, something went wrong: " + e.getMessage() + "\nPlease try again!");
			}
		}
		
		return 0;
	}
	
	private int saveData() {
		boolean running = true;
		String path = "";
		
		while(running) {
			UserInterface.println("Type the file path. (some-directory-/filename.txt)");
			
			path = UserInterface.getInput();
			
			if(path.equalsIgnoreCase("cancel"))
				return 0;
			
			try {
				mBookKeeper.save(path);
				UserInterface.println("List of appointments successfully saved to " + path);
				running = false;
				
			} catch (IOException e) {
				UserInterface.println("Warning, something went wrong: " + e.getMessage() + "\nPlease try again!");
			}
		}
		
		return 0;
	}
	
	// Book a new time for the customer.
	private int bookTime() {
		boolean running = true, repeat = true;
		
		// Create and initialize variables to use.
		String customer;
		String response;
		int year = -1, month = -1, day = -1, startH = -1, startM = -1, durationM = -1, recurring = 0;
		ZonedDateTime startTime;
		Duration duration;
		Calendar cal = Calendar.getInstance();
		
		// Setting up the patterns and creating the matcher.
		Pattern patFour = Pattern.compile("(\\d{4})"), 
				patTwo = Pattern.compile("(\\d{2})");
		Matcher m;
		
		// Start the loop.
		while(running) {
			UserInterface.println("What is your name?");
			customer = UserInterface.getInput();
			
			// Did the user want to cancel the operation?
			if(customer.equalsIgnoreCase("cancel")) {
				return 0;
			}
			
			// Ask for the date
			while(repeat) {
				UserInterface.println("Assign the desired date. (YYYY-MM-DD)");
				response = UserInterface.getInput();
				
				// Did the user want to cancel the operation?
				if(response.equalsIgnoreCase("cancel")) {
					return 0;
				}
					
				// Set the matcher to match for 4 numbers in a row.
				m = patFour.matcher(response);
				
				repeat = true;
				
				// Check for the year.
				if(m.find()) {
					year = Integer.parseInt(m.group(0));
					
					// Set the matcher to match against double numbers and skip the 4 first values as that was the year.
					m = patTwo.matcher(response.substring(4));
					
					ZonedDateTime zdt = ZonedDateTime.now();
					if(zdt.getYear() <= year) { // Is this a future year or same year?
						// Check for the month.
						if(m.find()) {
							month = Integer.parseInt(m.group(0));
							
							if((zdt.getMonthValue() <= month && zdt.getYear() == year) || zdt.getYear() < year) { // Does this month lie in the future?
								// Check for the day.
								if(m.find()) {
									day = Integer.parseInt(m.group(0));
									
									if(month >= 1 && month <= 12) {	// Is this a valid month?
										cal.set(Calendar.YEAR, year);
										cal.set(Calendar.MONTH, month);
										
										if((zdt.getDayOfMonth() <= day && zdt.getMonthValue() == month) || zdt.getMonthValue() < month) { // Does this day lie in the future?
											if(day >= 1 && day <= cal.getActualMaximum(Calendar.DAY_OF_MONTH)) { // Is this a valid day of the month?
												UserInterface.println(year + "-" + month + "-" + day);
												repeat = false;
											}
											else {
												
												UserInterface.println(day + " is not a valid day! Only values between 01 and " + 
														cal.getActualMaximum(Calendar.DAY_OF_MONTH) +" is accepted!");
												day = -1;
												repeat = true;
											}
										}
									}
									else {
										UserInterface.println(month + " is not a valid month! Only values between 01 and 12 is accepted!");
										day = -1;
										repeat = true;
									}
								}
							}
						}
					}
				}
				
				repeat = false;
				
				if(day == -1)
				{
					UserInterface.println("The date format was incorrect or the date lies in the past, please try again!");
					repeat = true;
				}
				
			}
			
			// Ask for the duration.
			repeat = true;
			while(repeat) {
				UserInterface.println("Select a duration in minutes (20 - 99). (mm)");
				
				response = UserInterface.getInput();
				
				// Did the user want to cancel the operation?
				if(response.equalsIgnoreCase("cancel")) {
					return 0;
				}
				
				// Set the matcher to match against double numbers and skip the 4 first values as that was the year.
				m = patTwo.matcher(response);
				
				// Get the value.
				if(m.find()) {
					durationM = Integer.parseInt(m.group(0));
					
					if(durationM <= 99 && durationM >= 20) {
						repeat = false;
					}
					else {
						UserInterface.println("The duration was not long enough or too long. Please try again.");
						repeat = true;
					}
				}
				
				if(durationM == -1)
				{
					UserInterface.println("The duration format was incorrect, please try again!");
					repeat = true;
				}
			}
			
			duration = Duration.ofMinutes(durationM);
			
			// Ask for the start time.
			repeat = true;
			while(repeat) {
				UserInterface.println("Select a start time. (hh:mm)");
				
				response = UserInterface.getInput();
				
				// Did the user want to cancel the operation?
				if(response.equalsIgnoreCase("cancel")) {
					return 0;
				}
					
				// Set the matcher to match for a pair or numbers.
				m = patTwo.matcher(response);
				
				repeat = true;
				
				// Find the hours,
				if(m.find()) {
					startH = Integer.parseInt(m.group(0));
					
					// Find the minutes.
					if(m.find()) {
						startM = Integer.parseInt(m.group(0));
						
						if(startH >= 8 && startH <= 18 - duration.toHours()) { // Is this hour valid?
							if(startM >= 0 && startM <= 59) { // Is this a valid minute?
								UserInterface.println(startH + ":" + startM);
								repeat = false;
								
								UserInterface.println("Do you want this to be a recurring appointment? Type time intervall in weeks (0 = not recurring)");
								response = UserInterface.getInput();
								
								if(response.equalsIgnoreCase("cancel"))
									return 0;
									
								try{
									recurring = Integer.parseInt(response);
								} catch(NumberFormatException e) {
									recurring = 0;
								}
								
							}
							else {
								UserInterface.println(startM + " is not accepted! Only hours between 8 and 18 can be booked!");
								repeat = true;
								startM = -1;
							}
						}
						else {
							UserInterface.println(startH + " is not a valid hour! Only values 0 to 23 is accepted!");
							repeat = true;
							startM = -1;
						}
					}
				}

				if(startM == -1)
				{
					UserInterface.println("The start time format was incorrect, please try again!");
				}
						
				// Combine the data into a single ZonedDateTime object.
				startTime = ZonedDateTime.of(year, month, day, startH, startM, 0, 0, Calendar.getInstance().getTimeZone().toZoneId());
				
				BookedTime bt = new BookedTime(startTime, duration, customer, mBarber, recurring);

				repeat = !mBookKeeper.bookTime(bt);
				
				if(!repeat)
					running = false;
				else
					UserInterface.println("The selected time is already booked, please select another time or try with another barber.");
			}
			
		}
		
		UserInterface.println("Appointment successfully booked!");
		
		return 0;
	}
	
	// Display the menu.
	private int menu() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		
		String response = "";
		
		// Display the options available.
		UserInterface.println("What do you wish to do?\n-See current bookings (current)\n-See available time (available)\n" +
				"-Book a new time (book)\n-Load appointments from txt a file (load)\n-Save appointments to a txt file (save)\n-Exit (exit)\n" + 
				"-Select barber (select)\nYou can cancel at any time and go back here by typing (cancel)");
		
		// Wait for input.
		response = scanner.nextLine();
		
		// Translate the input.
		if(response.length() > 0) {
			if(response.equalsIgnoreCase("current")) {
				setDisplayMode();
				return 2;
			}
			else if(response.equalsIgnoreCase("available")) {
				setDisplayMode();
				return 4;
			}
			else if(response.equalsIgnoreCase("book")) {
				return 1;
			}
			else if(response.equalsIgnoreCase("select")) {
				return 3;
			}
			else if(response.equalsIgnoreCase("load")) {
				return 6;
			}
			else if(response.equalsIgnoreCase("save")) {
				return 5;
			}
			else if(response.equalsIgnoreCase("exit")) {
				return -1;
			}
		}
		
		// No valid option.
		UserInterface.println(response + " is not a valid option, try again!");
		return 0;
	}
	
	// Set the flag deciding weather the display limit is a day, a week, a month or a year. 
	private void setDisplayMode() {
		boolean repeat = true;
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		
		String response = "";
		
		while(repeat) {
			UserInterface.println("For which time do you wich to see?\n-Today (day)\n-This week (week)\n-This month(month)\n"
					+ "-This year (year)\nYou can cancel at any time and go back here by typing (cancel)");
			
			response = scanner.nextLine();
			
			if(response.equalsIgnoreCase("cancel"))
				return;
			
			if(response.length() > 0) {
				repeat = false;
				if(response.equalsIgnoreCase("day")) {
					mDisplayMode = DisplayMode.Day;
				}
				else if(response.equalsIgnoreCase("week")) {
					mDisplayMode = DisplayMode.Week;
				}
				else if(response.equalsIgnoreCase("month")) {
					mDisplayMode = DisplayMode.Month;
				}
				else if(response.equalsIgnoreCase("year")) {
					mDisplayMode = DisplayMode.Year;
				}
				else {
					UserInterface.println(response + " is not a valid option. \nPlease try again!");
					repeat = true;
				}
			}
		}
	}
	
 	// Display the booked appointments for the given display limit.
	private int displayBookings(DisplayMode display) {
		
		UserInterface.println(mBookKeeper.displayBookings(display));
		
		return 0;
	}
	
	// Add a new barber to the list.
	public void addBarber(String barber) {
		mBookKeeper.addBarber(barber);
	}
	
	// Remove a barber from the list.
	public void removeBarber(String barber) {
		mBookKeeper.removeBarber(barber);
	}
	
	// Select an available barber.
	public int selectBarber() {		
		UserInterface.println("These are the barbers that work here:\n");
		UserInterface.println(mBookKeeper.listBarbers());
		
		UserInterface.println("\nWhich one do you pick?");
		
		String barber = UserInterface.getInput();
			
		if(barber.equalsIgnoreCase("cancel")) {
			return 0;
		}
			
		if(mBookKeeper.selectBarber(barber))
			UserInterface.println(mBookKeeper.getBarber() + " is now the active barber.");
		else
			UserInterface.println("Barber does not exist. \nSelecting " + mBookKeeper.getBarber() + " instead!");
		
		mBarber = mBookKeeper.getBarber();
		
		return 0;
	}
	
	// Show available times for the currently selected barber.
	private int showFreeTime() {
		
		UserInterface.print("Current unbooked time for " + mBookKeeper.getBarber() + ":\n");
		
		mBookKeeper.showFreeTime(mDisplayMode);
		
		return 0;
	}
	
	public static void main(String args[]) {
		Application app = new Application();
		app.run();
	}
}
