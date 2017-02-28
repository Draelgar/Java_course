package mainPackage;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInterface {
	private DisplayMode mDisplayMode; // Determines which bookings that should be displayed.
	private BookKeeper mBookKeeper;
	private String mBarber;
	
	// Default constructor.
	public UserInterface() {		
		mDisplayMode = DisplayMode.Day;
		mBookKeeper = new BookKeeper();
		mBarber = mBookKeeper.getBarber();
		
	}
	
	// Run the application loop.
	public void run() {
		int programMode = 0;
		ZonedDateTime localZdt = ZonedDateTime.now(); // Convert the local time to a more suitable format.
		
		String time = localZdt.toLocalTime().toString(); // Get the current local time as a string.
		time = time.substring(0, time.length() - 7); // Cut seconds and milliseconds from the end.
		System.out.println("Welcome to this barber booking system. " + localZdt.toLocalDate().toString() + ", " + time);
		
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
				default:
				{
					programMode = -1; // If we reach this point, something went wrong, set mode to -1 to terminate the application!
					break;
				}
			}
		}
		
		mBookKeeper.save();
	}
	
	// Book a new time for the customer.
	private int bookTime() {
		boolean running = true, repeat = true;

		@SuppressWarnings("resource") // Can not close scanner as that will also close the console input stream.
		Scanner scanner = new Scanner(System.in);
		
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
			System.out.println("What is your name?");
			customer = scanner.nextLine();
			
			// Did the user want to cancel the operation?
			if(customer.toLowerCase().equals("cancel")) {
				return 0;
			}
			
			// Ask for the date
			while(repeat) {
				System.out.println("Assign the desired date. (YYYY-MM-DD)");
				response = scanner.nextLine();
				
				// Did the user want to cancel the operation?
				if(response.toLowerCase().equals("cancel")) {
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
												System.out.println(year + "-" + month + "-" + day);
												repeat = false;
											}
											else {
												
												System.out.println(day + " is not a valid day! Only values between 01 and " + 
														cal.getActualMaximum(Calendar.DAY_OF_MONTH) +" is accepted!");
												day = -1;
												repeat = true;
											}
										}
									}
									else {
										System.out.println(month + " is not a valid month! Only values between 01 and 12 is accepted!");
										day = -1;
										repeat = true;
									}
								}
							}
						}
					}
				}
				
				if(day == -1)
				{
					System.out.println("The date format was incorrect or the date lies in the past, please try again!");
					repeat = true;
				}
				
			}
			
			// Ask for the duration.
			repeat = true;
			while(repeat) {
				System.out.println("Select a duration in minutes (20 - 99). (mm)");
				
				response = scanner.nextLine();
				
				// Did the user want to cancel the operation?
				if(response.toLowerCase().equals("cancel")) {
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
						System.out.println("The duration was not long enough or too long!");
						repeat = true;
					}
				}
				
				if(durationM == -1)
				{
					System.out.println("The duration format was incorrect, please try again!");
					repeat = true;
				}
			}
			
			duration = Duration.ofMinutes(durationM);
			
			// Ask for the start time.
			repeat = true;
			while(repeat) {
				System.out.println("Select a start time. (hh:mm)");
				
				response = scanner.nextLine();
				
				// Did the user want to cancel the operation?
				if(response.toLowerCase().equals("cancel")) {
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
								System.out.println(startH + ":" + startM);
								repeat = false;
								
								System.out.println("Do you want this to be a recurring appointment? Type time intervall in weeks (0 = not recurring)");
								response = scanner.nextLine().toLowerCase();
								
								if(response.equals("cancel"))
									return 0;
									
								try{
									recurring = Integer.parseInt(response);
								} catch(Exception e) {
									recurring = 0;
								}
								
							}
							else {
								System.out.println(startM + " is not accepted! Only hours between 8 and 18 can be booked!");
								repeat = true;
								startM = -1;
							}
						}
						else {
							System.out.println(startH + " is not a valid hour! Only values 0 to 23 is accepted!");
							repeat = true;
							startM = -1;
						}
					}
				}

				if(startM == -1)
				{
					System.out.println("The start time format was incorrect, please try again!");
				}
						
				// Combine the data into a single ZonedDateTime object.
				startTime = ZonedDateTime.of(year, month, day, startH, startM, 0, 0, Calendar.getInstance().getTimeZone().toZoneId());
				
				BookedTime bt = new BookedTime(startTime, duration, customer, mBarber, recurring);
				
				repeat = mBookKeeper.checkOverlaps(bt);
						
				// Was the time acceptable?
				if(!repeat) {
					running = !mBookKeeper.bookTime(bt);
				}
			}
			
		}
		
		System.out.println("Appointment successfully booked!");
		
		return 0;
	}
	
	// Display the menu.
	private int menu() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		
		String response = "";
		
		// Display the options available.
		System.out.println("What do you wish to do?\n-See current bookings (c)\n-See available time (a)\n" +
				"-Book a new time (b)\n-Exit (exit)\n" + 
				"-Select barber (s)\nYou can cancel at any time and go back here by typing (cancel)");
		
		// Wait for input.
		response = scanner.nextLine();
		response = response.toLowerCase();
		
		// Translate the input.
		if(response.length() > 0) {
			if(response.startsWith("c")) {
				setDisplayMode();
				return 2;
			}
			else if(response.startsWith("a")) {
				setDisplayMode();
				return 4;
			}
			else if(response.startsWith("b")) {
				return 1;
			}
			else if(response.startsWith("s")) {
				return 3;
			}
			else if(response.equals("exit")) {
				return -1;
			}
		}
		
		// No valid option.
		System.out.println(response + " is no valid option, try again!");
		return 0;
	}
	
	// Set the flag deciding weather the display limit is a day, a week, a month or a year. 
	private void setDisplayMode() {
		boolean repeat = true;
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		
		String response = "";
		
		while(repeat) {
			System.out.println("For which time do you wich to see?\n-Today (d)\n-This week (w)\n-This month(m)\n"
					+ "-This year (y)\nYou can cancel at any time and go back here by typing (cancel)");
			
			response = scanner.nextLine();
			response = response.toLowerCase();
			
			if(response.equals("cancel"))
				return;
			
			if(response.length() > 0) {
				repeat = false;
				if(response.startsWith("d")) {
					mDisplayMode = DisplayMode.Day;
				}
				else if(response.startsWith("w")) {
					mDisplayMode = DisplayMode.Week;
				}
				else if(response.startsWith("m")) {
					mDisplayMode = DisplayMode.Month;
				}
				else if(response.startsWith("y")) {
					mDisplayMode = DisplayMode.Year;
				}
				else {
					System.out.println("Invalid option!");
					repeat = true;
				}
			}
		}
	}
	
 	// Display the booked appointments for the given display limit.
	private int displayBookings(DisplayMode display) {
		
		mBookKeeper.displayBookings(display);
		
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
		System.out.println("These are the barbers that work here:");
		
		mBookKeeper.listBarbers();
		
		System.out.println("\nWhich one do you pick?");
			
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		
		String barber = scanner.nextLine();
			
		if(barber.equalsIgnoreCase("cancel")) {
			return 0;
		}
			
		if(mBookKeeper.selectBarber(barber))
			System.out.println(mBookKeeper.getBarber() + " is now the active barber.");
		else
			System.out.println("Barber does not exist! Going with " + mBookKeeper.getBarber() + " instead!");
		
		mBarber = mBookKeeper.getBarber();
		
		return 0;
	}
	
	// Show available times for the currently selected barber.
	private int showFreeTime() {
		
		System.out.print("Current unbooked time for " + mBookKeeper.getBarber() + ":\n");
		
		mBookKeeper.showFreeTime(mDisplayMode);
		
		return 0;
	}
}