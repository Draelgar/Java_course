package mainPackage;

import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/** This class is for handling a barber shop. 
 * @author Peter Hultgren
 * @version 1.0 **/
public class BarberShop {
	/** A list of the barbers that work here. **/
	private List<Barber> mBarbers;
	/** A reference to the currently selected barber. **/
	private Barber mCurrentBarber;
	/** The base price per minute that this shop charges. **/
	private double mBasePrice;
	
	/** Default constructor. **/
	public BarberShop() {
		mBarbers = new LinkedList<Barber>();
		mCurrentBarber = null;
		mBasePrice = 4.5;
	}
	
	/** Create a new instance of this class from a file.
	 * @param filePath -The path to the file. **/
	public BarberShop(String filePath) throws IOException{
		mBarbers = new LinkedList<Barber>();
		mCurrentBarber = null;
		mBasePrice = 4.5;
		
		// Load the file.
		String[] strings = FileHandler.load(filePath);
		Barber currentBarber = null;
		
		for(String string : strings) {
			if(!string.contains(",")) { // Is this a barber name?
					Barber barber = new Barber(string);
					currentBarber = barber;
					mBarbers.add(barber); // Create the barber.
			}
			else if(currentBarber != null) {
				currentBarber.addAppointment(new Appointment(string));
			}
		}
		
		if(mBarbers.size() > 0) {
			mCurrentBarber = mBarbers.get(0);
		}
	}
	
	/** Book a new appointment.
	 * @param start -The start time of this appointment.
	 * @param duration -The expected duration of this appointment.
	 * @param customer -The name of the customer for this appointment.
	 * @param price -The expected price for this appointment.
	 * @param recurring -Specified how many weeks before the booked appointment should be repeated for 1 year ahead.
	 * 0 = only book it once.
	 * @return True if the appointment was successfully booked. 
	 * False if the desired time is already occupied by another appointment. **/
	public boolean bookAppointment(ZonedDateTime start, Duration duration, String customer, double price, int recurring) {
		int iterations = 1;
		
		if(recurring > 0)
			iterations = 52 / recurring;
		
		for(int i = 0; i < iterations; i++) {
			if(mCurrentBarber.getBookedTime(start) != null)
				return false;
		
			Appointment appointment = new Appointment(start, duration, customer, price);
		
			if(!mCurrentBarber.addAppointment(new Appointment(appointment)))
				return false;
				
			appointment = new Appointment(start, duration, customer, price);
			appointment.setTimeInterval(appointment.getStartTime().plusWeeks(i * recurring), appointment.getEndTime().plusWeeks(i * recurring));
			
		}
		
		return true;
	}
	
	/** Book a new appointment.
	 * @param appointment -The appointment to book.
	 * 0 = only book it once.
	 * @return True if the appointment was successfully booked. 
	 * False if the desired time is already occupied by another appointment. **/
	public boolean bookAppointment(Appointment appointment) {
		return mCurrentBarber.addAppointment(new Appointment(appointment));
	}
	
	/** Create a string with all the barbers that work here. 
	 * @return An array of strings with the names of all the barbers. **/
	public String[] barbersToString() {
		String strings[] = new String[mBarbers.size()];
		int index = 0;
		
		for(Barber barber : mBarbers) {
			strings[index] = barber.getName();
			index++;
		}
		
		return strings;
	}
	
	/** Cancel a booked appointment
	 * @param dateTime -Date and time occupied by the appointment to cancel. 
	 * @return True if the appointment was removed, false if it didn't exist. **/
	public boolean cancelAppointment(ZonedDateTime dateTime) {
		return mCurrentBarber.removeBooking(new Appointment(dateTime, Duration.ofHours(1), null, 0.0));
	}
	
	/** Pick a barber to work with.
	 * @param barber -The name of the barber.
	 * @return The name of the selected barber. If he/she did not exist, returns the 
	 * name of the previously selected barber instead **/
	public String pickBarber(String barber) {
		String name = mCurrentBarber.getName();
		
		for(Barber barb : mBarbers) {
			if(barb.getName().equalsIgnoreCase(barber)) {
				mCurrentBarber = barb;
				return barb.getName();
			}
		}
		
		return name;
	}
	
