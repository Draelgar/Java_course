package mainPackage;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.SortedSet;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;

public class BarberBookingSystem {
	private SortedSet<BookedTime> mBookings; // A set of bookings.
	private Set<String> mBarbers; // A set of available barbers.
	private DisplayMode mDisplayMode; // Determines which bookings that should be displayed.
	private String mCurrentBarber; // The currently used barber.
	
	public static void main(String[] args) {
		BarberBookingSystem bbs = new BarberBookingSystem();
		bbs.run();
	}
	
	// Default constructor.
	public BarberBookingSystem() {
		mBookings = new TreeSet<BookedTime>();
		mBarbers = new HashSet<String>();
		
		mDisplayMode = DisplayMode.Day;
		
		Init();
	}
	
	private void Init() {
		// Add some default barbers.
		load();
		
		if(mBarbers.size() <= 0)
			mBarbers.add("Barber");
			
		mCurrentBarber = mBarbers.iterator().next();
	}
	
	// Run the application loop.
	public void run() {
		int programMode = 0;
		
		TimeZone localTimeZone = Calendar.getInstance().getTimeZone(); // Get the local time zone.
		Clock localClock = Clock.system(localTimeZone.toZoneId()); // Get the current clock in local time.
		ZonedDateTime localZdt = ZonedDateTime.now(localClock); // Convert the local time to a more suitable format.
		
		String time = localZdt.toLocalTime().toString();
		time = time.substring(0, time.length() - 7);
		System.out.println("Welcome to this barber booking system. " + localZdt.toLocalDate().toString() + ", " + time);
		
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
					programMode = selectBarber();
					break;
				}
				case 4:
				{
					programMode = showFreeTime();
					break;
				}
				default:
				{
					programMode = -1; // If we reach this point, something went wrong, set mode to -1 to terminate the application!
					break;
				}
			}
		}
		
		save();
	}
	
	// Book a new time for the customer.
	private int bookTime() {
		boolean running = true, repeat = true;

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		
		String customer, barber = mCurrentBarber;
		String response;
		int year = -1, month = -1, day = -1, startH = -1, startM = -1, durationM = -1, recurring = 0;
		
		ZonedDateTime startTime;
		ZonedDateTime newStartTime;
		Duration duration;
		
		Calendar cal = Calendar.getInstance();
		boolean leapyear = false;
		
		// Setting up the patterns.
		Pattern patFour = Pattern.compile("(\\d{4})"), 
				patTwo = Pattern.compile("(\\d{2})");
		
		Matcher m;
		
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
				
				LocalDateTime ldt;
				
				repeat = true;
				
				// Check for the year.
				if(m.find()) {
					year = Integer.parseInt(m.group(0));
					
					// Set the matcher to match against double numbers and skip the 4 first values as that was the year.
					m = patTwo.matcher(response.substring(4));
					
					// Check for the month.
					if(m.find()) {
						month = Integer.parseInt(m.group(0));
						
						// Check for the day.
						if(m.find()) {
							day = Integer.parseInt(m.group(0));
							
							if(month >= 1 && month <= 12) {	// Is this a valid month?
								ldt = LocalDateTime.of(1, month, 1, 1, 1, 0, 0);
								
								if(day >= 1 && day <= ldt.getMonth().length(false)) {
									System.out.println(year + "-" + month + "-" + day);
									repeat = false;
								}
								else {
									cal.set(Calendar.YEAR, year);
									leapyear = cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365;
									
									System.out.println(day + " is not a valid day! Only values between 01 and " + 
																			ldt.getMonth().length(leapyear) +" is accepted!");
									day = -1;
									repeat = true;
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
				
				if(day == -1)
				{
					System.out.println("The date format was incorrect, please try again!");
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
				
				repeat = false; // Everything looks okay so far.
				
				// Loop through the list.
				Iterator<BookedTime> it = mBookings.iterator();
				
				while(it.hasNext()) {
					BookedTime b = it.next();
					
					// Check for overlaps!
					if(b.isOverlaping(mCurrentBarber, startTime, startTime.plusMinutes(duration.toMinutes()))) {
						System.out.println("The selected time is already in use!");
						repeat = true;
						break;
					}
				}
				// Was the time acceptable?
				if(!repeat) {
					mBookings.add(new BookedTime(startTime, duration, customer, barber, recurring));
					
					if(recurring > 0) {
						newStartTime = startTime;
						while(newStartTime.getYear() == startTime.getYear()) {
							newStartTime = newStartTime.plusWeeks(recurring);
							mBookings.add(new BookedTime(newStartTime, duration, customer, barber, recurring));
						}
					}
					
					System.out.println("Appointment booked successfully!");
					running = false;
				}
			}
			
		}
		
		return 0;
	}
	
	@SuppressWarnings("unused")
	private int menu() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		
		String response = "";
		
		System.out.println("What do you wish to do?\n-See current bookings (c)\n-See available time (a)\n" +
				"-Book a new time (b)\n-Exit (exit)\n" + 
				"-Select barber (s)\nYou can cancel at any time and go back here by typing (cancel)");
		
		response = scanner.nextLine();
		response = response.toLowerCase();
		
		if(response.length() > 0) {
			for(int i = 0; i < response.length(); i++) {
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
				else {
					System.out.println("Invalid data, try again!");
					return 0;
				}
			}
		}
		
		return 0;
	}
	
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
	
	private boolean isIncluded(ZonedDateTime zdt) {
		return isIncluded(zdt, -1);
	}
 	private boolean isIncluded(ZonedDateTime zdt, int i)
	{
		ZonedDateTime time = ZonedDateTime.now(zdt.getZone()); // Get the current time for this zone ID.
		ZonedDateTime limit;
		
		int utcInt, zdtInt;
		
		if(i >= 0) {
			time = time.plusDays(i);
		}
		
		switch(mDisplayMode) {
			case Day:
			{
				utcInt = time.getYear();
				zdtInt = zdt.getYear();
				if(utcInt == zdtInt) {
					utcInt = time.getMonthValue();
					zdtInt = zdt.getMonthValue();
					if(utcInt == zdtInt) {
						utcInt = time.getDayOfMonth();
						zdtInt = zdt.getDayOfMonth();
						if(utcInt == zdtInt)
							return true;
					}
				}
				break;
			}
			case Week:
			{
				utcInt = time.getYear();
				zdtInt = zdt.getYear();
				if(utcInt == zdtInt) {
					utcInt = time.getMonthValue();
					zdtInt = zdt.getMonthValue();
					if(utcInt == zdtInt) {
						// Calculate the time by the end of the the current week.
						limit = time.plusDays(7 - time.getDayOfWeek().getValue());

						// Is the booked time after the current day and on the same week?
						if(zdt.isBefore(limit) && zdt.isAfter(time))
							return true;
					}
				}
				break;
			}
			case Month:
			{
				utcInt = time.getYear();
				zdtInt = zdt.getYear();
				if(utcInt == zdtInt) {
					utcInt = time.getMonthValue();
					zdtInt = zdt.getMonthValue();
					if(utcInt == zdtInt)
						return true;
				}
				break;
			}
			case Year:
			{
				utcInt = time.getYear();
				zdtInt = zdt.getYear();
				if(utcInt == zdtInt)
					return true;
				break;
			}
			default:
			{
				// Something went wrong, do nothing
				break;
			}
		}
		
		return false;
	}
	
	private int displayBookings(DisplayMode display) {
		
		if(mBookings.size() > 0)
		{
			System.out.print("Current appointment for this " + mDisplayMode.toString() + " for " + mCurrentBarber + ":\n");
			
			Iterator<BookedTime> it = mBookings.iterator();
			while(it.hasNext()) {
				BookedTime booking = it.next();
				if(booking.getBarber().equals(mCurrentBarber))	
					if(isIncluded(booking.getStartTime()) || isIncluded(booking.getEndTime())) {
						booking.print();
						System.out.println(); // Add an extra empty row.
					}
			}
		}
		else
		{
			System.out.println("There are no current bookings.");
		}
		
		return 0;
	}
	
	public void addBarber(String barber) {
		mBarbers.add(barber);
	}
	
	public void removeBarber(String barber) {
		mBarbers.remove(barber);
	}
	
	public int selectBarber() {
		boolean repeat = true;
		
		while(repeat) {
			System.out.println("These are the barbers that work here:");
			
			Iterator<String> it = mBarbers.iterator();
			
			while(it.hasNext()) {
				System.out.println("\t-" + it.next());
			}
			
			System.out.println("\nWhich one do you pick?");
			
			@SuppressWarnings("resource")
			Scanner scanner = new Scanner(System.in);
			
			String barber = scanner.nextLine();
			barber = barber.toLowerCase();
			
			if(barber.equals("cancel")) {
				repeat = false;
				break;
			}
			
			it = mBarbers.iterator();
			
			while(it.hasNext()) {
				String name = it.next();
				
				if(name.toLowerCase().equals(barber)) {
					mCurrentBarber = name;
					System.out.println(mCurrentBarber + " is now the active barber.");
					
					repeat = false;
				}
			}
			
			if(!mCurrentBarber.toLowerCase().equals(barber)) {
				System.out.println("Barber does not exist! Going with " + mCurrentBarber + " instead!");
			}
		}
		
		return 0;
	}
	
	private int showFreeTime() {
		
		System.out.print("Current unbooked time for " + mCurrentBarber + ":\n");
		
		ZonedDateTime zdt = ZonedDateTime.now();
		Calendar cal = Calendar.getInstance();
		
		int limit = 1;
		if(mDisplayMode == DisplayMode.Week)
			limit = 7 - zdt.getDayOfWeek().getValue();
		else if(mDisplayMode == DisplayMode.Month)
			limit = zdt.getMonth().length(cal.getActualMaximum(Calendar.DAY_OF_YEAR) > 365) - zdt.getDayOfMonth();
		else if(mDisplayMode == DisplayMode.Year) {
			if(cal.getActualMaximum(Calendar.DAY_OF_YEAR) == 365)
				limit = 365 - zdt.getDayOfYear();
			else
				limit = 366 - zdt.getDayOfYear();
		}
		
		mDisplayMode = DisplayMode.Day;
		
		for(int i = 0; i < limit; i++) {
			// Assuming the list is in order.
			System.out.print(zdt.getDayOfWeek().toString() + " " + zdt.getDayOfMonth() + " " + zdt.getMonth() + " " + zdt.getYear() + "\n08:00 - ");
			
			Iterator<BookedTime> it = mBookings.iterator();
			
			while(it.hasNext()) {
				BookedTime booking = it.next();
				if(booking.getBarber().equals(mCurrentBarber))	
					if(isIncluded(booking.getStartTime(), i) || isIncluded(booking.getEndTime(), i)) {
						
						// Assuming the list is in order.
						System.out.println(booking.getStartTime().minusMinutes(1).toLocalTime().toString());
						System.out.print(booking.getEndTime().plusMinutes(1).toLocalTime().toString() + " - ");
						
					}
			}
			
			zdt = zdt.plusDays(1);
			
			System.out.println("18:00");
		}
		
		return 0;
	}
	
	private void save() {
		
		File file = new File("data/data.txt");
		
		try {
			if(!file.exists()) // If the file does not exist, create a new one.
				file.createNewFile();
			
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write(""); // Overwrite the old data.
			
			for(BookedTime bt : mBookings) {
				bt.save(bw);
			}
			
			bw.close();
			
		} catch (IOException e) {
			System.out.println("Error, failed to create the file! " + e.getMessage());
		}
		
	}
	
	private void load() {
		File file = new File("data/barbers.txt");
		
		try {
			if(!file.exists()) // If the file does not exist, create a new one.
				file.createNewFile();
			
			FileReader fr = new FileReader(file);
			Scanner scanner = new Scanner(fr);
			
			String line = "";
			
			while(scanner.hasNext()) {
				line = scanner.nextLine(); // Read 1 line of data.
				
				mBarbers.add(line);
			}
			
			scanner.close();
			
		} catch (IOException e) {
			System.out.println("Error, failed to create the file! " + e.getMessage());
		}
		
		file = new File("data/data.txt");
		
		try {
			if(!file.exists()) // If the file does not exist, create a new one.
				file.createNewFile();
			
			FileReader fr = new FileReader(file);
			Scanner scanner = new Scanner(fr);
			
			String line = "";
			
			while(scanner.hasNext()) {
				line = scanner.nextLine(); // Read 1 line of data.
				
				mBookings.add(new BookedTime(line));
			}
			
			scanner.close();
			
		} catch (IOException e) {
			System.out.println("Error, failed to create the file! " + e.getMessage());
		}
	}

}
