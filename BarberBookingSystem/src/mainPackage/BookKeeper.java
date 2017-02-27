package mainPackage;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class BookKeeper {
	private SortedSet<BookedTime> mBookings; // A set of bookings.
	private Set<String> mBarbers; // A set of available barbers.
	private String mCurrentBarber; // The currently used barber.
	
	BookKeeper() {
		mBookings = new TreeSet<BookedTime>();
		mBarbers = new HashSet<String>();
		
		init();
	}
	
	// Initialize the system.
	private void init() {
		// Add some default barbers.
		FileHandler.load(mBarbers, mBookings);
		
		if(mBarbers.size() <= 0)
			mBarbers.add("Barber");
			
		mCurrentBarber = mBarbers.iterator().next();
	}

	public void save() {
		FileHandler.save(mBookings);
	}
	
	public String getBarber() {
		return mCurrentBarber;
	}
	
	// Attempt to book a new time for the customer.
	public boolean bookTime(BookedTime bt) {
		if(checkOverlaps(bt))
			return false;
		
		mBookings.add(bt);
		
		ZonedDateTime zdt = bt.getStartTime().plusWeeks(bt.getRecurring());
		BookedTime newBt = bt;
		newBt.setTimeInterval(zdt, bt.getDuration());
		
		// If recurring, add the remaining bookings for the whole year.
		if(bt.getRecurring() > 0) {
			while(zdt.getYear() == bt.getStartTime().getYear()) {
				zdt = zdt.plusWeeks(bt.getRecurring());
				newBt.setTimeInterval(zdt, bt.getDuration());
				
				if(!checkOverlaps(newBt))
					mBookings.add(newBt);
			}
		}
		
		return true;
	}
	
	public boolean checkOverlaps(BookedTime bt) {
		// Loop through the list.
		Iterator<BookedTime> it = mBookings.iterator();
		
		while(it.hasNext()) {
			BookedTime b = it.next();
			
			// Check for overlaps!
			if(b.isOverlaping(mCurrentBarber, bt.getStartTime(), bt.getEndTime())) {
				System.out.println("The selected time is already in use!");
				return true;
			}
		}
		
		return false;
	}
	
	// Call isIncluded with default value.
	public boolean isIncluded(ZonedDateTime zdt, DisplayMode displayMode) {
		return isIncluded(zdt, -1, displayMode);
	}
	
	// Check weather the given time is included in the limit.
 	public boolean isIncluded(ZonedDateTime zdt, int i, DisplayMode displayMode)
	{
		ZonedDateTime time = ZonedDateTime.now(zdt.getZone()); // Get the current time for this zone ID.
		ZonedDateTime limit;
		
		int utcInt, zdtInt;
		
		if(i >= 0) {
			time = time.plusDays(i);
		}
		
		switch(displayMode) {
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
	
 	// Display the booked appointments for the given display limit.
	public int displayBookings(DisplayMode display) {
		String text = "";
		
		if(mBookings.size() > 0)
		{
			text = "Current appointment for this " + display.toString() + " for " + mCurrentBarber + ":\n\n";
			
			Iterator<BookedTime> it = mBookings.iterator();
			while(it.hasNext()) {
				BookedTime booking = it.next();
				if(booking.getBarber().equals(mCurrentBarber))	
					if(isIncluded(booking.getStartTime(), display) || isIncluded(booking.getEndTime(), display)) {
						text += booking.print() + "\n";
					}
			}
		}
		else
		{
			text = "There are no current bookings for " + mCurrentBarber + ".\n";
		}
		
		System.out.print(text);
		
		return 0;
	}
	
	// Add a new barber to the list.
	public void addBarber(String barber) {
		mBarbers.add(barber);
	}
	
	// Remove a barber from the list.
	public void removeBarber(String barber) {
		mBarbers.remove(barber);
	}
	
	public void listBarbers() {
		System.out.println("These are the barbers that work here:");
		
		Iterator<String> it = mBarbers.iterator();
		
		while(it.hasNext()) {
			System.out.println("\t-" + it.next());
		}
	}
	
	// Select an available barber.
	public boolean selectBarber(String barber) {
			
		Iterator<String> it = mBarbers.iterator();
		String barb = "";
		
		while(it.hasNext()) {
			barb = it.next();
			
			if(barb.equalsIgnoreCase(barber)) {
				mCurrentBarber = barb;
			}
		}
		
		return true;
	}
	
	// Show available times for the currently selected barber.
	public int showFreeTime(DisplayMode displayMode) {
		
		System.out.print("Current unbooked time for " + mCurrentBarber + ":\n");
		
		ZonedDateTime zdt = ZonedDateTime.now();
		Calendar cal = Calendar.getInstance();

		int limit = 1;
		if(displayMode == DisplayMode.Week)
			limit = 7 - cal.get(Calendar.DAY_OF_WEEK);
		else if(displayMode == DisplayMode.Month)
			limit = cal.getActualMaximum(Calendar.DAY_OF_MONTH) - cal.get(Calendar.DAY_OF_MONTH);
		else if(displayMode == DisplayMode.Year) {
			limit = cal.getActualMaximum(Calendar.DAY_OF_YEAR) - cal.get(Calendar.DAY_OF_YEAR);
		}
		
		for(int i = 0; i < limit; i++) {
			// Assuming the list is in order.
			System.out.print(zdt.getDayOfWeek().toString() + " " + zdt.getDayOfMonth() + " " + zdt.getMonth().toString() + " " + zdt.getYear() + "\n08:00 - ");
			
			Iterator<BookedTime> it = mBookings.iterator();
			
			while(it.hasNext()) {
				BookedTime booking = it.next();
				if(booking.getBarber().equals(mCurrentBarber))	
					if(isIncluded(booking.getStartTime(), i, DisplayMode.Day) || isIncluded(booking.getEndTime(), i, DisplayMode.Day)) {
						
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
}
