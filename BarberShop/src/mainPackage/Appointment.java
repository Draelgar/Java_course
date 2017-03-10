package mainPackage;

import java.io.IOException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** This object represents a booked appointment for a barber shop.
 * @author Gustaf Peter Hultgren
 * @version 2.1 **/
public class Appointment implements Comparable<Appointment>{
	private ZonedDateTime mStartTime;
	private ZonedDateTime mEndTime;
	private String mCustomer;
	private double mPrice;
	
	/** Default constructor. **/
	public Appointment() {
		mStartTime = ZonedDateTime.now();
		mEndTime = mStartTime.plusHours(1);

		mCustomer = "None";
		mPrice = 100.0;
	}
	
	/** Create a new Appointment object from a data string. 
	 * @param data -The string representation of a booked time object.
	 * @throws IOException
	 * @throws DateTimeParseException
	 * @throws ArrayIndexOutOfBoundsException
	 * @throws NumberFormatException **/
	public Appointment(String data) throws IOException, DateTimeParseException, ArrayIndexOutOfBoundsException, NumberFormatException {
		String[] sarr = data.split(",");
		
		// Setup a pattern to check so that the date time is in the correct format.
		Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}\\+\\d{2}:\\d{2}.+$");
		Matcher matcher = pattern.matcher(sarr[1]);
		
		try {
			mCustomer = sarr[0];
			if(matcher.find()) {
				mStartTime = ZonedDateTime.parse(sarr[1]);
			}
			else
				throw new IOException("Zoned date time " + sarr[1] + " is in an incorrect format!");
			
			matcher = pattern.matcher(sarr[2]);
			if(matcher.find()) {
				mEndTime = ZonedDateTime.parse(sarr[2]);
			}
			else
				throw new IOException("Zoned date time " + sarr[2] + " is in an incorrect format!");

			mPrice = Double.parseDouble(sarr[3]);
			
		} catch (IOException e) {
			throw e;
		} catch (DateTimeParseException e) {
			throw e;
		} catch (ArrayIndexOutOfBoundsException e){
			throw e;
		} catch (NumberFormatException e) {
			throw e;
		}
	}
	
	/** Create a new instance of this object. 
	 * @param start -The start time of this appointment.
	 * @param duration -The duration of this appointment.
	 * @param customer -The name of the customer.
	 * @param recurring -A flag determining weather the time should be booked repeatedly and how many weeks in between each.
	 * @param price -The price for this appointment.**/
	public Appointment(ZonedDateTime start, Duration duration, String customer, double price) {
		mStartTime = start;
		mEndTime = mStartTime.plusMinutes(duration.toMinutes());
		mCustomer = customer;
		mPrice = price;
	}
	
	/** Create a copy of the given appointment.
	 * @param appointment -The appointment to copy. **/
	public Appointment(Appointment appointment) {
		mStartTime = appointment.getStartTime();
		mEndTime = appointment.getEndTime();
		mCustomer = appointment.getCustomer();
		mPrice = appointment.getPrice();
	}
	
	/** Set the time interval. 
	 * @param start -The start time of this time interval.
	 * @param end -The end time of this time interval. **/
	public void setTimeInterval(ZonedDateTime start, ZonedDateTime end) {
		mStartTime = start;
		mEndTime = end;
	}
	
	/** Set the customer's name. 
	 * @param customer -The name of the customer.**/
	public void setCustomer(String customer) {
		mCustomer = customer;
	}
	
	/** Set the price. 
	 * @param price -The price of this appointment.**/
	public void setPrice(double price) {
		mPrice = price;
	}
	
	/** Get the start time. 
	 * @return The start time of this appointment.**/
	public ZonedDateTime getStartTime() {
		return mStartTime;
	}
	
	/** Get the end time. 
	 * @return The end time of this appointment.**/
	public ZonedDateTime getEndTime() {
		return mEndTime;
	}
	
	/** Get the name of the customer in question. 
	 * @return The name of the customer.**/
	public String getCustomer() {
		return mCustomer;
	}
	
	/** Get the price of this appointment. 
	 * @return The price of this appointment. **/
	public double getPrice() {
		return mPrice;
	}
	
	/** Compare to another booked time object. If their time interval
	 *  collide in any way, they are treated as equal. 
	 *  
	 *  @param booking -The appointment to test against.
	 *  @return 0 if the objects are colliding, -1 if o comes after this object, 1 if o comes before this object.**/
	public int compareTo(Appointment appointment) {
		if(appointment.getStartTime().isAfter(getEndTime()))
			return -1;
		else if(appointment.getEndTime().isBefore(mStartTime))
			return 1;
		else {
			return 0;
		}
	}
	
	/** Get a string representing this object. 
	 * @return A string representing this appointment in user friendly format. **/
	@Override
	public String toString() {
		String text = "Date: " + mStartTime.toLocalDate().toString() + "\n";
		text += "Customer: " + mCustomer + "\n";
		
		// Convert time to a string representation.
		String start = mStartTime.toLocalTime().toString();
		String end = mEndTime.toLocalTime().toString();
		
		text += "Time: " + start + " - " + end + "\n";
		text += "Price: " + mPrice + "kr\n";
		
		return text;
	}
	
	/** Return a string to print to file. 
	 * @return A string representing this appointment in a more compact form for storing in a file.**/
	public String toFileString() {
		return mCustomer + "," + mStartTime.toString() + "," + mEndTime.toString() + "," + mPrice + "\n";
	}
}
