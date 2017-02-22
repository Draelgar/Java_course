package mainPackage;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.TimeZone;
import java.time.Clock;
import java.time.DayOfWeek;

public class BarberBookingSystem {
	private Set<BookedTime> mBookings; // A set of bookings.
	private Set<String> mBarbers; // A set of available barbers.
	private DisplayMode mDisplayMode; // Determines which bookings that should be displayed.
	
	public static void main(String[] args) {
		BarberBookingSystem bbs = new BarberBookingSystem();
		bbs.run();
	}
	
	// Default constructor.
	public BarberBookingSystem() {
		mBookings = new HashSet<BookedTime>();
		mBarbers = new HashSet<String>();
		
		mDisplayMode = DisplayMode.Day;
		
		// Add some default barbers.
		mBarbers.add("Lena");
		mBarbers.add("Olof");
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
					programMode = menu(); // TODO Menu: Select Task.
					break;
				}
				case 1:
				{
					programMode = bookTime(); // TODO Menu: Assign Date & Time to Book.
					break;
				}
				case 2:
				{
					programMode = displayBookings(mDisplayMode); // TODO: Display bookings.
					break;
				}
				default:
				{
					programMode = -1; // If we reach this point, something went wrong, set mode to -1 to terminate the application!
					break;
				}
			}
		}
	}
	
	
	// Book a new time for the customer.
	private int bookTime() {
		boolean running = true, repeat = true;

		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		
		String customer;
		String response;
		int year, month, day, startH, startM, endH, endM;
		char charBuffer[] = new char[8];
		
		ZonedDateTime startTime;
		ZonedDateTime endTime;
		
		while(running) {
			System.out.println("What is your name?");
			customer = scanner.nextLine();
			
			while(repeat) {
				System.out.println("Assign the desired date. (YYYY-MM-DD)");
				response = scanner.nextLine();
				
				// Was the date correct?
				if(response.length() == 10) {
					response.getChars(0, 4, charBuffer, 0);
					response.getChars(5, 7, charBuffer, 0);
					response.getChars(8, 10, charBuffer, 0);
					
					for(int i = 0; i < response.length(); i++)
						if(!Character.isDigit(charBuffer[i])) {
							
						}
				}
				else
					repeat = true;
			}
		}
		// TODO: Ask for customer name.
		// TODO: Ask for a date.
		// TODO: Ask for a new time booking & barber.
		// TODO: If incorrect time was given or if the time overlaps another with the same barber, retry.
		// TODO: Typing exit at any time cancels the booking process ahead of time.
		
		return 0;
	}
	
	@SuppressWarnings("unused")
	private int menu() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		
		String response = "";
		
		System.out.println("What do you wish to do?\n-See bookings for today (d)\n-See bookings for this week (w)\n-See bookings for this month(m)\n"
				+ "-See bookings for this year (y)\n-Book a new time (b)\n-Exit (exit)");
		
		response = scanner.nextLine();
		response = response.toLowerCase();
		
		if(response.length() > 0) {
			for(int i = 0; i < response.length(); i++) {
				if(response.startsWith("d")) {
					mDisplayMode = DisplayMode.Day;
					return 2;
				}
				else if(response.startsWith("w")) {
					mDisplayMode = DisplayMode.Week;
					return 2;
				}
				else if(response.startsWith("m")) {
					mDisplayMode = DisplayMode.Month;
					return 2;
				}
				else if(response.startsWith("y")) {
					mDisplayMode = DisplayMode.Year;
					return 2;
				}
				else if(response.startsWith("b")) {
					return 1;
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
	
	private boolean isIncluded(ZonedDateTime zdt)
	{
		ZonedDateTime time = ZonedDateTime.now(zdt.getZone()); // Get the current time for this zone ID.
		ZonedDateTime limit;
		
		int utcInt, zdtInt;
		
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
			System.out.print("Current appointment for this " + mDisplayMode.toString() + ":");
			
			Iterator<BookedTime> it = mBookings.iterator();
			while(it.hasNext()) {
				BookedTime booking = it.next();
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
	
	public void AddBarber(String barber) {
		mBarbers.add(barber);
	}
	
	public void RemoveBarber(String barber) {
		mBarbers.remove(barber);
	}

}
