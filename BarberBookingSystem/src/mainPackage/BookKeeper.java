package mainPackage;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
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
	
	BookKeeper() throws IOException{
		mBookings = new TreeSet<BookedTime>();
		mBarbers = new HashSet<String>();
	}
	
	// Initialize the system.
	public void init() throws IOException, DateTimeParseException, ArrayIndexOutOfBoundsException, NumberFormatException{
		// Add some default barbers.
		FileHandler.loadStaff(mBarbers, "data/barbers.txt");
		FileHandler.load(this, "data/data.txt");
		
		assert (mBookings != null); // Assume that the set isn't null!
		
		if(mBarbers.size() <= 0)
			mBarbers.add("Barber");
			
		mCurrentBarber = mBarbers.iterator().next();
	}
	
	public void load(String path) throws IOException, DateTimeParseException, ArrayIndexOutOfBoundsException, NumberFormatException{
		FileHandler.load(this, path);
	}

	public void save(String path) throws IOException{
		FileHandler.save(mBookings, path);
	}
	
	public Set<String> getBarbers() {
		return mBarbers;
	}
	
	public String getBarber() {
		return mCurrentBarber;
	}
	
	// Attempt to book a new time for the customer.
	public boolean bookTime(BookedTime bt) {	
		ZonedDateTime zdt = ZonedDateTime.now();
		
		// Only allow future dates and times to be booked!
		if(bt.getStartTime().isAfter(zdt)) {
		
			assert (mBookings != null); // Assume that the set isn't null!
			mBookings.add(bt);
		
			if(BookedTime.foundCollision())
				return false;
		
			zdt = bt.getStartTime().plusWeeks(bt.getRecurring());
			BookedTime newBt = bt;
			newBt.setTimeInterval(zdt, bt.getDuration());
			
			// If recurring, add the remaining bookings for the whole year.
			if(bt.getRecurring() > 0) {
				while(zdt.getYear() == bt.getStartTime().getYear()) {
					zdt = zdt.plusWeeks(bt.getRecurring());
					newBt.setTimeInterval(zdt, bt.getDuration());
					
					mBookings.add(newBt);
				}
			}
		
			return true; 
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
	public String displayBookings(DisplayMode display) {
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
		
		return text;
	}
	
	// Add a new barber to the list.
	public void addBarber(String barber) {
		mBarbers.add(barber);
	}
	
	// Remove a barber from the list.
	public void removeBarber(String barber) {
		mBarbers.remove(barber);
	}
	
	public String listBarbers() {
		String string = "";
		Iterator<String> it = mBarbers.iterator();
		
		while(it.hasNext()) {
			string += "\t-" + it.next() + "\n";
		}
		
		return string;
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
	public String showFreeTime(DisplayMode displayMode) {
		
		String information = "";
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
			information = zdt.getDayOfWeek().toString() + " " + zdt.getDayOfMonth() 
							+ " " + zdt.getMonth().toString() + " " 
							+ zdt.getYear() + "\n08:00 - ";
			
			Iterator<BookedTime> it = mBookings.iterator();
			
			while(it.hasNext()) {
				BookedTime booking = it.next();
				if(booking.getBarber().equals(mCurrentBarber))	
					if(isIncluded(booking.getStartTime(), i, DisplayMode.Day) || isIncluded(booking.getEndTime(), i, DisplayMode.Day)) {
						
						// Assuming the list is in order.
						information += booking.getStartTime().minusMinutes(1).toLocalTime().toString() + "\n";
						information += booking.getEndTime().plusMinutes(1).toLocalTime().toString() + " - ";
						
					}
			}
			
			zdt = zdt.plusDays(1);
			
			information += "18:00\n";
		}
		
		return information;
	}
	
	public boolean foundCollision() {
		return BookedTime.foundCollision();
	}
}