	/** Removes old appointments that lies in the past for all barbers. **/
	public void update() {
		for(Barber barber : mBarbers) {
			barber.removeOldAppointments();
		}
	}
	
	/** Get the correct end date & time for the given start date, time & time mode.
	 * @param timeMode -Defines how far into the future the end time should be.
	 * @param start -The start time. 
	 * @return The end time, or null if the selected time mode does not exist. **/
	private ZonedDateTime getEndTime(TimeMode timeMode, ZonedDateTime start) {
		ZonedDateTime end = null;
		
		switch(timeMode) {
			case DAY: {
				int hours = 24 - start.getHour();			
				end = start.plusHours(hours);
				break;
			}
			case WEEK: {
				int days = 7 - start.getDayOfWeek().getValue();
				end = start.plusDays(days);
				break;
			}
			case MONTH: {
				Calendar cal = Calendar.getInstance();
				int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH) - start.getDayOfMonth();
				end = start.plusDays(days);
				break;
			}
			case YEAR: {
				int months = 12 - start.getMonthValue();
				end = start.plusMonths(months);
				break;
			}
			case YEAR_AHEAD: {
				end = start.plusYears(1);
				break;
			}
			default: {
				break;
			}
		}
		
		return end;
	}
	
	/** Get the appointments of the currently selected barber. 
	 * @param timeMode -Defines weather the appointments for this day, week, month or year should be displayed. 
	 * @return A queue of strings representing the appointments for the selected barber. **/
	public Queue<String> getAppointments(TimeMode timeMode) {
		ZonedDateTime start = ZonedDateTime.now();
		ZonedDateTime end = getEndTime(timeMode, start);
		
		return mCurrentBarber.getAppointments(start, end);
	}
	
	/** Get the available free time that can be booked for this barber. 
	 * @param timeMode -Defines weather the appointments for this day, week, month or year should be displayed. 
	 * @return A queue containing strings that represents the available free time.**/
	public Queue<String> getFreeTime(TimeMode timeMode) {
		ZonedDateTime start = ZonedDateTime.now();
		ZonedDateTime end = getEndTime(timeMode, start);
		
		return mCurrentBarber.showFreeTime(start, end);
	}
	
	/** Save the currently booked appointments for all barbers into the selected file.
	 * @param path The path of the file to save the data to. If it does not exist, a new one will be created. 
	 * @throws IOException **/
	public void save(String path) throws IOException {
		boolean overwrite = true;
		
		for(Barber barber : mBarbers) {
			FileHandler.save(barber.toStringArray(), path, overwrite);
			
			if(overwrite)
				overwrite = false;
		}
	}
	
	/** Load data from file and add appointments 
	 * @param path -The path to the file.
	 * @throws IOException
	 * @throws NumberFormatException
	 * @throws ArrayIndexOutOfBoundsException
	 * @throws DateTimeParseException**/
	public void load(String path) throws DateTimeParseException, ArrayIndexOutOfBoundsException, NumberFormatException, IOException {
		String[] strings = FileHandler.load(path);	
		Barber currentBarber = null;
		
		for(String string : strings) {
			if(!string.contains(",")) { // Is this a barber name?
					Barber barber = new Barber(string);
					currentBarber = barber;
					mBarbers.add(barber); // Create the barber.
					
					if(mCurrentBarber == null)
						mCurrentBarber = currentBarber;
			}
			else if(currentBarber != null) {
				currentBarber.addAppointment(new Appointment(string));
			}
		}
		
	}
	
	/** Set the base price that this shop charges per booked minute.
	 * @param price -The base price value in SEK. **/
	public void setPrice(double price) {
		mBasePrice = price;
	}
	
	/** Get the base price that this shop charges per booked minute.
	 * @return The base price in SEK. **/
	public double getPrice() {
		return mBasePrice;
	}
}
